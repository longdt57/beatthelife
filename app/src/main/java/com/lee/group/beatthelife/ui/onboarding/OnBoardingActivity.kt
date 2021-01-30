package com.lee.group.beatthelife.ui.onboarding

import android.content.Intent
import androidx.activity.viewModels
import com.lee.group.beatthelife.databinding.ActivityOnboardingBinding
import com.lee.group.beatthelife.ui.utils.redirectToMainScreen
import dagger.hilt.android.AndroidEntryPoint
import lee.group.auth.base.BaseAuthenticatedActivity
import lee.group.auth.ui.login.LoginActivity
import lee.group.core.ext.getActivityResultLauncher

@AndroidEntryPoint
class OnBoardingActivity :
    BaseAuthenticatedActivity<ActivityOnboardingBinding, OnBoardingViewModel>() {

    override fun provideBinding(): ActivityOnboardingBinding {
        return ActivityOnboardingBinding.inflate(layoutInflater)
    }

    override val viewModel: OnBoardingViewModel by viewModels()

    override fun onSignedIn() {
        redirectToMainScreen()
    }

    override fun onSignedOut() {
        val intent = Intent(this, LoginActivity::class.java)
        loginLauncher.launch(intent)
    }

    override fun setupUI() = Unit

    private val loginLauncher = getActivityResultLauncher(
        onSuccess = {
            redirectToMainScreen()
        },
        onFailure = {
        }
    )
}
