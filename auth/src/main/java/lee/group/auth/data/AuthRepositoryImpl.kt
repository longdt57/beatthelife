/*
 * Created by do thanh long on 1/27/21 6:05 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 6:05 PM
 */

package lee.group.auth.data

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import lee.group.auth.data.auth0.Auth0Repository

internal class AuthRepositoryImpl @Inject constructor(
    private val auth0Repository: Auth0Repository
) : IAuthRepository {

    override fun isLogin(): Boolean {
        return auth0Repository.isLogin() || FirebaseAuth.getInstance().currentUser != null
    }
}
