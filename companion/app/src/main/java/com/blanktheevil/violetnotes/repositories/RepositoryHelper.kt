package com.blanktheevil.violetnotes.repositories

import com.blanktheevil.violetnotes.data.Either
import com.blanktheevil.violetnotes.data.eitherError
import com.blanktheevil.violetnotes.data.eitherSuccess

suspend fun <T> makeCall(call: suspend () -> T): Either<T> = try {
    eitherSuccess(call())
} catch (e: Exception) {
    eitherError(e)
}