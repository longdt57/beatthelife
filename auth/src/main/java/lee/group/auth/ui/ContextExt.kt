package lee.group.auth.ui

import android.content.Intent
import com.firebase.ui.auth.AuthUI

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
        .build()
}

internal fun setupTrackerUserId() {
//    val userId = UserManager.getCurrentUserId().orEmpty()
//    TrackerSDK.setUserId(userId)
//    FirebaseCrashlytics.getInstance().setUserId(userId)
}
