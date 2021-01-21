package one.module.chat.sdk.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import one.module.chat.sdk.data.firestore.IChatFirebaseAuth
import one.module.chat.sdk.data.firestore.IFireStoreChatGroupService
import one.module.chat.sdk.data.firestore.IFireStoreChatMessageService
import one.module.chat.sdk.data.firestore.IFireStoreChatUserService
import one.module.chat.sdk.data.firestore.impl.ChatFirebaseAuthImpl
import one.module.chat.sdk.data.firestore.impl.FireStoreChatGroupServiceImpl
import one.module.chat.sdk.data.firestore.impl.FireStoreChatMessageServiceImpl
import one.module.chat.sdk.data.firestore.impl.FireStoreChatUserServiceImpl

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

    @Binds
    abstract fun getOneChatFirebaseAuth(impl: ChatFirebaseAuthImpl): IChatFirebaseAuth
}
