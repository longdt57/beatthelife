package lee.group.chat.sdk.data.firestore

import io.reactivex.Single

internal interface IChatFirebaseAuth {

    fun isAuth(uid: String): Boolean

    fun signInWithCustomToken(token: String): Single<Unit>
}
