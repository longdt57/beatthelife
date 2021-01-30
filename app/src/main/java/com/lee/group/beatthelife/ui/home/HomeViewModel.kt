package com.lee.group.beatthelife.ui.home

import androidx.lifecycle.LiveData
import com.lee.group.beatthelife.data.IEventRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import lee.group.auth.base.BaseAuthenticatedViewModel
import lee.group.auth.data.IAuthRepository
import lee.group.core.base.viewmodel.SingleLiveData

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val eventRepo: IEventRepository,
    auth: IAuthRepository
) : BaseAuthenticatedViewModel(auth) {

    fun logEventLogOut() {
        eventRepo.logEventLogOut()
    }

    private val _text = SingleLiveData<String>().apply {
        value = "This is Beo U page"
    }
    val text: LiveData<String> = _text
}
