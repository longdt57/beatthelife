package lee.group.chat.sdk.data

import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.model.ChatMessage
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.model.event.OneChatEvent

interface IMessageRepository {

    suspend fun init(channelId: String): Flow<ChatChannel>

    suspend fun getPreviousMessages(
        limit: Long = MESSAGE_PAGING,
        timePoint: Long? = null
    ): Flow<List<ChatMessage>>

    suspend fun getNextMessages(
        limit: Long = MESSAGE_PAGING,
        timePoint: Long? = null
    ): Flow<List<ChatMessage>>

    suspend fun sendTextMessage(message: String): Flow<Unit>

    suspend fun sendMessage(chatMessage: ChatMessage): Flow<Unit>

    suspend fun observeChannelEvents(): Flow<OneChatEvent>

    suspend fun removeMessage(messageId: String): Flow<Unit>

    suspend fun markAllMessagesAsRead(): Flow<Unit>

    suspend fun release()
}

internal const val MESSAGE_PAGING: Long = 20
