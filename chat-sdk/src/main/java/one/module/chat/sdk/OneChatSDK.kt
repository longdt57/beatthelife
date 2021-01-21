package one.module.chat.sdk

import android.content.Context
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.callbackFlow
import one.module.chat.sdk.config.ChatInitializerManager
import one.module.chat.sdk.config.OneChatSDKConfig
import one.module.chat.sdk.data.firestore.utils.ChatFirebase
import one.module.chat.sdk.data.utils.ChatUserManager

object OneChatSDK {

    fun init(context: Context, config: OneChatSDKConfig? = null) {
        ChatInitializerManager.init(context, config)
    }

    fun clean(context: Context) {
        ChatInitializerManager.clean(context)
    }

    fun disConnect() {
        ChatFirebase.release()
    }

    fun getCurrentUser() = ChatUserManager.getCurrentActiveUser()

    fun isConnected(): Boolean {
        val firebaseAuth = ChatFirebase.getFirebaseAuth()
        return when {
            firebaseAuth.currentUser == null -> false
            firebaseAuth.currentUser?.uid != null -> true
            else -> {
                ChatFirebase.release()
                false
            }
        }
    }

    @ExperimentalCoroutinesApi
    fun connect(token: String) = callbackFlow {
        val firebaseAuth = ChatFirebase.getFirebaseAuth()
        firebaseAuth.signInWithCustomToken(token)
            .addOnSuccessListener {
                offer(Unit)
            }
            .addOnFailureListener { error ->
                close(error)
            }
    }
}
