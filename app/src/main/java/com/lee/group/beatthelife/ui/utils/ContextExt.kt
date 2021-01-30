package com.lee.group.beatthelife.ui.utils

import android.app.Activity
import android.content.Intent
import com.lee.group.beatthelife.MainActivity
import com.lee.group.beatthelife.ui.onboarding.OnBoardingActivity

internal fun Activity.redirectToOnBoardingScreen() {
    startActivity(Intent(this, OnBoardingActivity::class.java))
}

internal fun Activity.redirectToMainScreen() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
}

internal fun setupTrackerUserId() {
//    val userId = UserManager.getCurrentUserId().orEmpty()
//    TrackerSDK.setUserId(userId)
//    FirebaseCrashlytics.getInstance().setUserId(userId)
}
