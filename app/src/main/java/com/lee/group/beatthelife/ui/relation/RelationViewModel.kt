package com.lee.group.beatthelife.ui.relation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import com.lee.group.beatthelife.base.viewmodel.BaseViewModel
import com.lee.group.beatthelife.base.viewmodel.SingleLiveData

class RelationViewModel @ViewModelInject constructor() : BaseViewModel() {

    private val _text = SingleLiveData<String>().apply {
        value = "This is Relation Fragment"
    }
    val text: LiveData<String> = _text
}
