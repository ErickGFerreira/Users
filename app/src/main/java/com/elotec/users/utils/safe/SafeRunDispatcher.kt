package com.elotec.users.utils.safe

import com.elotec.users.utils.error.ErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.elotec.users.utils.error.Error


suspend fun <T> safeRunDispatcher(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    block: suspend CoroutineScope.() -> (T)
) = withContext(dispatcher) {
    return@withContext try {
        val result = block()
        Result.Success(result)
    } catch (ex: Exception) {
        Result.Failure(ErrorHandler.convertError(error = ex))
    }
}
class SafeRunException(val error: Error) : Exception()

sealed class Result<out T> {
    class Success<T>(val data: T) : Result<T>()
    class Failure(val error: Error) : Result<Nothing>()
}

fun <T> Result<T>.getOrThrow(): T {
    return when (this) {
        is Result.Failure -> throw SafeRunException(error)
        is Result.Success -> data
    }
}

fun <T> Result<T>.isSuccess(): Boolean {
    return this is Result.Success
}

fun <T> Result<T>.isFailure(): Boolean {
    return this is Result.Failure
}

fun <T> Result<T>.getOrNull(): T? {
    return when (this) {
        is Result.Failure -> null
        is Result.Success -> data
    }
}

fun <T> Result<T>.errorOrNull(): Error? {
    return when (this) {
        is Result.Failure -> error
        is Result.Success -> null
    }
}

fun <T> Result<T>.throwOnFailure() {
    if (this is Result.Failure) throw SafeRunException(error)
}

fun <T> Result<T>.fold(
    onSuccess: (T) -> Unit,
    onFailure: (Error) -> Unit
) {
    return when (this) {
        is Result.Failure -> onFailure(error)
        is Result.Success -> onSuccess(data)
    }
}

fun <T> Result<T>.onSuccess(onSuccess: (T) -> Unit): Result<T> {
    if (this is Result.Success) onSuccess(data)
    return this
}