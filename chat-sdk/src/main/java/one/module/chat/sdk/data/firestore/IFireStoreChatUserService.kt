package one.module.chat.sdk.data.firestore

import io.reactivex.Observable
import io.reactivex.Single
import one.module.chat.sdk.data.firestore.model.FireStoreUser

internal interface IFireStoreChatUserService {

    fun getUser(userId: String): Single<FireStoreUser>

    fun observeUser(userId: String): Observable<FireStoreUser>

    fun removeUserObserver()
}
