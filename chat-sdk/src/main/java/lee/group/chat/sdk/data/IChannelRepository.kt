package lee.group.chat.sdk.data

import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import lee.group.chat.sdk.data.model.channel.ChatChannel

interface IChannelRepository {

    fun getAllChannels(): Single<List<ChatChannel>>

    suspend fun observeAllChannels(): Flow<List<ChatChannel>>

    fun fetchNextChannels()

    fun removeChannelsObserver()

    fun removeChannel(groupId: String): Completable
}
