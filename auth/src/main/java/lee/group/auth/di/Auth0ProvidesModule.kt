/*
 * Created by do thanh long on 1/27/21 4:35 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 4:35 PM
 */

package lee.group.auth.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import lee.group.auth.data.auth0.AuthSecurePreferences
import lee.group.core.data.secure.preference.IPreferences
import lee.group.core.data.secure.preference.SecurePreferences

@Module
@InstallIn(ActivityRetainedComponent::class)
internal class Auth0ProvidesModule {

    @AuthSecurePreferences
    @Provides
    fun provideAuthSecurePreferences(context: Context): IPreferences =
        SecurePreferences(context, AuthSecurePreferences.PREF_NAME)
}
