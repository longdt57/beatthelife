/*
 * Created by do thanh long on 1/23/21 12:28 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 12:28 PM
 */

package lee.group.chat.sdk.ui.listchannel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import lee.group.chat.sdk.databinding.FragmentListChannelBinding
import lee.group.core.base.view.binding.BaseBindingFragment

@AndroidEntryPoint
class ListChannelFragment :
    BaseBindingFragment<FragmentListChannelBinding, ListChannelViewModel>() {

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListChannelBinding {
        return FragmentListChannelBinding.inflate(inflater, container, false)
    }

    override val viewModel: ListChannelViewModel by viewModels()

    override fun setupUI() {
        TODO("Not yet implemented")
    }

    override fun setupViewModel() {
        viewModel.listChannel.observe(
            viewLifecycleOwner,
            {
            }
        )
    }
}
