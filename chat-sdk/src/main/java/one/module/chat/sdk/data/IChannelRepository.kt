package one.module.chat.sdk.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import one.module.chat.sdk.data.model.channel.ChatChannel

interface IChannelRepository {

    fun getAllChannels(): Single<List<ChatChannel>>

    fun observeAllChannels(): Observable<List<ChatChannel>>

    fun fetchNextChannels()

    fun removeChannelsObserver()

    fun removeChannel(groupId: String): Completable
}
