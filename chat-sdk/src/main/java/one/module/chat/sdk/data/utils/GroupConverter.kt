package one.module.chat.sdk.data.utils

import one.module.chat.sdk.data.firestore.model.FireStoreGroup
import one.module.chat.sdk.data.firestore.model.FireStoreUser
import one.module.chat.sdk.data.firestore.utils.GROUP_PRIVATE_TYPE
import one.module.chat.sdk.data.model.channel.ChatChannel

internal fun FireStoreGroup.toChannel(): ChatChannel {
    return ChatChannel(
        channelId = group_id,
        title = getTitle(),
        avatarUrl = cover_url,
        message = last_message?.message,
        time = last_message?.created_at,
        lastMessage = last_message?.toChatMessage(),
        members = members?.map { it.toChatUser() }.orEmpty(),
        type = type,
        data = meta_data,
        isMessageSeen = isMessageSeen(this)
    )
}

internal fun List<FireStoreGroup>.toChannels(): List<ChatChannel> = filter { item ->
    item.isValidGroup()
}.map { item -> item.toChannel() }

internal fun FireStoreGroup.getTitle(): String? {
    return when (type) {
        GROUP_PRIVATE_TYPE -> getPartner()?.display_name
        else -> name
    }
}

internal fun isMessageSeen(group: FireStoreGroup): Boolean {
    val myProfile = group.members?.firstOrNull { member -> member.isCurrentUser() }
    myProfile?.let { profile ->
        if (group.last_message?.created_at != null && profile.seen_at != null) {
            return group.last_message!!.created_at < profile.seen_at!!
        }
    }
    return false
}

internal fun FireStoreGroup.getPartner(): FireStoreUser? {
    return members?.firstOrNull { it.isCurrentUser().not() } ?: members?.firstOrNull()
}
