package com.lee.group.beatthelife.di

import com.lee.group.beatthelife.data.IEventRepository
import com.lee.group.beatthelife.data.impl.EventRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindEventRepository(
        impl: EventRepositoryImpl
    ): IEventRepository
}
