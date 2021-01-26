package lee.group.chat.sdk.data.repo_impl

import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import lee.group.chat.sdk.data.IMessageRepository
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatMessageService
import lee.group.chat.sdk.data.firestore.model.FireStoreUser
import lee.group.chat.sdk.data.model.ChatMessage
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.model.event.MessageReceived
import lee.group.chat.sdk.data.model.event.OneChatEvent
import lee.group.chat.sdk.data.utils.isCurrentUser
import lee.group.chat.sdk.data.utils.makeDeleteMessage
import lee.group.chat.sdk.data.utils.makeSendingMessage
import lee.group.chat.sdk.data.utils.toChannel
import lee.group.chat.sdk.data.utils.toChatMessages
import lee.group.chat.sdk.data.utils.toFireStoreMessage

/**
 * This repository handle List Channel sync data
 */
@FlowPreview
internal class MessageRepositoryImpl @Inject constructor(
    private val chatGroupFireStore: IFireStoreChatGroupService,
    private val chatMessageFireStore: IFireStoreChatMessageService
) : IMessageRepository {

    private var channelId: String = ""
    private var nextFetchTime: Long = System.currentTimeMillis()
    private var previousFetchTime: Long = System.currentTimeMillis()

    /**************************
     * Message Repository Impl
     **************************/

    override suspend fun getPreviousMessages(
        limit: Long,
        timePoint: Long?
    ): Flow<List<ChatMessage>> {
        val timeStamp = timePoint ?: previousFetchTime
        return chatMessageFireStore.getPreviousMessages(channelId, limit, timeStamp)
            .map { it.toChatMessages() }
            .onEach {
                updateNextAndPreviousFetchTime(it)
            }
    }

    override suspend fun getNextMessages(limit: Long, timePoint: Long?): Flow<List<ChatMessage>> {
        val timeStamp = timePoint ?: nextFetchTime
        return chatMessageFireStore.getNextMessages(channelId, limit, timeStamp)
            .map { it.toChatMessages() }
            .onEach { updateNextAndPreviousFetchTime(it) }
    }

    override suspend fun init(channelId: String): Flow<ChatChannel> {
        this.channelId = channelId
        return chatGroupFireStore.observeGroup(channelId)
            .map { it.toChannel() }
    }

    override suspend fun sendTextMessage(message: String): Flow<Unit> {
        val chatMessage = ChatMessage.makeSendingMessage(message)
        return chatMessageFireStore.sendMessage(channelId, chatMessage.toFireStoreMessage())
            .flatMapConcat { updateChannelLastMessage(chatMessage) }
    }

    override suspend fun sendMessage(chatMessage: ChatMessage): Flow<Unit> {
        return chatMessageFireStore.sendMessage(channelId, chatMessage.toFireStoreMessage())
            .flatMapConcat { updateChannelLastMessage(chatMessage) }
    }

    override suspend fun observeChannelEvents(): Flow<OneChatEvent> {
        return chatMessageFireStore.observeNewMessages(channelId)
            .map { MessageReceived(it.toChatMessages()) }
    }

    override suspend fun release() {
        chatMessageFireStore.removeMessagesObserver()
    }

    override suspend fun removeMessage(messageId: String): Flow<Unit> {
        return chatMessageFireStore.removeMessage(channelId, messageId)
            .onEach { updateChannelLastMessage(ChatMessage.makeDeleteMessage()) }
    }

    override suspend fun markAllMessagesAsRead(): Flow<Unit> {
        return chatGroupFireStore.getGroup(channelId)
            .map { createNewUsersWithLastSeen(it.members.orEmpty()) }
            .flatMapConcat { chatGroupFireStore.markAllMessagesAsRead(channelId, it) }
    }

    private suspend fun updateChannelLastMessage(
        newMessage: ChatMessage
    ): Flow<Unit> {
        return chatGroupFireStore.updateChatGroupLastMessage(
            channelId,
            newMessage.toFireStoreMessage()
        )
    }

    private fun createNewUsersWithLastSeen(
        members: List<FireStoreUser>
    ): List<FireStoreUser> {
        val newListMembers = ArrayList<FireStoreUser>()

        for (member in members) {
            if (member.isCurrentUser()) {
                newListMembers.add(member.copy(seen_at = System.currentTimeMillis()))
            } else {
                newListMembers.add(member)
            }
        }

        return newListMembers
    }

    private suspend fun updateNextAndPreviousFetchTime(listMessage: List<ChatMessage>) {
        if (listMessage.isNullOrEmpty()) return
        nextFetchTime =
            kotlin.math.max(nextFetchTime, listMessage.maxBy { it.createdAt }!!.createdAt)
        previousFetchTime = previousFetchTime.coerceAtMost(
            listMessage.minBy { it.createdAt }!!.createdAt
        )
    }
}
