package com.lee.group.beatthelife.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lee.group.beatthelife.data.IBeoURepository
import com.lee.group.beatthelife.data.IEventRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import lee.group.core.base.viewmodel.BaseViewModel
import lee.group.core.base.viewmodel.SingleLiveData

class HomeViewModel @ViewModelInject constructor(
    private val beoURepository: IBeoURepository,
    private val eventRepo: IEventRepository
) : BaseViewModel() {

    val signOutEvent: MutableLiveData<Boolean> = SingleLiveData()

    fun signOut() {
        viewModelScope.launch {
            beoURepository.signOut().collect {
                signOutEvent.value = it
            }
        }
    }

    fun logEventLogOut() {
        eventRepo.logEventLogOut()
    }

    private val _text = SingleLiveData<String>().apply {
        value = "This is Beo U page"
    }
    val text: LiveData<String> = _text
}
