package io.agapps.common

import io.agapps.common.result.Result
import io.agapps.common.result.asResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class ResultTest {

    @Test
    fun asResult_initially_emits_loading() = runTest {
        val expected = Result.Loading
        val actual = flow {
            emit(1)
        }.asResult().toList().first()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun asResult_emits_loading_then_success() = runTest {
        val expected = listOf(Result.Loading, Result.Success(1))
        val actual = flow {
            emit(1)
        }.asResult().toList()

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun asResult_emits_loading_then_error() = runTest {
        val exception = Exception("Test")
        val expected = listOf(Result.Loading, Result.Error(exception))
        val actual = flow<Int> {
            throw exception
        }.asResult().toList()

        Assert.assertEquals(expected, actual)
    }

}
