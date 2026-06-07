package com.blanktheevil.violetnotes.data.responses

import com.blanktheevil.violetnotes.data.Notes
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotesApiResponse(
    val notes: Notes
)
