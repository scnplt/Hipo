package dev.sertan.hipoproject.util

import com.google.common.truth.Truth
import org.junit.Test

internal class StateTest {

    @Test
    fun idle() {
        val state = State.idle()
        Truth.assertThat(state).isInstanceOf(State.Idle::class.java)
        Truth.assertThat(state.exception).isNull()
        Truth.assertThat(state.isIdle).isTrue()
    }

    @Test
    fun loading() {
        val state = State.loading()
        Truth.assertThat(state).isInstanceOf(State.Loading::class.java)
        Truth.assertThat(state.exception).isNull()
        Truth.assertThat(state.isLoading).isTrue()
    }

    @Test
    fun failureWithoutException() {
        val state = State.failure()
        Truth.assertThat(state).isInstanceOf(State.Failure::class.java)
        Truth.assertThat(state.exception).isNull()
        Truth.assertThat(state.isFailure).isTrue()
    }

    @Test
    fun failureWithException() {
        val state = State.failure(IllegalArgumentException())
        Truth.assertThat(state).isInstanceOf(State.Failure::class.java)
        Truth.assertThat(state.exception).isNotNull()
        Truth.assertThat(state.exception).isInstanceOf(IllegalArgumentException::class.java)
        Truth.assertThat(state.isFailure).isTrue()
    }

    @Test
    fun successWithData() {
        val state = State.success<Any>()
        Truth.assertThat(state).isInstanceOf(State.Success::class.java)
        Truth.assertThat(state.exception).isNull()
        Truth.assertThat(state.data).isNull()
        Truth.assertThat(state.isSuccess).isTrue()
    }

    @Test
    fun successWithoutData() {
        val state = State.success(5)
        Truth.assertThat(state).isInstanceOf(State.Success::class.java)
        Truth.assertThat(state.exception).isNull()
        Truth.assertThat(state.data).isNotNull()
        Truth.assertThat(state.data).isEqualTo(5)
        Truth.assertThat(state.isSuccess).isTrue()
    }
}
