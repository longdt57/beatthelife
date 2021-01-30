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
import lee.group.auth.data.auth0.Auth0Repository
import lee.group.auth.data.auth0.impl.Auth0RepositoryImpl
import lee.group.auth.data.firebase.FirebaseAuthImpl
import lee.group.auth.data.firebase.IFirebaseAuth

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
}
