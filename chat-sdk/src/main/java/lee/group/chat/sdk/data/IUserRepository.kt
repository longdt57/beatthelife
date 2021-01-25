package lee.group.chat.sdk.data

import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.model.ChatUser
import lee.group.chat.sdk.data.model.channel.ChatChannel

interface IUserRepository {

    suspend fun observeChannel(groupID: String): Flow<ChatChannel>

    suspend fun removeChannel(groupID: String): Flow<Unit>

    suspend fun observeCurrentUser(): Flow<ChatUser>
}
