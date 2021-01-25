package lee.group.chat.sdk.data.repo_impl

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import lee.group.chat.sdk.data.IUserRepository
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatUserService
import lee.group.chat.sdk.data.model.ChatUser
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.utils.toChannel
import lee.group.chat.sdk.data.utils.toChatUser

internal class UserRepositoryImpl @Inject constructor(
    private val chatUserFireStore: IFireStoreChatUserService,
    private val chatGroupFireStore: IFireStoreChatGroupService
) : IUserRepository {

    override suspend fun observeChannel(groupID: String): Flow<ChatChannel> {
        return chatGroupFireStore.observeGroup(groupID)
            .map { it.toChannel() }
    }

    override suspend fun removeChannel(groupID: String): Flow<Unit> {
        return chatGroupFireStore.removeGroup(groupID)
    }

    override suspend fun observeCurrentUser(): Flow<ChatUser> {
        return chatUserFireStore.observeCurrentUser()
            .map { it.toChatUser() }
    }
}
