/*
 * Created by do thanh long on 2/1/21 11:07 AM.
 * Copyright (c) 2021. All rights reserved.
 * Last modified 2/1/21 11:07 AM
 */

package lee.group.chat.sdk.ui.newchannel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import lee.group.chat.sdk.databinding.FragmentNewChannelBinding
import lee.group.core.base.view.binding.BaseBindingBottomSheetDialogFragment

class NewChannelFragment :
    BaseBindingBottomSheetDialogFragment<FragmentNewChannelBinding, NewChannelViewModel>() {

    override fun provideBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewChannelBinding {
        return FragmentNewChannelBinding.inflate(inflater, container, false)
    }

    override val viewModel: NewChannelViewModel by viewModels()

    override fun setupUI() {
    }

    override fun setupViewModel() {
    }
}
