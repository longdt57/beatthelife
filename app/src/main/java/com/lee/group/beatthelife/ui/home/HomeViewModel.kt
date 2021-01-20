package com.lee.group.beatthelife.ui.home

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.firebase.ui.auth.AuthUI
import com.lee.group.beatthelife.base.viewmodel.SingleLiveData

class HomeViewModel @ViewModelInject constructor(
) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Beo U page"
    }
    val text: LiveData<String> = _text
}
