package com.blanktheevil.violetnotes.repositories

import com.blanktheevil.violetnotes.data.Notes
import com.blanktheevil.violetnotes.data.api.NotesApi
import com.blanktheevil.violetnotes.data.requests.NotesApiRequest

class NotesRepository(
    private val notesApi: NotesApi,
) {
    suspend fun getNotes() = makeCall {
        notesApi.getNotes().notes
    }

    suspend fun addNotes(notes: Notes) = makeCall {
        notesApi.addNotes(request = NotesApiRequest.Add(notes = notes))
    }

    suspend fun removeNotes(notes: Notes) = makeCall {
        notesApi.addNotes(request = NotesApiRequest.Remove(notes = notes))
    }
}