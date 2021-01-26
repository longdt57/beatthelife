/*
 * Created by do thanh long on 1/23/21 12:28 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 12:28 PM
 */

package lee.group.chat.sdk.ui.listchannel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import lee.group.chat.sdk.data.model.channel.BaseChannel
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.databinding.FragmentListChannelBinding
import lee.group.chat.sdk.ui.listchannel.adapter.ListChannelAdapter
import lee.group.core.base.view.binding.BaseBindingFragment

@AndroidEntryPoint
class ListChannelFragment :
    BaseBindingFragment<FragmentListChannelBinding, ListChannelViewModel>() {

    private val adapter: ListChannelAdapter by lazy {
        ListChannelAdapter(
            onClickCallback(),
            { viewModel.loadMore() }
        )
    }

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListChannelBinding {
        return FragmentListChannelBinding.inflate(inflater, container, false)
    }

    override val viewModel: ListChannelViewModel by viewModels()

    override fun setupUI() {
        setupRecyclerView()
        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    override fun setupViewModel() {
        viewModel.listChannel.observe(
            viewLifecycleOwner,
            {
                adapter.submitList(it)
                hideLoading()
            }
        )
    }

    override fun initViewModel() {
        super.initViewModel()
        refresh()
    }

    override fun hideLoading() {
        super.hideLoading()
        binding.swipeRefresh.isRefreshing = false
    }

    override fun refresh() {
        super.refresh()
        viewModel.getChannels()
    }

    /**
     * Handle Item click
     *
     * @param view view click
     * @param position item position
     * @param item data
     */
    private fun onClickCallback() = fun(view: View, position: Int, item: BaseChannel) {
        adapter.notifyItemChanged(position)
        when (view.id) {
            else -> onChannelClickCallback(item)
        }
    }

    private fun onChannelClickCallback(item: BaseChannel) {
        when (item) {
            is ChatChannel -> {
                // openChatChannel(item.channelId)
            }
        }
    }

    /**
     * Set Up Recycler View. Add Swipe
     */
    private fun setupRecyclerView() {
        binding.rvChat.layoutManager = LinearLayoutManager(context)
        binding.rvChat.adapter = adapter
    }
}
