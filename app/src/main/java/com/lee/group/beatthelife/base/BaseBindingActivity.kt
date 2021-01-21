package com.lee.group.beatthelife.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.lee.group.beatthelife.base.helper.ViewInterface
import com.lee.group.beatthelife.base.viewmodel.BaseViewModel

abstract class BaseBindingActivity<T : ViewBinding, V : BaseViewModel> :
    AppCompatActivity(),
    ViewInterface {

    protected abstract val binding: T

    protected abstract val viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
    }
}
