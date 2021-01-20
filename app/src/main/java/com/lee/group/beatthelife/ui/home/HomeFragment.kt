package com.lee.group.beatthelife.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.firebase.ui.auth.AuthUI
import com.lee.group.beatthelife.base.BaseBindingFragment
import com.lee.group.beatthelife.databinding.FragmentHomeBinding
import com.lee.group.beatthelife.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseBindingFragment<FragmentHomeBinding, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override fun setupUI() {
        binding.btnSignOut.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener {
                    signOutSuccess()
                }
        }
    }

    override fun setupViewModel() {
        viewModel.text.observe(
            viewLifecycleOwner,
            {
                binding.textHome.text = it
            }
        )

    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
    }

    private fun signOutSuccess() {
        activity?.finish()
        startActivity(Intent(context, OnBoardingActivity::class.java))
    }
}
