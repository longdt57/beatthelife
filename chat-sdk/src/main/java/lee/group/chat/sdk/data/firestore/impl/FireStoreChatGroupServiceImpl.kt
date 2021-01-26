package lee.group.chat.sdk.data.firestore.impl

import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
import lee.group.chat.sdk.data.firestore.utils.onProducerScopeOffer
import lee.group.chat.sdk.data.firestore.utils.toObjectOrNull
import lee.group.chat.sdk.data.firestore.utils.toObjectsOrEmpty

@ExperimentalCoroutinesApi
internal class FireStoreChatGroupServiceImpl @Inject constructor() : IFireStoreChatGroupService {

    override suspend fun getGroup(groupId: String): Flow<FireStoreGroup> = callbackFlow {
        try {
            FireStoreChatRef.getGroupDocument(groupId)
                .get()
                .addOnCompleteListener { result ->
                    val data = result.result?.toObjectOrNull<FireStoreGroup>()
                    onProducerScopeOffer(result.exception, data)
                    close()
                }
        } catch (ex: Throwable) {
            close(ex)
        }
    }

    override suspend fun getGroups(groupIds: List<String>): Flow<List<FireStoreGroup>> =
        callbackFlow {
            try {
                val groups = groupIds.subList(0, MAX_CHANNEL_SIZE)
                FireStoreChatRef.getGroupCollection()
                    .whereIn(FIRE_STORE_GROUP_ID, groups)
                    .get()
                    .addOnCompleteListener { result ->
                        val data = result.result?.toObjectsOrEmpty<FireStoreGroup>()
                        onProducerScopeOffer(result.exception, data)
                        close()
                    }
            } catch (ex: Throwable) {
                close(ex)
            }
        }

    override suspend fun observeGroups(groupIds: List<String>): Flow<QuerySnapshot> = callbackFlow {
        val listeners = observeFireStoreChannel(groupIds, this)
        awaitClose {
            listeners.forEach { listener ->
                listener.remove()
            }
        }
    }

    override suspend fun observeGroup(groupId: String): Flow<FireStoreGroup> = callbackFlow {
        val groupListener = FireStoreChatRef.getGroupDocument(groupId)
            .addSnapshotListener { value, error ->
                val data = value?.toObjectOrNull<FireStoreGroup>()
                onProducerScopeOffer(error, data)
            }
        awaitClose { groupListener.remove() }
    }

    override suspend fun removeGroup(groupId: String): Flow<Unit> = callbackFlow {
        FireStoreChatRef.getGroupDocument(groupId)
            .update(FIRE_STORE_GROUP_REMOVED, true)
            .addOnCompleteListener {
                onProducerScopeOffer(it.exception, Unit)
                close()
            }
    }

    override suspend fun updateChatGroupLastMessage(
        groupId: String,
        fireStoreMessage: FireStoreMessage
    ): Flow<Unit> = callbackFlow {
        FireStoreChatRef.getGroupDocument(groupId)
            .update(fireStoreMessage.getLastMessageUpdateMap())
            .addOnCompleteListener {
                onProducerScopeOffer(it.exception, Unit)
                close()
            }
    }

    private fun FireStoreMessage.getLastMessageUpdateMap() = mapOf(
        FIRE_STORE_GROUP_LAST_MESSAGE to this,
        FIRE_STORE_GROUP_REMOVED to false
    )

    override suspend fun markAllMessagesAsRead(
        groupId: String,
        members: List<FireStoreUser>
    ): Flow<Unit> = callbackFlow {

        FireStoreChatRef.getMessageMembersSeenAtDocument(groupId)
            .update(FIRE_STORE_GROUP_MEMBERS, members)
            .addOnCompleteListener {
                val myMember =
                    members.firstOrNull { member ->
                        member.uid == ChatFirebase.getFirebaseAuthUID()
                    }
                onProducerScopeOffer(it.exception, Unit)
                close()
            }
    }

    private fun observeFireStoreChannel(
        list: List<String>,
        emitter: ProducerScope<QuerySnapshot>
    ): List<ListenerRegistration> {
        val listeners = mutableListOf<ListenerRegistration>()
        for (i in 0..list.size / MAX_CHANNEL_SIZE) {
            val index = kotlin.math.min(MAX_CHANNEL_SIZE * (i + 1), list.size)
            val queryList = list.subList(MAX_CHANNEL_SIZE * i, index)
            observeGroupBy10(queryList, emitter)?.let {
                listeners.add(it)
            }
        }
        return listeners
    }

    private fun observeGroupBy10(
        groupIds: List<String>,
        emitter: ProducerScope<QuerySnapshot>
    ): ListenerRegistration? {
        if (groupIds.size > MAX_CHANNEL_SIZE || groupIds.isNullOrEmpty()) return null
        return FireStoreChatRef.getGroupCollection()
            .whereIn(FIRE_STORE_GROUP_ID, groupIds)
            .orderBy(
                "$FIRE_STORE_GROUP_LAST_MESSAGE.$FIRE_STORE_MESSAGE_CREATED_AT",
                Query.Direction.DESCENDING
            )
            .addSnapshotListener { value, error ->
                emitter.onProducerScopeOffer(error, value)
            }
    }

    companion object {
        const val MAX_CHANNEL_SIZE = 10
    }
}
