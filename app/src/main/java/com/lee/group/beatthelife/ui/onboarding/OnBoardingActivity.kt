package com.lee.group.beatthelife.ui.onboarding

import android.app.Activity
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.lee.group.beatthelife.MainActivity
import com.lee.group.beatthelife.R
import com.lee.group.beatthelife.base.BaseBindingActivity
import com.lee.group.beatthelife.databinding.ActivityOnboardingBinding
import com.lee.group.beatthelife.ui.ext.getFirebaseUISignInIntent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseBindingActivity<ActivityOnboardingBinding, OnBoardingViewModel>() {

    override val binding: ActivityOnboardingBinding
    by lazy { ActivityOnboardingBinding.inflate(layoutInflater) }

    override val viewModel: OnBoardingViewModel by viewModels()

    override fun setupUI() {
        binding.btnSignIn.setOnClickListener {
            openFirebaseUISignIn()
        }
        startAuthentication()
    }

    override fun setupViewModel() = Unit

    private fun startAuthentication() {
        val firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid
        if (uid != null) {
            onAuthenticationSuccess(firebaseAuth.currentUser!!)
        }
    }

    private fun onAuthenticationSuccess(user: FirebaseUser) {
        FirebaseCrashlytics.getInstance().setUserId(user.uid)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onAuthenticationFailed() {
        binding.tvNotification.text = getString(R.string.authentication_failed)
        binding.tvNotification.isVisible = true
    }

    private fun openFirebaseUISignIn() {
        // Create and launch sign-in intent
        val intent = getFirebaseUISignInIntent()
        loginLauncher.launch(intent)
    }

    private var loginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                onAuthenticationSuccess(FirebaseAuth.getInstance().currentUser!!)
            } else {
                onAuthenticationFailed()
            }
        }
}
