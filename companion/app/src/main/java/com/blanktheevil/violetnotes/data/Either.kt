package com.blanktheevil.violetnotes.data

sealed class Either<T> {
    data class Success<T>(val data: T) : Either<T>()
    data class Error<T>(val error: Throwable) : Either<T>()
}

fun <T> eitherSuccess(data: T) = Either.Success(data)
fun <T> eitherError(error: Throwable) = Either.Error<T>(error)