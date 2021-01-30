/*
 * Created by do thanh long on 1/28/21 3:25 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 3:25 PM
 */

package lee.group.auth.ui.helper.impl

import android.app.Activity
import android.app.Dialog
import com.auth0.android.Auth0Exception
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.provider.VoidCallback
import com.auth0.android.result.Credentials
import javax.inject.Inject
import lee.group.auth.data.auth0.Auth0Repository
import lee.group.auth.data.room.entity.toAuth0Credential
import lee.group.auth.di.ManualAuthModule
import lee.group.auth.ui.helper.IAuth0Helper

internal class Auth0HelperImpl @Inject constructor(
    private val auth0Repository: Auth0Repository
) : IAuth0Helper {

    override fun login(
        activity: Activity,
        onSuccess: (auth: Any) -> Unit,
        onFailure: (ex: Exception) -> Unit
    ) {
        with(activity) {
            ManualAuthModule.provideLoginWebAuthBuilder(this)
                .start(
                    activity,
                    object : AuthCallback {
                        override fun onFailure(dialog: Dialog) {
                            runOnUiThread { dialog.show() }
                        }

                        override fun onFailure(exception: AuthenticationException) {
                            onFailure.invoke(exception)
                        }

                        override fun onSuccess(credentials: Credentials) {
                            val auth0Credential = credentials.toAuth0Credential()
                            auth0Repository.saveAuth0Credential(auth0Credential)
                            onSuccess.invoke(auth0Credential)
                        }
                    }
                )
        }
    }

    override fun logout(
        activity: Activity,
        onSuccess: () -> Unit,
        onFailure: (ex: Exception) -> Unit
    ) {
        if (auth0Repository.isLogin()) {
            ManualAuthModule.provideLogoutWebAuthBuilder(activity)
                .start(
                    activity,
                    object : VoidCallback {
                        override fun onSuccess(payload: Void?) {
                            auth0Repository.cleanAuth0CredentialLocal()
                            onSuccess.invoke()
                        }

                        override fun onFailure(error: Auth0Exception) {
                            // Log out canceled, keep the user logged in
                            onFailure.invoke(error)
                        }
                    }
                )
        }
    }
}
