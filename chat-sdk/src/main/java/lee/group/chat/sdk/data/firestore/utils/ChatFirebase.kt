package lee.group.chat.sdk.data.firestore.utils

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

object ChatFirebase {

    private var useDefaultFirebase: Boolean = true

    private val isInitialized: Boolean
        get() = try {
            getFirebaseApp()
            true
        } catch (e: Exception) {
            false
        }

    fun init(context: Context, config: ChatFirebaseConfig? = null) {
        useDefaultFirebase = config == null
        if (isInitialized) {
            Timber.d("ChatFirebase is initialized")
            return
        }

        synchronized(isInitialized) {
            when {
                config.isValidFireBaseConfig() -> FirebaseApp.initializeApp(
                    context,
                    createFirebaseOptions(context, config),
                    CHAT_FIREBASE
                )
                else -> FirebaseApp.initializeApp(context)
            }
        }
    }

    private fun getFirebaseApp(): FirebaseApp = when {
        useDefaultFirebase -> FirebaseApp.getInstance()
        else -> FirebaseApp.getInstance(CHAT_FIREBASE)
    }

    fun getFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance(getFirebaseApp())

    fun getFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance(getFirebaseApp())

    fun getFirebaseAuthUID(): String? = FirebaseAuth.getInstance(getFirebaseApp()).uid

    private fun createFirebaseOptions(
        context: Context,
        config: ChatFirebaseConfig?
    ): FirebaseOptions {
        requireNotNull(config)
        val projectId = config.firebaseProjectId
        val apiKey = config.firebaseApiKey.orEmpty()
        val applicationId = config.firebaseApplicationId.orEmpty()

        return FirebaseOptions.Builder()
            .setProjectId(projectId)
            .setApplicationId(applicationId)
            .setApiKey(apiKey)
            .build()
    }

    fun release() {
        getFirebaseAuth().signOut()
    }

    private const val CHAT_FIREBASE = "CHAT_FIREBASE"
}

data class ChatFirebaseConfig(
    var firebaseProjectId: String? = null,
    var firebaseApiKey: String? = null,
    var firebaseApplicationId: String? = null
)

fun ChatFirebaseConfig?.isValidFireBaseConfig(): Boolean {
    return this != null && firebaseApiKey.isNullOrBlank().not() &&
        firebaseApplicationId.isNullOrBlank().not() &&
        firebaseProjectId.isNullOrBlank().not()
}
