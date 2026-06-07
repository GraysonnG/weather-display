package com.blanktheevil.violetnotes.data.api

import com.blanktheevil.violetnotes.BuildConfig.NOTES_SECRET_KEY
import com.blanktheevil.violetnotes.data.requests.NotesApiRequest
import com.blanktheevil.violetnotes.data.responses.NotesApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface NotesApi {
    companion object {
        const val URL = "https://creative-cheesecake-3607b7.netlify.app"
    }

    @GET("notes")
    suspend fun getNotes(
        @Header("x-api-key") apiKey: String = NOTES_SECRET_KEY,
    ): NotesApiResponse

    @POST("notes")
    suspend fun addNotes(
        @Header("x-api-key") apiKey: String = NOTES_SECRET_KEY,
        @Body request: NotesApiRequest,
    ): NotesApiResponse
}