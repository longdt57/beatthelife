package one.module.chat.sdk.data.firestore.impl

import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.Observable
import io.reactivex.Single
import one.module.chat.sdk.data.firestore.IFireStoreChatUserService
import one.module.chat.sdk.data.firestore.model.FireStoreUser
import one.module.chat.sdk.data.firestore.utils.ChatFirebase
import one.module.chat.sdk.data.firestore.utils.FireStoreChatRef
import one.module.chat.sdk.data.firestore.utils.onFireStoreSnapshotListener
import one.module.chat.sdk.data.firestore.utils.onRxSingleListener
import one.module.chat.sdk.data.firestore.utils.toObjectOrNull

internal class FireStoreChatUserServiceImpl : IFireStoreChatUserService {

    private var userListener: ListenerRegistration? = null

    override fun getUser(userId: String): Single<FireStoreUser> {
        if (currentFireStoreUser?.uid == userId) return Single.just(currentFireStoreUser)

        return Single.create<FireStoreUser> { emitter ->
            FireStoreChatRef.getUserDocument(userId)
                .get()
                .addOnSuccessListener {
                    it.toObjectOrNull<FireStoreUser>()?.let { user ->
                        updateCurrentUser(user)
                        emitter.onRxSingleListener(null, user)
                    }
                }
                .addOnFailureListener { error ->
                    emitter.onRxSingleListener(error, null)
                }
        }
    }

    override fun observeUser(userId: String): Observable<FireStoreUser> {
        userListener?.remove()
        return Observable.create { emitter ->
            userListener = FireStoreChatRef.getUserDocument(userId)
                .addSnapshotListener { value, error ->
                    updateCurrentUser(value?.toObject(FireStoreUser::class.java))
                    emitter.onFireStoreSnapshotListener(error, value)
                }
        }
    }

    override fun removeUserObserver() {
        userListener?.remove()
        userListener = null
    }

    private fun updateCurrentUser(fireStoreUser: FireStoreUser?) {
        if (fireStoreUser == null) return
        if (ChatFirebase.getFirebaseAuthUID() != fireStoreUser.uid) return
        currentFireStoreUser = fireStoreUser
    }

    companion object {
        internal var currentFireStoreUser: FireStoreUser? = null
    }
}
