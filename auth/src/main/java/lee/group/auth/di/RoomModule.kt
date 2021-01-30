/*
 * Created by do thanh long on 1/27/21 4:11 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 4:11 PM
 */

package lee.group.auth.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lee.group.auth.data.room.AuthDatabase
import lee.group.auth.data.room.AuthRoomConst.AUTH_DATABASE_NAME
import lee.group.auth.data.room.AuthRoomConst.AUTH_PASS_PHRASE
import lee.group.auth.data.room.dao.Auth0CredentialDao
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Module
@InstallIn(SingletonComponent::class)
internal class RoomModule {

    @Provides
    fun provideRoom(context: Context): AuthDatabase {
        val builder = Room.databaseBuilder(
            context.applicationContext,
            AuthDatabase::class.java, AUTH_DATABASE_NAME
        )
        val factory = SupportFactory(SQLiteDatabase.getBytes(AUTH_PASS_PHRASE.toCharArray()))

        return builder.openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideAuth0CredentialDao(authDatabase: AuthDatabase): Auth0CredentialDao =
        authDatabase.auth0CredentialDao()
}
