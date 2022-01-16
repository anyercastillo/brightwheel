package com.anyer.demo.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

private const val MESSAGE_NULL_DATA = "Null data"
private const val MESSAGE_NO_EXCEPTION_MESSAGE = "Exception with no message"

/**
 * A naive implementation to allow fetching a network resource.
 *
 * This function sets the ground for a more robust implementation.
 *
 * A more robust implementation should take care of serving data from database first,
 * performing a network request if needed and persisting data.
 */
fun <T> fetchNetworkResource(
    api: suspend () -> T,
    dispatcherIO: CoroutineDispatcher = Dispatchers.IO
) = flow {
    emit(Resource.Loading())

    emit(apiCall(api, dispatcherIO))
}

/**
 * A naive implementation to allow network calls.
 *
 * This function sets the ground for a more robust implementation.
 *
 * A more robust implementation should take care of IO errors vs Http errors.
 * Having a more robust implementation allows features like: re-try a request.
 */
suspend fun <T> apiCall(
    api: suspend () -> T,
    dispatcherIO: CoroutineDispatcher
): Resource<T> = withContext(context = dispatcherIO) {
    val result = runCatching {
        api.invoke()
    }

    if (result.isSuccess) {
        val data = result.getOrNull() ?: return@withContext Resource.Error(
            data = null,
            message = MESSAGE_NULL_DATA
        )

        return@withContext Resource.Success(data = data)
    }

    val message = result.exceptionOrNull()?.message ?: MESSAGE_NO_EXCEPTION_MESSAGE
    return@withContext Resource.Error(data = null, message = message)
}