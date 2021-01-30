/*
 * Created by do thanh long on 1/27/21 3:52 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 3:52 PM
 */

package lee.group.auth.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lee.group.auth.data.room.entity.Auth0Credential

@Dao
internal interface Auth0CredentialDao {

    @Query("Select * from Auth0Credential")
    fun getAllSync(): List<Auth0Credential>

    @Query("Select * from Auth0Credential")
    suspend fun getAll(): List<Auth0Credential>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuth0(credential: Auth0Credential)

    @Query("DELETE FROM Auth0Credential")
    suspend fun deleteAll()
}
