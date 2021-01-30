/*
 * Created by do thanh long on 1/28/21 10:10 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 10:10 AM
 */

package lee.group.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import lee.group.core.data.secure.preference.IPreferences
import lee.group.core.data.secure.preference.SecurePreferences

@Module
@InstallIn(ActivityRetainedComponent::class)
internal class DataModule {

    @Provides
    fun provideSecurePreferences(context: Context): IPreferences = SecurePreferences(context)
}
