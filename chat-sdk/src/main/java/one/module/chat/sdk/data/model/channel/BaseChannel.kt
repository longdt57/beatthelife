package one.module.chat.sdk.data.model.channel

import androidx.annotation.Keep
import one.module.chat.sdk.data.firestore.utils.GROUP_PEOPLE_TYPE
import one.module.chat.sdk.data.firestore.utils.GROUP_PRIVATE_TYPE
import one.module.chat.sdk.data.model.ChatMessage
import one.module.chat.sdk.data.model.ChatUser
import one.module.chat.sdk.data.utils.ChatUserManager
import one.module.chat.sdk.data.utils.orFalse

@Keep
open class BaseChannel(
    open val title: String? = null,
    open val message: String? = null,
    open val time: Long? = null,
    open val avatarUrl: String? = null,
    // Primary key
    open val channelId: String = "",
    open val lastMessage: ChatMessage? = null,
    open val notificationOff: Boolean? = null,
    open val members: List<ChatUser> = emptyList(),
    open val isMessageSeen: Boolean? = null,
    open val type: String? = null
) {

    private fun baseContentEquals(other: BaseChannel): Boolean {
        if (title != other.title) return false
        if (message != other.message) return false
        if (time != other.time) return false
        if (avatarUrl != other.avatarUrl) return false
        if (channelId != other.channelId) return false
        if (lastMessage != other.lastMessage) return false
        if (notificationOff != other.notificationOff) return false
        if (members != other.members) return false
        if (isMessageSeen != other.isMessageSeen) return false

        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseChannel

        return baseContentEquals(other)
    }

    override fun hashCode(): Int {
        var result = title?.hashCode() ?: 0
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (time?.toString()?.hashCode() ?: 0)
        result = 31 * result + (avatarUrl?.hashCode() ?: 0)
        result = 31 * result + channelId.hashCode()
        result = 31 * result + (lastMessage?.hashCode() ?: 0)
        result = 31 * result + (notificationOff?.hashCode() ?: 0)
        result = 31 * result + members.hashCode()
        result = 31 * result + isMessageSeen.hashCode()
        return result
    }

    open val isRead: Boolean
        get() = isMessageSeen.orFalse()

    val lastMessageType: String?
        get() = lastMessage?.type

    val senderName: String?
        get() = lastMessage?.senderName

    val isMyMessage: Boolean?
        get() = lastMessage?.isMyMessage

    fun is1To1Channel() = type.equals(GROUP_PRIVATE_TYPE)

    fun isGroupChannel() = type.equals(GROUP_PEOPLE_TYPE)

    fun isEmptyChannel(): Boolean {
        return lastMessage?.id.isNullOrBlank()
    }

    fun getFirstPartnerUserId(): String =
        members.firstOrNull { it.userId != ChatUserManager.getCurrentActiveUID() }?.userId.orEmpty()

    val isBlockedByMe: Boolean? = false
}
