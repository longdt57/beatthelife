package lee.group.core.base.view.binding

import androidx.activity.viewModels
import androidx.viewbinding.ViewBinding
import lee.group.core.base.viewmodel.SimpleViewModel

abstract class SimpleBindingActivity<T : ViewBinding> : BaseBindingActivity<T, SimpleViewModel>() {

    override val viewModel: SimpleViewModel by viewModels()
}
