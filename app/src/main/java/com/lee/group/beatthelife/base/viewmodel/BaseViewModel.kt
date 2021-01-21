package com.lee.group.beatthelife.base.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    protected fun dispose() {
        compositeDisposable?.dispose()
    }

    protected fun Disposable.add(baseViewModel: BaseViewModel) {
        baseViewModel.addDisposable(this)
    }

    protected fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null || compositeDisposable?.isDisposed == true) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}
