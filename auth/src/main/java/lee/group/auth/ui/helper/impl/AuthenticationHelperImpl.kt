/*
 * Created by do thanh long on 1/27/21 4:57 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 4:42 PM
 */

package lee.group.auth.ui.helper.impl

import android.app.Activity
import javax.inject.Inject
import lee.group.auth.ui.helper.AuthenticationHelper
import lee.group.auth.ui.helper.IAuth0Helper

internal class AuthenticationHelperImpl @Inject constructor(
    private val auth0Helper: IAuth0Helper
) : AuthenticationHelper {

    override fun loginByAuth0(
        activity: Activity,
        onSuccess: (auth: Any) -> Unit,
        onFailure: (ex: Exception) -> Unit
    ) {
        auth0Helper.login(activity, onSuccess, onFailure)
    }

    override fun logout(
        activity: Activity,
        onSuccess: () -> Unit,
        onFailure: (ex: Exception) -> Unit
    ) {
        auth0Helper.logout(activity, onSuccess, onFailure)
    }
}
