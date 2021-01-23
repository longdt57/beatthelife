package lee.group.chat.sdk.data.firestore

import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.firestore.model.FireStoreUser

internal interface IFireStoreChatUserService {

    fun getUser(userId: String): Single<FireStoreUser>

    suspend fun observeUser(userId: String): Flow<FireStoreUser>

    fun removeUserObserver()
}
