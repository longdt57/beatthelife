package lee.group.chat.sdk.data.firestore.impl

import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import lee.group.chat.sdk.data.firestore.IFireStoreChatUserService
import lee.group.chat.sdk.data.firestore.model.FireStoreUser
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.firestore.utils.FireStoreChatRef
import lee.group.chat.sdk.data.firestore.utils.onFireStoreSnapshotListener
import lee.group.chat.sdk.data.firestore.utils.onProducerScopeOffer
import lee.group.chat.sdk.data.firestore.utils.toObjectOrNull
import lee.group.chat.sdk.data.utils.ChatUserManager

@ExperimentalCoroutinesApi
internal class FireStoreChatUserServiceImpl @Inject constructor() : IFireStoreChatUserService {

    override suspend fun getCurrentUser(): Flow<FireStoreUser> = callbackFlow {
        val userId = ChatUserManager.getCurrentActiveUID()
        if (currentFireStoreUser?.uid == userId) {
            offer(currentFireStoreUser!!)
            close()
        }

        FireStoreChatRef.getUserDocument(userId.orEmpty())
            .get()
            .addOnSuccessListener {
                it.toObjectOrNull<FireStoreUser>()?.let { user ->
                    updateCurrentUser(user)
                    onProducerScopeOffer(null, user)
                    close()
                }
            }
            .addOnFailureListener { error ->
                onProducerScopeOffer(error, null)
            }
    }

    @ExperimentalCoroutinesApi
    override suspend fun observeCurrentUser(): Flow<FireStoreUser> = callbackFlow {
        val userId = ChatUserManager.getCurrentActiveUID().orEmpty()
        val userListener = FireStoreChatRef.getUserDocument(userId)
            .addSnapshotListener { value, error ->
                updateCurrentUser(value?.toObject(FireStoreUser::class.java))
                onFireStoreSnapshotListener(error, value)
            }
        awaitClose { userListener.remove() }
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
