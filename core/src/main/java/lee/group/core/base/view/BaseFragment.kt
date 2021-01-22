package lee.group.core.base.view

import androidx.fragment.app.Fragment
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseFragment<V : BaseViewModel> : Fragment(), ViewInterface<V>
