package lee.group.core.base.view

import androidx.activity.viewModels
import lee.group.core.base.viewmodel.SimpleViewModel

abstract class SimpleActivity : BaseActivity<SimpleViewModel>() {

    override val viewModel: SimpleViewModel by viewModels()

    override fun setupUI() = Unit

    override fun setupViewModel() = Unit
}
