package io.agapps.common.result

sealed class Result<out T : Any>
data class Success<out T : Any>(val data: T) : Result<T>()
data class Failure(val exception: Exception) : Result<Nothing>()

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Success) action(data)
    return this
}

inline fun <T : Any> Result<T>.onFailure(action: (Exception) -> Unit) {
    if (this is Failure) action(exception)
}
