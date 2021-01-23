/*
 * Created by do thanh long on 1/23/21 12:21 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 12:21 PM
 */

package lee.group.chat.sdk.ui.listchannel.adapter.viewholder

import androidx.viewbinding.ViewBinding
import lee.group.chat.sdk.data.model.channel.BaseChannel
import lee.group.chat.sdk.ui.listchannel.adapter.ListChannelAdapter

abstract class BaseChannelViewHolder<I : BaseChannel, T : ViewBinding>(val binding: T) :
    ListChannelAdapter.ViewHolder(binding.root) {

    override fun bindView(item: BaseChannel) {
        (item as? I)?.let {
            updateAllContent(item)
        }
    }

    /**
     * Update top layer content
     */
    open fun updateAllContent(item: I) {
        // Reset swipe
        updateTitle(item)
        updateSubTitle(item)
        updateAvatar(item)
        updateTime(item)
        updateBadges(item)
    }

    abstract fun updateTitle(item: I)

    abstract fun updateSubTitle(item: I)

    abstract fun updateAvatar(item: I)

    abstract fun updateTime(item: I)

    abstract fun updateBadges(item: I)
}
