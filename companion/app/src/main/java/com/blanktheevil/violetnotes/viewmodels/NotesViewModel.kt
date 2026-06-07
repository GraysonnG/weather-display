package com.blanktheevil.violetnotes.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blanktheevil.violetnotes.data.Either
import com.blanktheevil.violetnotes.data.Note
import com.blanktheevil.violetnotes.data.Notes
import com.blanktheevil.violetnotes.repositories.NotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NotesViewModel(
    private val notesRepository: NotesRepository,
): ViewModel() {
    private val _notes = MutableStateFlow<Notes>(emptyList())
    val notes = _notes.asStateFlow()

    init {
        Log.d(NotesViewModel::class.simpleName, "getNotes")
        updateState {
            notesRepository.getNotes().apply {
                Log.d(NotesViewModel::class.simpleName, (this as? Either.Success<Notes>)?.data.toString())
            }
        }
    }

    fun refresh() = updateState { notesRepository.getNotes() }

    fun addNote(note: Note) = updateState {
        notesRepository.addNotes(listOf(note))
    }

    fun removeNote(note: Note) = updateState {
        notesRepository.removeNotes(listOf(note))
    }

    private fun updateState(update: suspend () -> Either<Notes>) = viewModelScope.launch {
        when (val res = update()) {
            is Either.Success -> {
                _notes.value = res.data
            }

            is Either.Error -> {
                // idk dawg maybe show a toast?
            }
        }
    }.let {}
}