package com.lee.group.beatthelife.ui.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.lee.group.beatthelife.ui.utils.checkAuthentication
import com.lee.group.beatthelife.ui.utils.redirectToSignInScreen
import lee.group.core.base.view.binding.BaseBindingActivity
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseAuthenticatedActivity<T : ViewBinding, V : BaseViewModel> :
    BaseBindingActivity<T, V>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        checkSignedIn()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        checkSignedIn()
        super.onResume()
    }

    private fun checkSignedIn() {
        checkAuthentication(
            unSignedInCallback = {
                redirectToSignInScreen()
            }
        )
    }
}
