package lee.group.core.base.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), ViewInterface<V> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
    }
}
