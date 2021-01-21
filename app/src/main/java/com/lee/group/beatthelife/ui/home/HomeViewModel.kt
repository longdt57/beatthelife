package com.lee.group.beatthelife.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lee.group.beatthelife.data.IBeoURepository
import io.reactivex.android.schedulers.AndroidSchedulers
import lee.group.core.base.viewmodel.BaseViewModel
import lee.group.core.base.viewmodel.SingleLiveData
import timber.log.Timber

class HomeViewModel @ViewModelInject constructor(
    private val beoURepository: IBeoURepository
) : BaseViewModel() {

    val signOutEvent: MutableLiveData<Boolean> = SingleLiveData()

    fun signOut() {
        beoURepository.signOut()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    signOutEvent.value = true
                },
                {
                    Timber.e(it)
                }
            )
            .add(this)
    }

    private val _text = SingleLiveData<String>().apply {
        value = "This is Beo U page"
    }
    val text: LiveData<String> = _text
}
