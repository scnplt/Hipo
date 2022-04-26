package dev.sertan.hipoproject.util

internal sealed class State<out T>(
    val value: T? = null,
    val exception: Throwable? = null
) {
    val isIdle get() = this is Idle
    val isLoading get() = this is Loading
    val isFailure get() = this is Failure
    val isSuccess get() = this is Success

    object Idle : State<Nothing>()
    object Loading : State<Nothing>()
    data class Success<T>(val data: T?) : State<T>(data)
    data class Failure(val e: Throwable?) : State<Nothing>(exception = e)

    companion object {
        fun idle(): Idle = Idle
        fun loading(): Loading = Loading
        fun failure(e: Throwable? = null): Failure = Failure(e)
        fun <T> success(data: T? = null): Success<T> = Success(data)
    }
}
