package com.lee.group.beatthelife.ui.onboarding

import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.google.firebase.perf.metrics.AddTrace
import com.lee.group.beatthelife.R
import com.lee.group.beatthelife.databinding.ActivityOnboardingBinding
import com.lee.group.beatthelife.ui.utils.UITracingName.TRACING_ON_BOARDING_SCREEN
import com.lee.group.beatthelife.ui.utils.UITracingName.TRACING_ON_BOARDING_SCREEN_SETUP_UI
import com.lee.group.beatthelife.ui.utils.checkAuthentication
import com.lee.group.beatthelife.ui.utils.getActivityResultLauncher
import com.lee.group.beatthelife.ui.utils.getSignInIntent
import com.lee.group.beatthelife.ui.utils.redirectToMainScreen
import com.lee.group.beatthelife.ui.utils.setupTrackerUserId
import dagger.hilt.android.AndroidEntryPoint
import lee.group.core.base.view.binding.BaseBindingActivity

@AndroidEntryPoint
@AddTrace(name = TRACING_ON_BOARDING_SCREEN)
class OnBoardingActivity : BaseBindingActivity<ActivityOnboardingBinding, OnBoardingViewModel>() {

    override fun provideBinding(): ActivityOnboardingBinding {
        return ActivityOnboardingBinding.inflate(layoutInflater)
    }

    override val viewModel: OnBoardingViewModel by viewModels()

    @AddTrace(name = TRACING_ON_BOARDING_SCREEN_SETUP_UI)
    override fun setupUI() {
        viewModel.logDeviceType()
        startAuthentication()
        binding.btnSignIn.setOnClickListener {
            openFirebaseUISignIn()
        }
    }

    override fun observeViewModel() = Unit

    private fun startAuthentication() {
        checkAuthentication(
            signedInCallback = {
                onAuthenticationSuccess()
            }
        )
    }

    private fun onAuthenticationSuccess() {
        redirectToMainScreen()
    }

    private fun onAuthenticationFailed() {
        binding.tvNotification.text = getString(R.string.authentication_failed)
        binding.tvNotification.isVisible = true
    }

    private fun openFirebaseUISignIn() {
        val intent = getSignInIntent()
        loginLauncher.launch(intent)
    }

    private val loginLauncher = getActivityResultLauncher(
        onSuccess = {
            setupTrackerUserId()
            viewModel.logEventSignedIn()
            onAuthenticationSuccess()
        },
        onFailed = { onAuthenticationFailed() }
    )
}
