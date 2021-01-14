package com.lee.group.beatthelife.ui.onboarding

import android.os.Bundle
import com.lee.group.beatthelife.base.BaseBindingActivity
import com.lee.group.beatthelife.databinding.ActivityOnboardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseBindingActivity<ActivityOnboardingBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun provideBinding(): ActivityOnboardingBinding {
        return ActivityOnboardingBinding.inflate(layoutInflater)
    }
}
