package one.module.chat.sdk.data.model.channel

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
import one.module.chat.sdk.data.model.ChatMessage
import one.module.chat.sdk.data.model.ChatUser

@Parcelize
@Keep
data class ChatChannel(
    override val channelId: String,
    override val title: String?,
    override val avatarUrl: String?,
    override val message: String?,
    override val lastMessage: ChatMessage?,
    override val time: Long?,
    override val members: List<ChatUser>,
    override val isMessageSeen: Boolean? = null,
    override val type: String?,
    val data: String? = null
) : BaseChannel(
    channelId = channelId,
    title = title,
    message = message,
    time = time,
    members = members,
    avatarUrl = avatarUrl,
    lastMessage = lastMessage,
    isMessageSeen = isMessageSeen,
    type = type
),
    Parcelable {

    constructor(channelId: String) : this(
        channelId = channelId,
        title = null,
        avatarUrl = null,
        message = null,
        lastMessage = null,
        time = null,
        members = emptyList(),
        isMessageSeen = false,
        type = null,
        data = null
    )
}
