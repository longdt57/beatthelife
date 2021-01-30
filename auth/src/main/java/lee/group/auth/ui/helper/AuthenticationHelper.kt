/*
 * Created by do thanh long on 1/27/21 4:58 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 4:58 PM
 */

package lee.group.auth.ui.helper

import android.app.Activity

interface AuthenticationHelper {

    fun loginByAuth0(
        activity: Activity,
        onSuccess: (auth: Any) -> Unit,
        onFailure: (ex: Exception) -> Unit
    )

    fun logout(activity: Activity, onSuccess: () -> Unit, onFailure: (ex: Exception) -> Unit)
}
