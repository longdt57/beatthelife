package com.lee.group.beatthelife.ui.inbox

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import lee.group.core.base.viewmodel.BaseViewModel
import lee.group.core.base.viewmodel.SingleLiveData

class InboxViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _text = SingleLiveData<String>().apply {
        value = "This is Help Page"
    }
    val text: LiveData<String> = _text
}
