package lee.group.chat.sdk.data.firestore.impl

import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import lee.group.chat.sdk.data.firestore.IFireStoreChatMessageService
import lee.group.chat.sdk.data.firestore.model.FireStoreMessage
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_CREATED_AT
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_LISTEN_LIMIT
import lee.group.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_REMOVED
import lee.group.chat.sdk.data.firestore.utils.FireStoreChatRef
import lee.group.chat.sdk.data.firestore.utils.onProducerScopeOffer

@ExperimentalCoroutinesApi
internal class FireStoreChatMessageServiceImpl @Inject constructor() :
    IFireStoreChatMessageService {

    private var messagesListener: MutableList<ListenerRegistration> = mutableListOf()

    override suspend fun getPreviousMessages(
        groupId: String,
        limit: Long,
        time: Long
    ): Flow<QuerySnapshot> = callbackFlow {
        FireStoreChatRef.getMessageCollection(groupId)
            .whereLessThan(FIRE_STORE_MESSAGE_CREATED_AT, time)
            .addMessagesQuery(this, limit)
    }

    override suspend fun getNextMessages(
        groupId: String,
        limit: Long,
        time: Long
    ): Flow<QuerySnapshot> = callbackFlow {
        FireStoreChatRef.getMessageCollection(groupId)
            .whereGreaterThan(FIRE_STORE_MESSAGE_CREATED_AT, time)
            .addMessagesQuery(this, limit)
    }

    override suspend fun sendMessage(
        groupId: String,
        fireStoreMessage: FireStoreMessage
    ): Flow<Unit> = callbackFlow {
        FireStoreChatRef.getMessageDocument(groupId, fireStoreMessage.message_id)
            .set(fireStoreMessage)
            .addOnSuccessListener {
                onProducerScopeOffer(null, Unit)
                close()
            }
            .addOnFailureListener { error ->
                onProducerScopeOffer(error, Unit)
            }
    }

    override suspend fun observeNewMessages(groupId: String): Flow<QuerySnapshot> = callbackFlow {
        FireStoreChatRef.getMessageCollection(groupId)
            .addMessagesQuery(this, FIRE_STORE_MESSAGE_LISTEN_LIMIT)
    }

    private fun Query.addMessagesQuery(
        emitter: ProducerScope<QuerySnapshot>,
        limit: Long
    ) {
        return this
            .orderBy(FIRE_STORE_MESSAGE_CREATED_AT, Query.Direction.DESCENDING)
            .limit(limit)
            .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
                emitter.onProducerScopeOffer(error, value)
            }.let {
                messagesListener.add(it)
            }
    }

    override suspend fun removeMessagesObserver() {
        messagesListener.forEach { item ->
            item.remove()
        }
        messagesListener.clear()
    }

    override suspend fun removeMessage(groupId: String, messageId: String): Flow<Unit> =
        callbackFlow {
            FireStoreChatRef.getMessageDocument(groupId, messageId)
                .update(FIRE_STORE_MESSAGE_REMOVED, true)
                .addOnSuccessListener {
                    onProducerScopeOffer(null, Unit)
                    close()
                }
                .addOnFailureListener { error ->
                    onProducerScopeOffer(error, null)
                }
        }
}
