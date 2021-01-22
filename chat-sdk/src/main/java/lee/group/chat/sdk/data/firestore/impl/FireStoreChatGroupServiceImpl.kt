package lee.group.chat.sdk.data.firestore.impl

import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.model.FireStoreGroup
import lee.group.chat.sdk.data.firestore.model.FireStoreMessage
import lee.group.chat.sdk.data.firestore.model.FireStoreUser
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_GROUP_ID
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_GROUP_LAST_MESSAGE
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_GROUP_MEMBERS
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_GROUP_REMOVED
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_CREATED_AT
import lee.group.chat.sdk.data.firestore.utils.FireStoreChatRef
import lee.group.chat.sdk.data.firestore.utils.getNotNullObjects
import lee.group.chat.sdk.data.firestore.utils.onFireStoreSnapshotListener
import lee.group.chat.sdk.data.firestore.utils.onRxCompletableListener
import lee.group.chat.sdk.data.firestore.utils.onRxObservableListener
import lee.group.chat.sdk.data.firestore.utils.onRxSingleListener
import lee.group.chat.sdk.data.firestore.utils.toObjectOrNull

internal class FireStoreChatGroupServiceImpl : IFireStoreChatGroupService {

    private var groupsListener: MutableList<ListenerRegistration> = mutableListOf()

    private var groupListener: ListenerRegistration? = null

    override fun getGroup(groupId: String): Single<FireStoreGroup> {
        return Single.create { emitter ->
            FireStoreChatRef.getGroupDocument(groupId)
                .get()
                .addOnSuccessListener { groupSnapshots ->
                    emitter.onRxSingleListener(null, groupSnapshots.toObjectOrNull())
                }
                .addOnFailureListener { error ->
                    emitter.onRxSingleListener(error, null)
                }
        }
    }

    override fun getGroups(groupIds: List<String>): Single<List<FireStoreGroup>> {
        return Single.create { emitter ->
            val groups = groupIds.subList(0, MAX_CHANNEL_SIZE)
            FireStoreChatRef.getGroupCollection()
                .whereIn(FIRE_STORE_GROUP_ID, groups)
                .get()
                .addOnSuccessListener { groupSnapshots ->
                    emitter.onRxSingleListener(null, groupSnapshots.getNotNullObjects())
                }
                .addOnFailureListener { error ->
                    emitter.onRxSingleListener(error, null)
                }
        }
    }

    override fun observeGroups(groupIds: List<String>): Observable<QuerySnapshot> {
        removeAllGroupsObserver()
        return Observable.defer {
            Observable.create<QuerySnapshot> { emitter ->
                observeFireStoreChannel(groupIds, emitter)
            }
        }
    }

    override fun observeGroup(groupId: String): Observable<FireStoreGroup> {
        removeAllGroupsObserver()
        return Observable.create { emitter ->
            FireStoreChatRef.getGroupDocument(groupId)
                .addSnapshotListener { value, error ->
                    emitter.onFireStoreSnapshotListener(error, value)
                }.apply {
                    groupListener?.remove()
                    groupListener = this
                }
        }
    }

    override fun removeGroup(groupId: String): Completable = Completable.create { emitter ->
        FireStoreChatRef.getGroupDocument(groupId)
            .update(FIRE_STORE_GROUP_REMOVED, true)
            .addOnCompleteListener {
                emitter.onRxCompletableListener(it.exception)
            }
    }

    override fun updateChatGroupLastMessage(
        groupId: String,
        fireStoreMessage: FireStoreMessage
    ): Single<FireStoreMessage> {
        return Single.create { emitter ->
            FireStoreChatRef.getGroupDocument(groupId)
                .update(fireStoreMessage.getLastMessageUpdateMap())
                .addOnCompleteListener {
                    emitter.onRxSingleListener(it.exception, fireStoreMessage)
                }
        }
    }

    private fun FireStoreMessage.getLastMessageUpdateMap() = mapOf(
        FIRE_STORE_GROUP_LAST_MESSAGE to this,
        FIRE_STORE_GROUP_REMOVED to false
    )

    override fun removeAllGroupsObserver() {
        groupsListener.forEach { item ->
            item.remove()
        }
        groupsListener.clear()
    }

    override fun removeLastGroupObserver() {
        groupListener?.remove()
    }

    override fun markAllMessagesAsRead(
        groupId: String,
        members: List<FireStoreUser>
    ): Single<FireStoreUser> {
        return Single.create { emitter ->

            FireStoreChatRef.getMessageMembersSeenAtDocument(groupId)
                .update(FIRE_STORE_GROUP_MEMBERS, members)
                .addOnCompleteListener {
                    val myMember =
                        members.firstOrNull { member ->
                            member.uid == ChatFirebase.getFirebaseAuthUID()
                        }
                    emitter.onRxSingleListener(it.exception, myMember)
                }
        }
    }

    private fun observeFireStoreChannel(
        list: List<String>,
        emitter: ObservableEmitter<QuerySnapshot>
    ) {
        for (i in 0..list.size / MAX_CHANNEL_SIZE) {
            val index = kotlin.math.min(MAX_CHANNEL_SIZE * (i + 1), list.size)
            val queryList = list.subList(MAX_CHANNEL_SIZE * i, index)
            observeGroupBy10(queryList, emitter)
        }
    }

    private fun observeGroupBy10(
        groupIds: List<String>,
        emitter: ObservableEmitter<QuerySnapshot>
    ) {
        if (groupIds.size > MAX_CHANNEL_SIZE || groupIds.isNullOrEmpty()) return
        FireStoreChatRef.getGroupCollection()
            .whereIn(FIRE_STORE_GROUP_ID, groupIds)
            .orderBy(
                "$FIRE_STORE_GROUP_LAST_MESSAGE.$FIRE_STORE_MESSAGE_CREATED_AT",
                Query.Direction.DESCENDING
            )
            .addSnapshotListener { value, error ->
                emitter.onRxObservableListener(error, value)
            }.apply {
                groupsListener.add(this)
            }
    }

    companion object {
        const val MAX_CHANNEL_SIZE = 10
    }
}
