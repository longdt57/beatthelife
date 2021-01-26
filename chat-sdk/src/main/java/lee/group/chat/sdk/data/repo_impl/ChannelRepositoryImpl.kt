package lee.group.chat.sdk.data.repo_impl

import javax.inject.Inject
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import lee.group.chat.sdk.data.IChannelRepository
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatUserService
import lee.group.chat.sdk.data.firestore.error.InValidChatUserException
import lee.group.chat.sdk.data.firestore.model.FireStoreGroup
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.firestore.utils.toObjectsOrEmpty
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.utils.toChannels

@FlowPreview
internal class ChannelRepositoryImpl @Inject constructor(
    private val chatUserFireStore: IFireStoreChatUserService,
    private val chatGroupFireStore: IFireStoreChatGroupService
) : IChannelRepository {

    override suspend fun getAllChannels(): Flow<List<ChatChannel>> {
        return chatUserFireStore.getCurrentUser()
            .flatMapConcat { chatGroupFireStore.getGroups(it.groups.orEmpty()) }
            .map { it.toChannels() }
    }

    override suspend fun observeAllChannels(): Flow<List<ChatChannel>> {
        return chatUserFireStore.observeCurrentUser()
            .flatMapConcat { chatGroupFireStore.observeGroups(it.groups.orEmpty()) }
            .map { it.toObjectsOrEmpty<FireStoreGroup>().toChannels() }
    }

    override suspend fun fetchNextChannels() {
    }

    override suspend fun removeChannel(
        groupId: String
    ): Flow<Unit> = chatGroupFireStore.removeGroup(groupId)

    /**     Internal fun **/

    private fun getCurrentChatUserId(): String {
        return ChatFirebase.getFirebaseAuthUID() ?: throw InValidChatUserException()
    }
}
