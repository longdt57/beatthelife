package lee.group.core.base.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseFragment<V : BaseViewModel> : Fragment(), ViewInterface<V> {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }
}
