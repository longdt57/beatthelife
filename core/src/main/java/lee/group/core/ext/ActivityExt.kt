/*
 * Created by do thanh long on 1/28/21 5:23 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/28/21 5:23 PM
 */

package lee.group.core.ext

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.getActivityResultLauncher(
    onSuccess: (result: Intent?) -> Unit,
    onFailure: (ex: Exception?) -> Unit
): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            onSuccess.invoke(result.data)
        } else {
            onFailure.invoke(null)
        }
    }
}
