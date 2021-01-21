package lee.group.core.base

interface ViewInterface<T, V> {

    val binding: T

    val viewModel: V

    fun setupUI()

    fun setupViewModel()
}
