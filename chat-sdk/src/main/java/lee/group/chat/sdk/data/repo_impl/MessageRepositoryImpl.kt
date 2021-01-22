package lee.group.chat.sdk.data.repo_impl

import io.reactivex.Completable
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import lee.group.chat.sdk.data.IMessageRepository
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatMessageService
import lee.group.chat.sdk.data.firestore.model.FireStoreUser
import lee.group.chat.sdk.data.model.ChatMessage
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.model.event.MessageReceived
import lee.group.chat.sdk.data.model.event.OneChatEvent
import lee.group.chat.sdk.data.utils.FIRE_STORE_DEFAULT_REQUEST_TIMEOUT
import lee.group.chat.sdk.data.utils.isCurrentUser
import lee.group.chat.sdk.data.utils.makeDeleteMessage
import lee.group.chat.sdk.data.utils.makeSendingMessage
import lee.group.chat.sdk.data.utils.toChannel
import lee.group.chat.sdk.data.utils.toChatMessages
import lee.group.chat.sdk.data.utils.toFireStoreMessage

/**
 * This repository handle List Channel sync data
 */
internal class MessageRepositoryImpl constructor(
    private val chatGroupFireStore: IFireStoreChatGroupService,
    private val chatMessageFireStore: IFireStoreChatMessageService
) : IMessageRepository {

    private var channelId: String = ""
    private var nextFetchTime: Long = System.currentTimeMillis()
    private var previousFetchTime: Long = System.currentTimeMillis()

    /**************************
     * Message Repository Impl
     **************************/

    override fun getPreviousMessages(limit: Long, timePoint: Long?): Observable<List<ChatMessage>> {
        val timeStamp = timePoint ?: previousFetchTime
        return chatMessageFireStore.getPreviousMessages(channelId, limit, timeStamp)
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
            .map { it.toChatMessages() }
            .doOnNext { updateNextAndPreviousFetchTime(it) }
    }

    override fun getNextMessages(limit: Long, timePoint: Long?): Observable<List<ChatMessage>> {
        val timeStamp = timePoint ?: nextFetchTime
        return chatMessageFireStore.getNextMessages(channelId, limit, timeStamp)
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
            .map { it.toChatMessages() }
            .doOnNext { updateNextAndPreviousFetchTime(it) }
    }

    override fun init(channelId: String): Observable<ChatChannel> {
        this.channelId = channelId
        return chatGroupFireStore.observeGroup(channelId)
            .map { it.toChannel() }
    }

    override fun sendTextMessage(message: String): Completable {
        val chatMessage = ChatMessage.makeSendingMessage(message)
        return chatMessageFireStore.sendMessage(channelId, chatMessage.toFireStoreMessage())
            .andThen(updateChannelLastMessage(chatMessage))
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    override fun sendMessage(chatMessage: ChatMessage): Completable {
        return chatMessageFireStore.sendMessage(channelId, chatMessage.toFireStoreMessage())
            .andThen(updateChannelLastMessage(chatMessage))
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    override fun observeChannelEvents(): Observable<OneChatEvent> {
        return chatMessageFireStore.observeNewMessages(channelId)
            .flatMapIterable {
                it.toChatMessages()
            }
            .distinct()
            .map { MessageReceived(it) }
    }

    override fun release() {
        chatGroupFireStore.removeLastGroupObserver()
        chatMessageFireStore.removeMessagesObserver()
    }

    override fun removeMessage(messageId: String): Completable {
        return chatMessageFireStore.removeMessage(channelId, messageId)
            .andThen(updateChannelLastMessage(ChatMessage.makeDeleteMessage()))
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    override fun markAllMessagesAsRead(): Completable {
        return chatGroupFireStore.getGroup(channelId)
            .map { createNewUsersWithLastSeen(it.members.orEmpty()) }
            .flatMap { chatGroupFireStore.markAllMessagesAsRead(channelId, it) }
            .flatMapCompletable { Completable.complete() }
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    private fun updateChannelLastMessage(
        newMessage: ChatMessage
    ): Completable {
        return chatGroupFireStore.updateChatGroupLastMessage(
            channelId,
            newMessage.toFireStoreMessage()
        )
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
            .flatMapCompletable { Completable.complete() }
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

    private fun updateNextAndPreviousFetchTime(listMessage: List<ChatMessage>) {
        if (listMessage.isNullOrEmpty()) return
        nextFetchTime =
            kotlin.math.max(nextFetchTime, listMessage.maxBy { it.createdAt }!!.createdAt)
        previousFetchTime = previousFetchTime.coerceAtMost(
            listMessage.minBy { it.createdAt }!!.createdAt
        )
    }
}
