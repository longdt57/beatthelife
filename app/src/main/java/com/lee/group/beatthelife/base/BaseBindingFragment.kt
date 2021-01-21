package com.lee.group.beatthelife.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.lee.group.beatthelife.base.helper.ViewInterface
import com.lee.group.beatthelife.base.viewmodel.BaseViewModel

abstract class BaseBindingFragment<T : ViewBinding, V : BaseViewModel> : Fragment(), ViewInterface {

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
}
