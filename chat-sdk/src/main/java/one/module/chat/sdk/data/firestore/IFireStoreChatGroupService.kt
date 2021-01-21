package one.module.chat.sdk.data.firestore

import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import one.module.chat.sdk.data.firestore.model.FireStoreGroup
import one.module.chat.sdk.data.firestore.model.FireStoreMessage
import one.module.chat.sdk.data.firestore.model.FireStoreUser

internal interface IFireStoreChatGroupService {

    fun getGroup(groupId: String): Single<FireStoreGroup>

    fun getGroups(groupIds: List<String>): Single<List<FireStoreGroup>>

    fun observeGroup(groupId: String): Observable<FireStoreGroup>

    fun observeGroups(groupIds: List<String>): Observable<QuerySnapshot>

    fun updateChatGroupLastMessage(
        groupId: String,
        fireStoreMessage: FireStoreMessage
    ): Single<FireStoreMessage>

    fun removeAllGroupsObserver()

    fun removeLastGroupObserver()

    fun removeGroup(groupId: String): Completable

    fun markAllMessagesAsRead(
        groupId: String,
        members: List<FireStoreUser>
    ): Single<FireStoreUser>
}
