package com.lee.group.beatthelife.base

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseBindingActivity<T : ViewBinding, V : ViewModel> : AppCompatActivity() {

    protected abstract val binding: T

    protected abstract val viewModel: V
}
