package com.lee.group.beatthelife.ui.inbox

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.R
import com.lee.group.beatthelife.databinding.FragmentInboxBinding
import dagger.hilt.android.AndroidEntryPoint
import lee.group.chat.sdk.ui.listchannel.ListChannelFragment
import lee.group.core.base.view.binding.BaseBindingFragment
import lee.group.core.ext.replaceFragment

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
        val listChatFragment = ListChannelFragment()
        replaceFragment(listChatFragment, false, R.id.flContainer)
    }

    override fun setupViewModel() {
    }
}
