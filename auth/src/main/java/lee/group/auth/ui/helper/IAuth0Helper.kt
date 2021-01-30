/*
 * Created by do thanh long on 1/28/21 3:25 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 3:25 PM
 */

package lee.group.auth.ui.helper

import android.app.Activity

internal interface IAuth0Helper {

    fun login(
        activity: Activity,
        onSuccess: (auth: Any) -> Unit,
        onFailure: (ex: Exception) -> Unit
    )

    fun logout(activity: Activity, onSuccess: () -> Unit, onFailure: (ex: Exception) -> Unit)
}
