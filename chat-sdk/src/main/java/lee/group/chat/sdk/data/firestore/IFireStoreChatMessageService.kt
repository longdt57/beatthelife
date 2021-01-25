package lee.group.chat.sdk.data.firestore

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.firestore.model.FireStoreMessage

internal interface IFireStoreChatMessageService {

    suspend fun getPreviousMessages(groupId: String, limit: Long, time: Long): Flow<QuerySnapshot>

    suspend fun getNextMessages(groupId: String, limit: Long, time: Long): Flow<QuerySnapshot>

    suspend fun sendMessage(groupId: String, fireStoreMessage: FireStoreMessage): Flow<Unit>

    suspend fun observeNewMessages(groupId: String): Flow<QuerySnapshot>

    suspend fun removeMessagesObserver()

    suspend fun removeMessage(groupId: String, messageId: String): Flow<Unit>
}
