package lee.group.core.base.view.binding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import lee.group.core.base.view.ViewInterface
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseBindingActivity<T : ViewBinding, V : BaseViewModel> :
    AppCompatActivity(), ViewInterface<V>, BindingInterface<T> {

    override lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = provideBinding()
        setContentView(binding.root)
        setupUI()
        setupViewModel()
    }

    /**
     * Do not call this directly
     */
    abstract fun provideBinding(): T
}
