/*
 * Created by do thanh long on 1/27/21 5:05 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 5:05 PM
 */

package lee.group.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import lee.group.auth.data.AuthRepositoryImpl
import lee.group.auth.data.IAuthRepository
import lee.group.auth.data.auth0.Auth0Repository
import lee.group.auth.data.auth0.impl.Auth0RepositoryImpl
import lee.group.auth.data.firebase.FirebaseAuthImpl
import lee.group.auth.data.firebase.IFirebaseAuth
import lee.group.auth.ui.helper.AuthenticationHelper
import lee.group.auth.ui.helper.IAuth0Helper
import lee.group.auth.ui.helper.impl.Auth0HelperImpl
import lee.group.auth.ui.helper.impl.AuthenticationHelperImpl

@Module
@InstallIn(ActivityRetainedComponent::class)
internal abstract class AuthModule {

    @Binds
    abstract fun bindBeoURepository(
        firebaseAuthImpl: FirebaseAuthImpl
    ): IFirebaseAuth

    @Binds
    abstract fun providesAuth0Repository(
        impl: Auth0RepositoryImpl
    ): Auth0Repository

    @Binds
    abstract fun providesAuth(impl: AuthRepositoryImpl): IAuthRepository

    @Binds
    abstract fun providesAuth0Helper(impl: Auth0HelperImpl): IAuth0Helper

    @Binds
    abstract fun providesAuthenticationHelper(impl: AuthenticationHelperImpl): AuthenticationHelper
}
