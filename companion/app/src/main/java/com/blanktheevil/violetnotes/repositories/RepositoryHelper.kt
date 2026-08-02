package com.blanktheevil.violetnotes.repositories

import android.util.Log
import com.blanktheevil.violetnotes.data.Either
import com.blanktheevil.violetnotes.data.eitherError
import com.blanktheevil.violetnotes.data.eitherSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> makeCall(call: suspend () -> T): Either<T> = withContext(Dispatchers.IO) {
    try {
        eitherSuccess(call()).also {
            Log.d("MakeCall", "Success: $it")
        }
    } catch (e: Exception) {
        Log.e("MakeCall", e.message, e)
        eitherError(e)
    }
}