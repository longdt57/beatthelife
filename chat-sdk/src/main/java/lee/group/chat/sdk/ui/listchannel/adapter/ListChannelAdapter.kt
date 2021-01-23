/*
 * Created by do thanh long on 1/23/21 12:18 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 12:07 PM
 */

package lee.group.chat.sdk.ui.listchannel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lee.group.chat.sdk.data.model.channel.BaseChannel
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.databinding.ListChannelItemBinding
import lee.group.chat.sdk.ui.listchannel.adapter.viewholder.ChannelViewHolder

class ListChannelAdapter(
    private val callback: (View, Int, BaseChannel) -> Unit,
    private val loadMore: () -> Unit
) : ListAdapter<BaseChannel, ListChannelAdapter.ViewHolder>(ChannelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_CHANNEL -> ChannelViewHolder(
                ListChannelItemBinding.inflate(
                    inflater,
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("ListChannelAdapter: illegal view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatChannel -> VIEW_TYPE_CHANNEL
            else -> throw IllegalArgumentException("ListChannelAdapter: illegal item type")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindView(item)
        holder.setupOnclick(getItem(holder.adapterPosition), callback)
        if (position == itemCount - 1) loadMore()
    }

    abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(item: BaseChannel)

        open fun setupOnclick(item: BaseChannel, callback: (View, Int, BaseChannel) -> Unit) {}
    }

    companion object {
        const val VIEW_TYPE_CHANNEL = 2
    }
}
