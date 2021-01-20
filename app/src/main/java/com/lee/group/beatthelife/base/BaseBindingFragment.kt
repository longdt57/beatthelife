package com.lee.group.beatthelife.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseBindingFragment<T : ViewBinding, V : ViewModel> : Fragment() {

    protected abstract val viewModel: V

    protected val binding: T get() = _binding!!

    protected var _binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflateBinding(inflater, container)
        setupUI()
        setupViewModel()
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?)

    abstract fun setupUI()

    abstract fun setupViewModel()
}
