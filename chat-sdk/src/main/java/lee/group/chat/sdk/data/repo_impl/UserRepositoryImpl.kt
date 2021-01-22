package lee.group.chat.sdk.data.repo_impl

import io.reactivex.Completable
import io.reactivex.Observable
import lee.group.chat.sdk.data.IUserRepository
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatUserService
import lee.group.chat.sdk.data.firestore.error.InValidChatUserException
import lee.group.chat.sdk.data.firestore.utils.ChatFirebase
import lee.group.chat.sdk.data.model.ChatUser
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.utils.toChannel
import lee.group.chat.sdk.data.utils.toChatUser

internal class UserRepositoryImpl constructor(
    private val chatUserFireStore: IFireStoreChatUserService,
    private val chatGroupFireStore: IFireStoreChatGroupService
) : IUserRepository {

    override fun observeChannel(groupID: String): Observable<ChatChannel> {
        return chatGroupFireStore.observeGroup(groupID)
            .map { it.toChannel() }
    }

    override fun removeChannel(groupID: String): Completable {
        return chatGroupFireStore.removeGroup(groupID)
    }

    override fun observeCurrentUser(): Observable<ChatUser> {
        val currentUserId = ChatFirebase.getFirebaseAuthUID()
        val userId = currentUserId ?: return Observable.error(InValidChatUserException())
        return chatUserFireStore.observeUser(userId)
            .map { it.toChatUser() }
    }
}
