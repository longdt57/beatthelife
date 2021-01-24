/*
 * Created by do thanh long on 1/23/21 12:30 PM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 1/23/21 12:30 PM
 */

package lee.group.chat.sdk.ui.listchannel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import lee.group.chat.sdk.data.IChannelRepository
import lee.group.chat.sdk.data.model.channel.BaseChannel
import lee.group.core.base.viewmodel.BaseViewModel
import lee.group.core.base.viewmodel.SingleLiveData
import timber.log.Timber

@HiltViewModel
class ListChannelViewModel @Inject constructor(
    private val channelRepository: IChannelRepository
) : BaseViewModel() {

    val listChannel: MutableLiveData<List<BaseChannel>> = SingleLiveData()

    fun getChannels() {
        viewModelScope.launch {
            channelRepository.observeAllChannels()
                .catch { error ->
                    Timber.e(error)
                }
                .flowOn(Dispatchers.Main)
                .collect { items ->
                    listChannel.value = items
                }
        }
    }

    fun loadMore() {
    }
}
