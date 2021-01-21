package com.lee.group.beatthelife.ui.account

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import lee.group.core.base.viewmodel.BaseViewModel
import lee.group.core.base.viewmodel.SingleLiveData

class AccountViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _text = SingleLiveData<String>().apply {
        value = "This is Relation Fragment"
    }
    val text: LiveData<String> = _text
}
