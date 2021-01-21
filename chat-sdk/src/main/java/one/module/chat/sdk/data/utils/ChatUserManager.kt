package one.module.chat.sdk.data.utils

import one.module.chat.sdk.data.firestore.impl.FireStoreChatUserServiceImpl
import one.module.chat.sdk.data.firestore.utils.ChatFirebase
import one.module.chat.sdk.data.model.ChatUser

internal object ChatUserManager {

    fun getCurrentActiveUID(): String? {
        return ChatFirebase.getFirebaseAuthUID()
    }

    internal fun getCurrentActiveUser(): ChatUser? {
        return FireStoreChatUserServiceImpl.currentFireStoreUser?.toChatUser()
    }

    internal fun releaseCurrentActiveUser() {
        FireStoreChatUserServiceImpl.currentFireStoreUser = null
    }
}
