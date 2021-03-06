package lee.group.core.base.view

interface ViewInterface<V> {

    val viewModel: V

    fun setupUI()

    fun setupViewModel()

    fun initViewModel() {}

    fun showLoading() {}

    fun hideLoading() {}

    fun refresh() {}
}
