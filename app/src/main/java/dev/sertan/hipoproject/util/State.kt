package dev.sertan.hipoproject.util

internal sealed class State<out T>(exception: Throwable? = null, val data: T? = null) {
    object Idle : State<Nothing>()
    object Loading : State<Nothing>()
    data class Error(private val e: Throwable?) : State<Nothing>(exception = e)
    data class Loaded<T>(private val value: T?) : State<T>(data = value)

    companion object {
        fun idle(): State<Nothing> = Idle
        fun loading(): State<Nothing> = Loading
        fun error(e: Throwable? = null): State<Nothing> = Error(e)
        fun <T> loaded(data: T? = null): State<T> = Loaded(data)
    }
}
