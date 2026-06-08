package com.blanktheevil.violetnotes.viewmodels

import android.content.Context
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
import androidx.core.content.edit

class NotesViewModel(
    private val notesRepository: NotesRepository,
    context: Context,
): ViewModel() {
    private val sharedPrefs = context.getSharedPreferences("LOCAL", Context.MODE_PRIVATE)

    private val _notes = MutableStateFlow<Notes>(emptyList())
    val notes = _notes.asStateFlow()

    private val _pendingNotes = MutableStateFlow<List<String>>(emptyList())
    val pendingNotes = _pendingNotes.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    init {
        Log.d(NotesViewModel::class.simpleName, "getNotes")
        viewModelScope.launch {
            initState()
            initUsername()
            _loading.value = false
        }
    }

    fun refresh() = updateState { notesRepository.getNotes() }

    fun addNote(note: Note) = updateState {
        _pendingNotes.value += note.id
        notesRepository.addNotes(listOf(note)).also {
            _pendingNotes.value -= note.id
        }
    }

    fun removeNote(note: Note) = updateState {
        notesRepository.removeNotes(listOf(note))
    }

    fun updateUsername(username: String) {
        _username.value = username
        sharedPrefs.edit { putString("username", username) }
    }

    private fun initUsername() {
        _username.value = sharedPrefs.getString("username", null)
    }

    private suspend fun initState() {
        when (
            val res = notesRepository.getNotes().apply {
                Log.d(NotesViewModel::class.simpleName, (this as? Either.Success<Notes>)?.data.toString())
            }
        ) {
            is Either.Success -> {
                _notes.value = res.data
            }

            is Either.Error -> {
                // idk dawg maybe show a toast?
            }
        }
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