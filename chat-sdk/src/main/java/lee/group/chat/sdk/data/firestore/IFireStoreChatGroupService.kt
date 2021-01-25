package lee.group.chat.sdk.data.firestore

import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.firestore.model.FireStoreGroup
import lee.group.chat.sdk.data.firestore.model.FireStoreMessage
import lee.group.chat.sdk.data.firestore.model.FireStoreUser

internal interface IFireStoreChatGroupService {

    suspend fun getGroup(groupId: String): Flow<FireStoreGroup>

    suspend fun getGroups(groupIds: List<String>): Flow<List<FireStoreGroup>>

    suspend fun observeGroup(groupId: String): Flow<FireStoreGroup>

    suspend fun observeGroups(groupIds: List<String>): Flow<QuerySnapshot>

    suspend fun updateChatGroupLastMessage(
        groupId: String,
        fireStoreMessage: FireStoreMessage
    ): Flow<Unit>

    suspend fun removeAllGroupsObserver()

    suspend fun removeLastGroupObserver()

    suspend fun removeGroup(groupId: String): Flow<Unit>

    suspend fun markAllMessagesAsRead(
        groupId: String,
        members: List<FireStoreUser>
    ): Flow<Unit>
}
