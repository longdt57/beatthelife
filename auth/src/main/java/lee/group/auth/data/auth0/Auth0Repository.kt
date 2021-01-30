/*
 * Created by do thanh long on 1/27/21 5:02 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 5:02 PM
 */

package lee.group.auth.data.auth0

import lee.group.auth.data.room.entity.Auth0Credential

interface Auth0Repository {

    fun isLogin(): Boolean

    fun saveAuth0Credential(auth0Credential: Auth0Credential)

    fun getAuth0CredentialLocal(): Auth0Credential

    fun cleanAuth0CredentialLocal()
}
