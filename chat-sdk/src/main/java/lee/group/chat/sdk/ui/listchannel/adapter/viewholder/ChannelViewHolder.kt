/*
 * Created by do thanh long on 1/23/21 12:20 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 12:20 PM
 */

package lee.group.chat.sdk.ui.listchannel.adapter.viewholder

import android.os.Build
import androidx.core.view.isVisible
import lee.group.chat.sdk.R
import lee.group.chat.sdk.data.model.channel.ChatChannel
import lee.group.chat.sdk.data.utils.orFalse
import lee.group.chat.sdk.databinding.ListChannelItemBinding
import lee.group.chat.sdk.ui.utils.MessengerTimeUtils

class ChannelViewHolder(binding: ListChannelItemBinding) :
    BaseChannelViewHolder<ChatChannel, ListChannelItemBinding>(binding) {

    /**
     * Update Title View
     */
    override fun updateTitle(item: ChatChannel) {
        binding.tvTitle.text = item.title
        val titleColorId = if (item is ChatChannel) {
            if (item.isMessageSeen.orFalse()
                .not()
            ) R.style.DS2_Light_16Body_Medium else R.style.DS2_Light_16Body
        } else {
            if (item.isRead.orFalse()
                .not()
            ) R.style.DS2_Light_16Body_Medium else R.style.DS2_Light_16Body
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.tvTitle.setTextAppearance(titleColorId)
        } else {
            @Suppress("DEPRECATION")
            binding.tvTitle.setTextAppearance(itemView.context, titleColorId)
        }
    }

    /**
     * Update subtitle view
     */
    override fun updateSubTitle(item: ChatChannel) {
        binding.tvMessage.text = item.message
        val subTitleColorId = if (item is ChatChannel) {
            if (item.isMessageSeen.orFalse()
                .not()
            ) R.style.DS2_Light_14Caption_Medium else R.style.DS2_Light_14Caption_2
        } else {
            if (item.isRead.orFalse()
                .not()
            ) R.style.DS2_Light_14Caption_Medium else R.style.DS2_Light_14Caption_2
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.tvMessage.setTextAppearance(subTitleColorId)
        } else {
            @Suppress("DEPRECATION")
            binding.tvMessage.setTextAppearance(itemView.context, subTitleColorId)
        }
    }

    /**
     * Update dot and badge
     */
    override fun updateBadges(item: ChatChannel) {
        binding.viewDot.isVisible = item.isRead.orFalse().not()
    }

    /**
     * Update time
     */
    override fun updateTime(item: ChatChannel) {
        item.time?.let {
            binding.tvChannelTime.text = MessengerTimeUtils.getDisplayTime(itemView.context, it)
        }
        if (item.time == 0L || item.time == null) {
            binding.tvChannelTime.text = ""
        }
    }

    override fun updateAvatar(item: ChatChannel) {
        // Todo ("Not yet implemented")
    }
}
