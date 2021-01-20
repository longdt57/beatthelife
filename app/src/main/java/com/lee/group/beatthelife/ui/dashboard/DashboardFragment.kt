package com.lee.group.beatthelife.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.R
import com.lee.group.beatthelife.base.BaseBindingFragment
import com.lee.group.beatthelife.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseBindingFragment<FragmentDashboardBinding, DashboardViewModel>() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        val textView: TextView = binding.root.findViewById(R.id.text_dashboard)

        dashboardViewModel.text.observe(
            viewLifecycleOwner,
            {
                textView.text = it
            }
        )
        return view
    }

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?) {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
    }
}
