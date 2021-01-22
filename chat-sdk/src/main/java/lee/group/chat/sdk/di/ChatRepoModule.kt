package lee.group.chat.sdk.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import lee.group.chat.sdk.data.IChannelRepository
import lee.group.chat.sdk.data.IMessageRepository
import lee.group.chat.sdk.data.IUserRepository
import lee.group.chat.sdk.data.repo_impl.ChannelRepositoryImpl
import lee.group.chat.sdk.data.repo_impl.MessageRepositoryImpl
import lee.group.chat.sdk.data.repo_impl.UserRepositoryImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class ChatRepoModule {

    @Binds
    abstract fun getMessageRepository(impl: MessageRepositoryImpl): IMessageRepository

    @Binds
    abstract fun getChannelRepository(impl: ChannelRepositoryImpl): IChannelRepository

    @Binds
    abstract fun getChatProfileRepository(impl: UserRepositoryImpl): IUserRepository
}
