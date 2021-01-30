package com.lee.group.beatthelife.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.databinding.FragmentHomeBinding
import com.lee.group.beatthelife.ui.utils.redirectToOnBoardingScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import lee.group.auth.ui.helper.AuthenticationHelper
import lee.group.core.base.view.binding.BaseBindingFragment

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {

    @Inject
    lateinit var authHelper: AuthenticationHelper

    override val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {
        binding.btnSignOut.setOnClickListener {
            authHelper.logout(
                requireActivity(),
                onSuccess = { signOutSuccess() },
                onFailure = {}
            )
        }
    }

    override fun setupViewModel() {
        viewModel.text.observe(
            viewLifecycleOwner,
            {
                binding.textHome.text = it
            }
        )

        viewModel.signOutEvent.observe(
            viewLifecycleOwner,
            {
                if (it) {
                    signOutSuccess()
                    viewModel.logEventLogOut()
                }
            }
        )
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    private fun signOutSuccess() {
        activity?.redirectToOnBoardingScreen()
    }
}
