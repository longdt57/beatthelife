package one.module.chat.sdk.data.firestore.impl

import io.reactivex.Single
import one.module.chat.sdk.data.firestore.IChatFirebaseAuth
import one.module.chat.sdk.data.firestore.utils.ChatFirebase
import one.module.chat.sdk.data.firestore.utils.onRxSingleListener

internal class ChatFirebaseAuthImpl : IChatFirebaseAuth {

    override fun isAuth(uid: String): Boolean {
        val firebaseAuth = firebaseAuth()
        return when {
            firebaseAuth.currentUser == null -> false
            firebaseAuth.currentUser?.uid == uid -> true
            else -> {
                ChatFirebase.release()
                false
            }
        }
    }

    override fun signInWithCustomToken(token: String): Single<Unit> {
        return Single.create { emitter ->
            firebaseAuth().signInWithCustomToken(token)
                .addOnSuccessListener {
                    emitter.onRxSingleListener(null, Unit)
                }
                .addOnFailureListener { error ->
                    emitter.onRxSingleListener(error, null)
                }
        }
    }

    private fun firebaseAuth() = ChatFirebase.getFirebaseAuth()
}
