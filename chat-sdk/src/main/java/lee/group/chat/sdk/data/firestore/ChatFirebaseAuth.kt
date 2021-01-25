package lee.group.chat.sdk.data.firestore

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.firestore.utils.onProducerScopeOffer

@ExperimentalCoroutinesApi
internal object ChatFirebaseAuth {

    fun isAuth(uid: String): Boolean {
        return when {
            firebaseAuth.currentUser == null -> false
            firebaseAuth.currentUser?.uid == uid -> true
            else -> {
                ChatFirebase.release()
                false
            }
        }
    }

    fun signInWithCustomToken(token: String): Flow<Unit> = callbackFlow {
        firebaseAuth.signInWithCustomToken(token)
            .addOnSuccessListener {
                onProducerScopeOffer(null, Unit)
            }
            .addOnFailureListener { error ->
                onProducerScopeOffer(error, null)
            }
    }

    private val firebaseAuth get() = ChatFirebase.getFirebaseAuth()
}
