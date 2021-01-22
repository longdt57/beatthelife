package lee.group.chat.sdk.data.firestore

import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Completable
import io.reactivex.Observable
import lee.group.chat.sdk.data.firestore.model.FireStoreMessage

internal interface IFireStoreChatMessageService {

    fun getPreviousMessages(groupId: String, limit: Long, time: Long): Observable<QuerySnapshot>

    fun getNextMessages(groupId: String, limit: Long, time: Long): Observable<QuerySnapshot>

    fun sendMessage(groupId: String, fireStoreMessage: FireStoreMessage): Completable

    fun observeNewMessages(groupId: String): Observable<QuerySnapshot>

    fun removeMessagesObserver()

    fun removeMessage(groupId: String, messageId: String): Completable
}
