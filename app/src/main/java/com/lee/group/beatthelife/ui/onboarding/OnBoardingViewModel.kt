package com.lee.group.beatthelife.ui.onboarding

import com.lee.group.beatthelife.data.IEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import lee.group.core.base.viewmodel.BaseViewModel

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val eventRepo: IEventRepository
) : BaseViewModel() {

    fun logEventSignedIn() {
        eventRepo.logEventLogin()
    }
}
