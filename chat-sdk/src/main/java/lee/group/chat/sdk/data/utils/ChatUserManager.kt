package lee.group.chat.sdk.data.utils

import lee.group.chat.sdk.data.firestore.impl.FireStoreChatUserServiceImpl
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.model.ChatUser

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
