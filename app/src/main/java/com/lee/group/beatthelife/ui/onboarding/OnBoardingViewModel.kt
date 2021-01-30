package com.lee.group.beatthelife.ui.onboarding

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import lee.group.auth.base.BaseAuthenticatedViewModel
import lee.group.auth.data.IAuthRepository

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    auth: IAuthRepository
) : BaseAuthenticatedViewModel(auth)
