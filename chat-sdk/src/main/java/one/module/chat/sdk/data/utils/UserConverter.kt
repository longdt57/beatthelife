package one.module.chat.sdk.data.utils

import one.module.chat.sdk.data.firestore.model.FireStoreUser
import one.module.chat.sdk.data.model.ChatUser

internal fun ChatUser.toFireStoreMessageSender(): FireStoreUser {
    return FireStoreUser(
        uid = userId,
        display_name = nickname,
        profile_url = profileUrl
    )
}

internal fun FireStoreUser.toChatUser(): ChatUser {
    return ChatUser(
        userId = uid,
        nickname = display_name,
        profileUrl = profile_url,
        groups = groups.orEmpty(),
        seenAt = seen_at
    )
}

internal fun FireStoreUser.isCurrentUser(): Boolean {
    return uid == ChatUserManager.getCurrentActiveUID()
}
