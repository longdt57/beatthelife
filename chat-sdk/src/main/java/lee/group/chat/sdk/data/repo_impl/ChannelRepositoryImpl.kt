package lee.group.chat.sdk.data.repo_impl

import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import lee.group.chat.sdk.data.IChannelRepository
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatUserService
import lee.group.chat.sdk.data.firestore.model.FireStoreGroup
import lee.group.chat.sdk.data.firestore.utils.toObjectsOrEmpty
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.network.ChatService
import lee.group.chat.sdk.data.network.model.AddMessageResponse
import lee.group.chat.sdk.data.utils.toChannels
import lee.group.core.data.BaseResult
import lee.group.core.data.toResult

@FlowPreview
internal class ChannelRepositoryImpl @Inject constructor(
    private val chatUserFireStore: IFireStoreChatUserService,
    private val chatGroupFireStore: IFireStoreChatGroupService,
    private val chatService: ChatService
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

    override suspend fun addMessage(message: String): BaseResult<AddMessageResponse> =
        withContext(Dispatchers.IO) {
            try {
                chatService.addMessage(message).data.toResult()
            } catch (e: Exception) {
                e.toResult()
            }
        }
}
