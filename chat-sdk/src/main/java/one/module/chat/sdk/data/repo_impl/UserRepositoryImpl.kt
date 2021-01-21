package one.module.chat.sdk.data.repo_impl

import io.reactivex.Completable
import io.reactivex.Observable
import one.module.chat.sdk.data.IUserRepository
import one.module.chat.sdk.data.firestore.IFireStoreChatGroupService
import one.module.chat.sdk.data.firestore.IFireStoreChatUserService
import one.module.chat.sdk.data.firestore.error.InValidChatUserException
import one.module.chat.sdk.data.firestore.utils.ChatFirebase
import one.module.chat.sdk.data.model.ChatUser
import one.module.chat.sdk.data.model.channel.ChatChannel
import one.module.chat.sdk.data.utils.toChannel
import one.module.chat.sdk.data.utils.toChatUser

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
