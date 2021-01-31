/*
 * Created by do thanh long on 1/31/21 7:57 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/31/21 7:57 PM
 */

package lee.group.core.ext

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import android.widget.Toast

@SuppressLint("HardwareIds")
fun Context.getDeviceId(): String {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}

fun Context.getUserAgent(): String {
    val packageName = packageName.replace("com.lee.group.", "")
    val version = packageManager.getPackageInfo(packageName, 0).versionName
    val userAgent = System.getProperty("http.agent")
    return "$packageName/$version $userAgent"
}

fun Context.toast(message: CharSequence, isLengthLong: Boolean = true) {
    Toast.makeText(
        this, message,
        if (isLengthLong) {
            Toast.LENGTH_LONG
        } else {
            Toast.LENGTH_SHORT
        }
    ).show()
}
