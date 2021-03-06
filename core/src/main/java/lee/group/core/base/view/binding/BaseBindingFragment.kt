package lee.group.core.base.view.binding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import lee.group.core.base.view.BaseFragment
import lee.group.core.base.viewmodel.BaseViewModel

abstract class BaseBindingFragment<T : ViewBinding, V : BaseViewModel> :
    BaseFragment<V>(),
    BindingInterface<T> {

    override val binding: T get() = _binding!!

    private var _binding: T? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = provideBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * Do not call this directly
     */
    abstract fun provideBinding(inflater: LayoutInflater, container: ViewGroup?): T
}
