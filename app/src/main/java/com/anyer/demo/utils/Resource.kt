package com.anyer.demo.utils

/**
 * A generic class that contains data and status about loading this data.
 *
 * Source https://developer.android.com/jetpack/guide#addendum
 */
sealed class Resource<T>(
    open val data: T? = null,
    open val message: String? = null
) {
    data class Success<T>(override val data: T) : Resource<T>(data)

    data class Loading<T>(override val data: T? = null) : Resource<T>(data)

    data class Error<T>(
        override val message: String,
        override val data: T? = null
    ) : Resource<T>(data, message)
}