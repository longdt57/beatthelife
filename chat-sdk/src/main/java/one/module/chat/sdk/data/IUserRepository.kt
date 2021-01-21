package one.module.chat.sdk.data

import io.reactivex.Completable
import io.reactivex.Observable
import one.module.chat.sdk.data.model.ChatUser
import one.module.chat.sdk.data.model.channel.ChatChannel

interface IUserRepository {

    fun observeChannel(groupID: String): Observable<ChatChannel>

    fun removeChannel(groupID: String): Completable

    fun observeCurrentUser(): Observable<ChatUser>
}
