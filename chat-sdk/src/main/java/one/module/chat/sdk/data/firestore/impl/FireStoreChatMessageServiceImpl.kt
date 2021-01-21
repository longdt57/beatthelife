package one.module.chat.sdk.data.firestore.impl

import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import one.module.chat.sdk.data.firestore.IFireStoreChatMessageService
import one.module.chat.sdk.data.firestore.model.FireStoreMessage
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_CREATED_AT
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_LISTEN_LIMIT
import one.module.chat.sdk.data.firestore.utils.FIRE_STORE_MESSAGE_REMOVED
import one.module.chat.sdk.data.firestore.utils.FireStoreChatRef
import one.module.chat.sdk.data.firestore.utils.onRxCompletableListener
import one.module.chat.sdk.data.firestore.utils.onRxObservableListener

internal class FireStoreChatMessageServiceImpl : IFireStoreChatMessageService {

    private var messagesListener: MutableList<ListenerRegistration> = mutableListOf()

    override fun getPreviousMessages(
        groupId: String,
        limit: Long,
        time: Long
    ): Observable<QuerySnapshot> {
        return Observable.create { emitter ->
            FireStoreChatRef.getMessageCollection(groupId)
                .whereLessThan(FIRE_STORE_MESSAGE_CREATED_AT, time)
                .addMessagesQuery(emitter, limit)
        }
    }

    override fun getNextMessages(
        groupId: String,
        limit: Long,
        time: Long
    ): Observable<QuerySnapshot> {
        return Observable.create { emitter ->
            FireStoreChatRef.getMessageCollection(groupId)
                .whereGreaterThan(FIRE_STORE_MESSAGE_CREATED_AT, time)
                .addMessagesQuery(emitter, limit)
        }
    }

    override fun sendMessage(groupId: String, fireStoreMessage: FireStoreMessage): Completable {
        return Completable.create { emitter ->
            FireStoreChatRef.getMessageDocument(groupId, fireStoreMessage.message_id)
                .set(fireStoreMessage)
                .addOnSuccessListener {
                    emitter.onRxCompletableListener(null)
                }
                .addOnFailureListener { error ->
                    emitter.onRxCompletableListener(error)
                }
        }
    }

    override fun observeNewMessages(groupId: String): Observable<QuerySnapshot> {
        return Observable.create { emitter ->
            FireStoreChatRef.getMessageCollection(groupId)
                .addMessagesQuery(emitter, FIRE_STORE_MESSAGE_LISTEN_LIMIT)
        }
    }

    private fun Query.addMessagesQuery(emitter: ObservableEmitter<QuerySnapshot>, limit: Long) {
        return this
            .orderBy(FIRE_STORE_MESSAGE_CREATED_AT, Query.Direction.DESCENDING)
            .limit(limit)
            .addSnapshotListener(MetadataChanges.INCLUDE) { value, error ->
                emitter.onRxObservableListener(error, value)
            }.let {
                messagesListener.add(it)
            }
    }

    override fun removeMessagesObserver() {
        messagesListener.forEach { item ->
            item?.remove()
        }
        messagesListener.clear()
    }

    override fun removeMessage(groupId: String, messageId: String) = Completable.create { emitter ->
        FireStoreChatRef.getMessageDocument(groupId, messageId)
            .update(FIRE_STORE_MESSAGE_REMOVED, true)
            .addOnSuccessListener {
                emitter.onRxCompletableListener(null)
            }
            .addOnFailureListener { error ->
                emitter.onRxCompletableListener(error)
            }
    }
}
