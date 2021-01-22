package lee.group.chat.sdk.config

import android.content.Context
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.utils.ChatUserManager
import timber.log.Timber

internal object ChatInitializerManager {

    private var isInitialized: Boolean = false

    fun init(context: Context, config: OneChatSDKConfig? = null) {
        if (isInitialized) {
            Timber.d("ChatInitializerManager is initialized")
            return
        }
        synchronized(isInitialized) {
            ChatFirebase.init(context, config?.chatFirebaseConfig)
            isInitialized = true
        }
    }

    fun clean(context: Context) {
        try {
            ChatFirebase.release()
            ChatUserManager.releaseCurrentActiveUser()
        } catch (ex: Exception) {
            Timber.e(ex)
        }
    }
}
