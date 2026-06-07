package com.blanktheevil.violetnotes.repositories

import com.blanktheevil.violetnotes.data.Notes
import com.blanktheevil.violetnotes.data.api.NotesApi
import com.blanktheevil.violetnotes.data.requests.NotesApiRequest
import com.blanktheevil.violetnotes.data.responses.noteList

class NotesRepository(
    private val notesApi: NotesApi,
) {
    suspend fun getNotes() = makeCall {
        notesApi.getNotes().noteList
    }

    suspend fun addNotes(notes: Notes) = makeCall {
        notesApi.addNotes(request = NotesApiRequest.Add(notes = notes)).noteList
    }

    suspend fun removeNotes(notes: Notes) = makeCall {
        notesApi.addNotes(request = NotesApiRequest.Remove(notes = notes)).noteList
    }
}