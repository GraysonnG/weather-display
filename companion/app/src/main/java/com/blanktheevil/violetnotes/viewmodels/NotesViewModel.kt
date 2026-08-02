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
import com.blanktheevil.violetnotes.data.NoteColor
import com.blanktheevil.violetnotes.ui.DisplayNote
import com.blanktheevil.violetnotes.ui.toDisplayNote
import com.blanktheevil.violetnotes.ui.toDisplayNotes
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.util.UUID

class NotesViewModel(
    private val notesRepository: NotesRepository,
    context: Context,
): ViewModel() {
    private val sharedPrefs = context.getSharedPreferences("LOCAL", Context.MODE_PRIVATE)

    private val _notes = MutableStateFlow<List<DisplayNote>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _username = MutableStateFlow<String?>(null)
    val username = _username.asStateFlow()

    private val _loading = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    private val _events = MutableSharedFlow<UIEvent>()
    val events = _events.asSharedFlow()

    init {
        Log.d(NotesViewModel::class.simpleName, "getNotes")
        viewModelScope.launch {
            initState()
            initUsername()
            _loading.value = false
        }
    }

    fun refresh() = viewModelScope.launch {
        _loading.value = true
        when (val res = notesRepository.getNotes()) {
            is Either.Success -> {
                _notes.value = res.data.toDisplayNotes().sortByAge()
                _loading.value = false
            }
            is Either.Error -> {
                _loading.value = false
                _events.emit(
                    UIEvent.ShowToast("Could not get notes!", true)
                )
            }
        }
    }

    fun addNote(text: String, color: NoteColor) = viewModelScope.launch {
        val uuid = UUID.randomUUID().toString()
        val note = Note(
            id = uuid,
            text = text,
            timePosted = System.currentTimeMillis(),
            owner = username.value!!,
            color = color
        )
        _notes.value += note.toDisplayNote(pending = true)
        when (val res = notesRepository.addNotes(listOf(note))) {
            is Either.Success -> _notes.value = res.data.toDisplayNotes().sortByAge()
            is Either.Error -> {
                // send even to ui to show a toast
                _events.emit(
                    UIEvent.ShowToast("Could not add your note!", true)
                )
                _notes.value = _notes.value.filter { it.id != uuid }.sortByAge()
            }
        }
    }.let{}

    fun updateNote(uuid: String, text: String, color: NoteColor) = viewModelScope.launch {
        val originalDisplayNote = _notes.value.firstOrNull { it.id == uuid }?.copy() ?: return@launch
        val note = originalDisplayNote.toNote().copy(
            text = text,
            color = color,
        )

        _notes.value = (_notes.value.filter { it.id != uuid } + note.toDisplayNote(pending = true))
            .sortByAge()

        when (val res = notesRepository.addNotes(listOf(note))) {
            is Either.Success -> _notes.value = res.data.toDisplayNotes().sortByAge()
            is Either.Error -> {
                _events.emit(
                    UIEvent.ShowToast("Could not edit your note!", isError = true)
                )
                _notes.value = (_notes.value.filter { it.id != uuid } + originalDisplayNote)
                    .sortByAge()
            }
        }
    }.let{}

    fun removeNote(displayNote: DisplayNote) = viewModelScope.launch {
        _notes.value = (_notes.value.filterNot { it.id == displayNote.id } + displayNote.copy(pending = true))
            .sortByAge()

        when (val res = notesRepository.removeNotes(listOf(
            Note(
                id = displayNote.id,
                text = displayNote.text,
                timePosted = displayNote.createdTime,
                owner = displayNote.author
            )
        ))) {
            is Either.Success -> _notes.value = res.data.toDisplayNotes().sortByAge()
            is Either.Error -> {
                _events.emit(
                    UIEvent.ShowToast("Could not remove your note!", true)
                )
                _notes.value = (_notes.value.filter { it.id != displayNote.id } + displayNote)
                    .sortByAge()
            }
        }
    }.let{}

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
                _notes.value = res.data.toDisplayNotes().sortByAge()
            }

            is Either.Error -> {
                _events.emit(
                    UIEvent.ShowToast("Could not get notes!", true)
                )
            }
        }
    }

    private fun List<DisplayNote>.sortByAge() = sortedByDescending { it.createdTime }

    private fun DisplayNote.toNote() = Note(
        id = this.id,
        text = this.text,
        timePosted = this.createdTime,
        owner = this.author,
        color = NoteColor.fromColorRes(this.color),
    )

    sealed class UIEvent {
        data class ShowToast(val message: String, val isError: Boolean = false): UIEvent()
    }
}