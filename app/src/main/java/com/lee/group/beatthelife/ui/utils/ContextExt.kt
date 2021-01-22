package com.lee.group.beatthelife.ui.utils

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.lee.group.beatthelife.MainActivity
import com.lee.group.beatthelife.R
import com.lee.group.beatthelife.data.UserManager
import com.lee.group.beatthelife.ui.onboarding.OnBoardingActivity
import lee.group.tracking.TrackerSDK

internal fun getSignInIntent(): Intent {
    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
        AuthUI.IdpConfig.EmailBuilder().build()
    )

    // Create and launch sign-in intent
    return AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(R.mipmap.ic_launcher_round)
        .build()
}

internal fun AppCompatActivity.getActivityResultLauncher(
    onSuccess: () -> Unit,
    onFailed: () -> Unit
): ActivityResultLauncher<Intent> {
    return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            onSuccess.invoke()
        } else {
            onFailed.invoke()
        }
    }
}

internal fun Activity.redirectToSignInScreen() {
    finish()
    startActivity(Intent(this, OnBoardingActivity::class.java))
}

internal fun Activity.redirectToMainScreen() {
    finish()
    startActivity(Intent(this, MainActivity::class.java))
}

internal fun checkAuthentication(
    signedInCallback: () -> Unit = {},
    unSignedInCallback: () -> Unit = {}
) {
    if (UserManager.isLogin()) {
        signedInCallback.invoke()
    } else {
        unSignedInCallback.invoke()
    }
}

internal fun setupTrackerUserId() {
    val userId = UserManager.getCurrentUserId().orEmpty()
    TrackerSDK.setUserId(userId)
    FirebaseCrashlytics.getInstance().setUserId(userId)
}
