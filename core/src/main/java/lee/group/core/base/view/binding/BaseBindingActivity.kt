package lee.group.core.base.view.binding

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import lee.group.core.base.view.BaseActivity
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseBindingActivity<T : ViewBinding, V : BaseViewModel> :
    BaseActivity<V>(), BindingInterface<T> {

    override lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = provideBinding()
        setContentView(binding.root)
        setupUI()
        observeViewModel()
    }

    /**
     * Do not call this directly
     */
    abstract fun provideBinding(): T
}
