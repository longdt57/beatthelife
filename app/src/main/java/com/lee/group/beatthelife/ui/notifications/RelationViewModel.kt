package com.lee.group.beatthelife.ui.notifications

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RelationViewModel @ViewModelInject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Relation Fragment"
    }
    val text: LiveData<String> = _text
}
