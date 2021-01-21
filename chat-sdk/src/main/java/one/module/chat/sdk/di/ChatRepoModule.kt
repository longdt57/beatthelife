package one.module.chat.sdk.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import one.module.chat.sdk.data.IChannelRepository
import one.module.chat.sdk.data.IMessageRepository
import one.module.chat.sdk.data.IUserRepository
import one.module.chat.sdk.data.repo_impl.ChannelRepositoryImpl
import one.module.chat.sdk.data.repo_impl.MessageRepositoryImpl
import one.module.chat.sdk.data.repo_impl.UserRepositoryImpl

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
