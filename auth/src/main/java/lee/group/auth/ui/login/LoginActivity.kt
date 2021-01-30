/*
 * Created by do thanh long on 1/27/21 4:22 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/27/21 3:36 PM
 */

package lee.group.auth.ui.login

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import lee.group.auth.R
import lee.group.auth.data.room.entity.Auth0Credential
import lee.group.auth.databinding.ActivityLoginBinding
import lee.group.auth.ui.getSignInIntent
import lee.group.auth.ui.helper.AuthenticationHelper
import lee.group.core.base.view.binding.BaseBindingActivity
import lee.group.core.ext.getActivityResultLauncher

@AndroidEntryPoint
class LoginActivity : BaseBindingActivity<ActivityLoginBinding, LoginViewModel>() {

    @Inject
    lateinit var authHelper: AuthenticationHelper

    override val viewModel: LoginViewModel by viewModels()

    override fun provideBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun setupUI() {
        binding.btnSignIn.setOnClickListener {
            openFirebaseUISignIn()
        }
        binding.btnSignInByAuth0.setOnClickListener {
            openAuth0SignIn()
        }
    }

    private fun openFirebaseUISignIn() {
        val intent = getSignInIntent()
        loginLauncher.launch(intent)
    }

    private fun openAuth0SignIn() {
        login()
    }

    private val loginLauncher = getActivityResultLauncher(
        onSuccess = {
            onAuthenticationSuccess(intent)
        },
        onFailure = {
            onAuthenticationFailed(null)
        }
    )

    private fun onAuthenticationSuccess(intent: Intent?) {
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onAuthenticationFailed(ex: Exception?) {
        binding.tvNotification.text = ex?.message ?: getString(R.string.authentication_failed)
        binding.tvNotification.isVisible = true
    }

    override fun setupViewModel() = Unit

    private fun login() {
        authHelper.loginByAuth0(
            this,
            onSuccess = {
                runOnUiThread {
                    val intent = Intent()
                    (it as? Auth0Credential)?.let {
                        intent.putExtra(EXTRA_AUTH_CREDENTIAL, it)
                    }
                    onAuthenticationSuccess(intent)
                }
            },
            onFailure = {
                runOnUiThread {
                    Toast.makeText(this, "Error: " + it.message, Toast.LENGTH_SHORT).show()
                    onAuthenticationFailed(it)
                }
            }
        )
    }

    companion object {
        const val EXTRA_AUTH_CREDENTIAL = "EXTRA_AUTH0_CREDENTIAL"
    }
}
