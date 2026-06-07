package com.blanktheevil.violetnotes.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * @param id UUID for the note.
 * @param text The text of the note.
 * @param timePosted The time the note was created in milliseconds.
 */
@JsonClass(generateAdapter = true)
data class Note(
    val id: String,
    val text: String,
    @param:Json(name ="time_posted") val timePosted: Long,
)

typealias Notes = List<Note>