package lee.group.chat.sdk.data

import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.network.model.AddMessageResponse
import lee.group.core.data.BaseResult

interface IChannelRepository {

    suspend fun getAllChannels(): Flow<List<ChatChannel>>

    suspend fun observeAllChannels(): Flow<List<ChatChannel>>

    suspend fun fetchNextChannels()

    suspend fun removeChannel(groupId: String): Flow<Unit>

    suspend fun addMessage(message: String): BaseResult<AddMessageResponse>
}
