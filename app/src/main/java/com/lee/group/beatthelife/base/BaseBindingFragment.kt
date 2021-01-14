package com.lee.group.beatthelife.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseBindingFragment<T : ViewBinding> : Fragment() {

    protected lateinit var binding: T
        private set

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = provideBinding(container)
        return binding.root
    }

    protected abstract fun provideBinding(container: ViewGroup?): T
}
