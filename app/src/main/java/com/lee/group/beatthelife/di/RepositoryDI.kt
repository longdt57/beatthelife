package com.lee.group.beatthelife.di

import com.lee.group.beatthelife.data.IBeoURepository
import com.lee.group.beatthelife.data.impl.BeoURepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class RepositoryDI {

    @Binds
    abstract fun bindBeoURepository(
        beoURepositoryImpl: BeoURepositoryImpl
    ): IBeoURepository
}
