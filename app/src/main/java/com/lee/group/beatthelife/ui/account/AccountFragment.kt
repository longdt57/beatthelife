package com.lee.group.beatthelife.ui.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.databinding.FragmentAccountBinding
import dagger.hilt.android.AndroidEntryPoint
import lee.group.core.base.view.binding.BaseBindingFragment

@AndroidEntryPoint
class AccountFragment : BaseBindingFragment<FragmentAccountBinding, AccountViewModel>() {

    override val viewModel: AccountViewModel by viewModels()

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAccountBinding {
        return FragmentAccountBinding.inflate(inflater, container, false)
    }

    override fun setupUI() = Unit

    override fun observeViewModel() = Unit
}
