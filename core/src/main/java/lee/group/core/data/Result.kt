package lee.group.core.data

sealed class BaseResult<out R> {
    data class ResultSuccess<out T>(val data: T) : BaseResult<T>()
    data class ResultError(val throwable: Throwable) : BaseResult<Nothing>()
}

fun <T> T.toResult() = BaseResult.ResultSuccess(this)

fun Throwable.toResult() = BaseResult.ResultError(this)
