package com.lee.group.beatthelife.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.databinding.FragmentHomeBinding
import com.lee.group.beatthelife.ui.utils.redirectToSignInScreen
import dagger.hilt.android.AndroidEntryPoint
import lee.group.core.base.view.binding.BaseBindingFragment

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
    }

    override fun observeViewModel() {
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
        activity?.redirectToSignInScreen()
    }
}
