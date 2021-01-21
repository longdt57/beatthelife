package com.lee.group.beatthelife.ui.health

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import com.lee.group.beatthelife.base.viewmodel.BaseViewModel
import com.lee.group.beatthelife.base.viewmodel.SingleLiveData

class HealthViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _text = SingleLiveData<String>().apply {
        value = "This is Help Page"
    }
    val text: LiveData<String> = _text
}
