package lee.group.chat.sdk.ui.listchannel.adapter

import androidx.recyclerview.widget.DiffUtil
import lee.group.chat.sdk.data.model.channel.BaseChannel

class ChannelDiffCallback : DiffUtil.ItemCallback<BaseChannel>() {
    override fun areItemsTheSame(oldItem: BaseChannel, newItem: BaseChannel): Boolean {
        return oldItem.channelId == newItem.channelId
    }

    override fun areContentsTheSame(oldItem: BaseChannel, newItem: BaseChannel): Boolean {
        return oldItem == newItem
    }
}
