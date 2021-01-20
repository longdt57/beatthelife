package com.lee.group.beatthelife.ui.onboarding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.lee.group.beatthelife.R
import com.lee.group.beatthelife.base.BaseBindingActivity
import com.lee.group.beatthelife.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseBindingActivity<ActivityOnboardingBinding, OnBoardingViewModel>() {

    override val binding: ActivityOnboardingBinding
    by lazy { ActivityOnboardingBinding.inflate(layoutInflater) }

    override val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initUI()

        startAuthentication()
    }

    private fun initUI() {
        binding.btnSignIn.setOnClickListener {
            openFirebaseUISignIn()
        }
    }

    private fun startAuthentication() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            onAuthenticationSuccess(firebaseAuth.currentUser!!)
        }
    }

    private fun onAuthenticationSuccess(user: FirebaseUser) {
        binding.userId.text = user.uid
        FirebaseCrashlytics.getInstance().setUserId(user.uid)
    }

    private fun openFirebaseUISignIn() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

// Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.mipmap.ic_launcher_round)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                onAuthenticationSuccess(FirebaseAuth.getInstance().currentUser!!)
            } else {
                binding.userId.text = "Sign In failed"
            }
        }
    }

    companion object {
        const val RC_SIGN_IN = 10
    }
}
