package lee.group.chat.sdk.data.firestore

import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.firestore.model.FireStoreUser

internal interface IFireStoreChatUserService {

    suspend fun getCurrentUser(): Flow<FireStoreUser>

    suspend fun observeCurrentUser(): Flow<FireStoreUser>
}
