package com.lee.group.beatthelife.ui

import android.content.Context
import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.lee.group.beatthelife.R

internal fun Context.getFirebaseUISignInIntent(): Intent {
    // Choose authentication providers
    val providers = arrayListOf(
        AuthUI.IdpConfig.GoogleBuilder().build(),
        AuthUI.IdpConfig.PhoneBuilder().build()
    )

    // Create and launch sign-in intent
    return AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(R.mipmap.ic_launcher_round)
        .build()
}
