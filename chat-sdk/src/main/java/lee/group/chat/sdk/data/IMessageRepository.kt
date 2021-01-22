package lee.group.chat.sdk.data

import io.reactivex.Completable
import io.reactivex.Observable
import lee.group.chat.sdk.data.model.ChatMessage
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.model.event.OneChatEvent

interface IMessageRepository {

    fun init(channelId: String): Observable<ChatChannel>

    fun getPreviousMessages(
        limit: Long = MESSAGE_PAGING,
        timePoint: Long? = null
    ): Observable<List<ChatMessage>>

    fun getNextMessages(
        limit: Long = MESSAGE_PAGING,
        timePoint: Long? = null
    ): Observable<List<ChatMessage>>

    fun sendTextMessage(message: String): Completable

    fun sendMessage(chatMessage: ChatMessage): Completable

    fun observeChannelEvents(): Observable<OneChatEvent>

    fun removeMessage(messageId: String): Completable

    fun markAllMessagesAsRead(): Completable

    fun release()
}

internal const val MESSAGE_PAGING: Long = 20
