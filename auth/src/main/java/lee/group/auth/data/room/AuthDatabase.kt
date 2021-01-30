/*
 * Created by do thanh long on 1/27/21 4:14 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 4:14 PM
 */

package lee.group.auth.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import lee.group.auth.data.room.dao.Auth0CredentialDao
import lee.group.auth.data.room.entity.Auth0Credential

@Database(entities = [Auth0Credential::class], version = 2)
internal abstract class AuthDatabase : RoomDatabase() {

    abstract fun auth0CredentialDao(): Auth0CredentialDao
}
