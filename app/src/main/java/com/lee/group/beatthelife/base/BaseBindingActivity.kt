package com.lee.group.beatthelife.base

import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseBindingActivity<T : ViewBinding> : AppCompatActivity() {

    protected val binding: T by lazy { provideBinding() }

    protected abstract fun provideBinding(): T
}
