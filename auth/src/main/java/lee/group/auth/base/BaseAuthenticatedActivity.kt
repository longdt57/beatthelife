package lee.group.auth.base

import androidx.viewbinding.ViewBinding
import lee.group.core.base.view.binding.BaseBindingActivity

abstract class BaseAuthenticatedActivity<T : ViewBinding, V : BaseAuthenticatedViewModel> :
    BaseBindingActivity<T, V>() {

    override fun onResume() {
        super.onResume()
        viewModel.checkLogin()
    }

    override fun setupViewModel() {
        viewModel.isLogin.observe(
            this,
            {
                if (it) {
                    onSignedIn()
                } else {
                    onSignedOut()
                }
            }
        )
    }

    abstract fun onSignedIn()

    abstract fun onSignedOut()
}
