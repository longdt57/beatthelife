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
import lee.group.auth.data.AuthRepositoryImpl
import lee.group.auth.data.IAuthRepository
import lee.group.auth.data.auth0.Auth0Repository
import lee.group.auth.data.auth0.AuthSecurePreferences
import lee.group.auth.ui.helper.AuthenticationHelper
import lee.group.auth.ui.helper.IAuth0Helper
import lee.group.auth.ui.helper.impl.Auth0HelperImpl
import lee.group.auth.ui.helper.impl.AuthenticationHelperImpl
import lee.group.core.data.secure.preference.IPreferences
import lee.group.core.data.secure.preference.SecurePreferences

@Module
@InstallIn(ActivityRetainedComponent::class)
internal class Auth0ProvidesModule {

    @AuthSecurePreferences
    @Provides
    fun provideAuthSecurePreferences(context: Context): IPreferences =
        SecurePreferences(context, AuthSecurePreferences.PREF_NAME)

    @Provides
    fun providesAuth(auth0Repository: Auth0Repository): IAuthRepository =
        AuthRepositoryImpl(auth0Repository)

    @Provides
    fun providesAuth0Helper(auth0Repository: Auth0Repository): IAuth0Helper =
        Auth0HelperImpl(auth0Repository)

    @Provides
    fun providesAuthenticationHelper(auth0Helper: IAuth0Helper): AuthenticationHelper =
        AuthenticationHelperImpl(auth0Helper)
}
