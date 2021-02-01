/*
 * Created by do thanh long on 2/1/21 9:51 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 2/1/21 9:51 AM
 */

package lee.group.chat.sdk.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import lee.group.chat.sdk.data.network.ChatService
import lee.group.chat.sdk.data.network.ChatServiceNetwork

@Module
@InstallIn(ActivityRetainedComponent::class)
class ChatProvidesModule {

    @Provides
    fun providesChatService(
        chatServiceNetwork: ChatServiceNetwork
    ): ChatService {
        return chatServiceNetwork.create(ChatService::class.java)
    }
}
