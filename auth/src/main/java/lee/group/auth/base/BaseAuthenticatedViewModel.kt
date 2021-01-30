/*
 * Created by do thanh long on 1/27/21 5:58 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 5:58 PM
 */

package lee.group.auth.base

import androidx.lifecycle.MutableLiveData
import lee.group.auth.data.IAuthRepository
import lee.group.core.base.viewmodel.BaseViewModel
import lee.group.core.base.viewmodel.SingleLiveData

abstract class BaseAuthenticatedViewModel(
    protected val auth: IAuthRepository
) : BaseViewModel() {

    val isLogin: MutableLiveData<Boolean> = SingleLiveData()
    val signOutEvent: MutableLiveData<Boolean> = SingleLiveData()

    fun checkLogin() {
        isLogin.value = auth.isLogin()
    }
}
