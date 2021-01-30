/*
 * Created by do thanh long on 1/28/21 10:33 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 9:56 AM
 */

package lee.group.auth.data.auth0.impl

import javax.inject.Inject
import lee.group.auth.data.auth0.Auth0Repository
import lee.group.auth.data.auth0.AuthSecurePreferences
import lee.group.auth.data.room.AuthRoomConst.ACCESS_TOKEN
import lee.group.auth.data.room.AuthRoomConst.EXPIRES_AT
import lee.group.auth.data.room.AuthRoomConst.ID_TOKEN
import lee.group.auth.data.room.AuthRoomConst.REFRESH_TOKEN
import lee.group.auth.data.room.AuthRoomConst.SCOPE
import lee.group.auth.data.room.AuthRoomConst.TOKEN_TYPE
import lee.group.auth.data.room.entity.Auth0Credential
import lee.group.core.data.secure.preference.IPreferences

internal class Auth0RepositoryImpl @Inject constructor(
    @AuthSecurePreferences private val iPreferences: IPreferences
) : Auth0Repository {

    override fun isLogin(): Boolean {
        val credential = getAuth0CredentialLocal()
        return isExpired(credential.expiresAt).not()
    }

    override fun saveAuth0Credential(auth0Credential: Auth0Credential) {
        auth0Credential.accessToken?.let { iPreferences.setString(ACCESS_TOKEN, it) }
        auth0Credential.type?.let { iPreferences.setString(TOKEN_TYPE, it) }
        auth0Credential.idToken.let { iPreferences.setString(ID_TOKEN, it) }
        auth0Credential.refreshToken?.let { iPreferences.setString(REFRESH_TOKEN, it) }
        auth0Credential.scope?.let { iPreferences.setString(SCOPE, it) }
        auth0Credential.expiresAt?.let { iPreferences.setLong(EXPIRES_AT, it) }
    }

    override fun getAuth0CredentialLocal(): Auth0Credential {
        return Auth0Credential(
            accessToken = iPreferences.getString(ACCESS_TOKEN),
            type = iPreferences.getString(TOKEN_TYPE),
            idToken = iPreferences.getString(ID_TOKEN).orEmpty(),
            refreshToken = iPreferences.getString(REFRESH_TOKEN),
            expiresAt = iPreferences.getLong(EXPIRES_AT),
            scope = iPreferences.getString(ACCESS_TOKEN),
        )
    }

    override fun cleanAuth0CredentialLocal() {
        iPreferences.clear()
    }

    private fun isExpired(secondTime: Long?): Boolean {
        return secondTime == null || secondTime < System.currentTimeMillis() / 1000
    }
}
