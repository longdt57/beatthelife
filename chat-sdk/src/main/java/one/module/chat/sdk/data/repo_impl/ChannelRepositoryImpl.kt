package one.module.chat.sdk.data.repo_impl

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import one.module.chat.sdk.data.IChannelRepository
import one.module.chat.sdk.data.firestore.IFireStoreChatGroupService
import one.module.chat.sdk.data.firestore.IFireStoreChatUserService
import one.module.chat.sdk.data.firestore.error.InValidChatUserException
import one.module.chat.sdk.data.firestore.model.FireStoreGroup
import one.module.chat.sdk.data.firestore.utils.ChatFirebase
import one.module.chat.sdk.data.firestore.utils.getNotNullObjects
import one.module.chat.sdk.data.model.channel.ChatChannel
import one.module.chat.sdk.data.utils.FIRE_STORE_DEFAULT_REQUEST_TIMEOUT
import one.module.chat.sdk.data.utils.toChannels

internal class ChannelRepositoryImpl constructor(
    private val chatUserFireStore: IFireStoreChatUserService,
    private val chatGroupFireStore: IFireStoreChatGroupService
) : IChannelRepository {

    override fun getAllChannels(): Single<List<ChatChannel>> {
        val userId = getCurrentChatUserId()
        return chatUserFireStore.getUser(userId)
            .flatMap { chatGroupFireStore.getGroups(it.groups.orEmpty()) }
            .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)
            .map {
                it.toChannels()
            }
    }

    override fun observeAllChannels(): Observable<List<ChatChannel>> {
        val userId = getCurrentChatUserId()
        return chatUserFireStore.observeUser(userId)
            .flatMap { chatGroupFireStore.observeGroups(it.groups.orEmpty()) }
            .map { it.getNotNullObjects<FireStoreGroup>().toChannels() }
    }

    override fun fetchNextChannels() {
    }

    override fun removeChannelsObserver() {
        chatGroupFireStore.removeAllGroupsObserver()
        chatUserFireStore.removeUserObserver()
    }

    override fun removeChannel(
        groupId: String
    ): Completable = chatGroupFireStore.removeGroup(groupId)
        .timeout(FIRE_STORE_DEFAULT_REQUEST_TIMEOUT, TimeUnit.MILLISECONDS)

    /**     Internal fun **/

    private fun getCurrentChatUserId(): String {
        return ChatFirebase.getFirebaseAuthUID() ?: throw InValidChatUserException()
    }
}
