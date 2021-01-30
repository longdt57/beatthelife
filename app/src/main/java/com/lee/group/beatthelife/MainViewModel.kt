/*
 * Created by do thanh long at 1/22/21 10:16 PM.
 * Copyright (c) 2021. All rights reserved.
 */

package com.lee.group.beatthelife

import com.lee.group.beatthelife.data.IEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import lee.group.auth.base.BaseAuthenticatedViewModel
import lee.group.auth.data.IAuthRepository

@HiltViewModel
class MainViewModel @Inject constructor(
    auth: IAuthRepository,
    private val eventRepo: IEventRepository,
) : BaseAuthenticatedViewModel(auth) {

    fun logDeviceType() {
        eventRepo.trackDeviceType()
    }
}
