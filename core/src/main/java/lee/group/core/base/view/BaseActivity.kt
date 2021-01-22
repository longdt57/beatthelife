package lee.group.core.base.view

import androidx.appcompat.app.AppCompatActivity
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), ViewInterface<V>
