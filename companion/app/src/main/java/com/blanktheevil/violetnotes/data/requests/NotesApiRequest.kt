package com.blanktheevil.violetnotes.data.requests

import com.blanktheevil.violetnotes.data.Notes
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
open class NotesApiRequest(
    val action: String,
    val notes: Notes,
) {
    class Add(notes: Notes): NotesApiRequest(action = "add", notes = notes)
    class Remove(notes: Notes): NotesApiRequest(action = "remove", notes = notes)
}
