package lee.group.chat.sdk.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import lee.group.chat.sdk.data.firestore.IFireStoreChatGroupService
import lee.group.chat.sdk.data.firestore.IFireStoreChatMessageService
import lee.group.chat.sdk.data.firestore.IFireStoreChatUserService
import lee.group.chat.sdk.data.firestore.impl.FireStoreChatGroupServiceImpl
import lee.group.chat.sdk.data.firestore.impl.FireStoreChatMessageServiceImpl
import lee.group.chat.sdk.data.firestore.impl.FireStoreChatUserServiceImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class FireStoreRepoModule {

    @Binds
    abstract fun getFireStoreChatGroupService(
        impl: FireStoreChatGroupServiceImpl
    ): IFireStoreChatGroupService

    @Binds
    abstract fun getFireStoreChatMessageService(
        impl: FireStoreChatMessageServiceImpl
    ): IFireStoreChatMessageService

    @Binds
    abstract fun getFireStoreChatUserService(
        impl: FireStoreChatUserServiceImpl
    ): IFireStoreChatUserService
}
