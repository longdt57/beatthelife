package com.lee.group.beatthelife.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.base.BaseBindingFragment
import com.lee.group.beatthelife.databinding.FragmentHealthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthFragment : BaseBindingFragment<FragmentHealthBinding, HealthViewModel>() {

    override val viewModel: HealthViewModel by viewModels()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentHealthBinding.inflate(inflater, container, false)
    }

    override fun setupUI() {
    }

    override fun setupViewModel() {
        viewModel.text.observe(
            viewLifecycleOwner,
            {
                binding.textDashboard.text = it
            }
        )
    }
}
