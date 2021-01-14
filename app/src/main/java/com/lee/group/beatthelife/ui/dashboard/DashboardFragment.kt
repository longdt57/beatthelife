package com.lee.group.beatthelife.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.lee.group.beatthelife.R
import com.lee.group.beatthelife.base.BaseBindingFragment
import com.lee.group.beatthelife.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : BaseBindingFragment<FragmentDashboardBinding>() {

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val root = binding.root
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        dashboardViewModel.text.observe(
            viewLifecycleOwner,
            Observer {
                textView.text = it
            }
        )
        return root
    }

    override fun provideBinding(container: ViewGroup?): FragmentDashboardBinding {
        return FragmentDashboardBinding.inflate(layoutInflater, container, false)
    }
}
