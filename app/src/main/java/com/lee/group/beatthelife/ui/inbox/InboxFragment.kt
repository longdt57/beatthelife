package com.lee.group.beatthelife.ui.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.databinding.FragmentInboxBinding
import dagger.hilt.android.AndroidEntryPoint
import lee.group.core.base.view.binding.BaseBindingFragment

@AndroidEntryPoint
class InboxFragment : BaseBindingFragment<FragmentInboxBinding, InboxViewModel>() {

    override val viewModel: InboxViewModel by viewModels()

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentInboxBinding {
        return FragmentInboxBinding.inflate(inflater, container, false)
    }

    override fun setupUI() {
//        val navHostFragment =
//            parentFragmentManager.findFragmentById(R.id.nav_host_fragment_inbox) as NavHostFragment
//        val navController = navHostFragment.navController
    }

    override fun setupViewModel() {
    }
}
