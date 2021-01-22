package lee.group.chat.sdk.data.repo_impl

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import lee.group.chat.sdk.data.IChannelRepository
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatUserService
import lee.group.chat.sdk.data.firestore.error.InValidChatUserException
import lee.group.chat.sdk.data.firestore.model.FireStoreGroup
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.firestore.utils.getNotNullObjects
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.utils.FIRE_STORE_DEFAULT_REQUEST_TIMEOUT
import lee.group.chat.sdk.data.utils.toChannels

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
