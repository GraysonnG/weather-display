package com.blanktheevil.violetnotes.ui

import androidx.annotation.ColorRes
import com.blanktheevil.violetnotes.data.Note
import com.blanktheevil.violetnotes.data.Notes

data class DisplayNote(
    val id: String,
    val text: String,
    val author: String,
    @param:ColorRes val color: Int,
    val createdTime: Long,
    val pending: Boolean,
    val editing: Boolean,
)

fun Note.toDisplayNote(
    pending: Boolean = false,
    editing: Boolean = false,
) = DisplayNote(
    id = id,
    text = text,
    author = owner,
    color = color.colorResId,
    createdTime = timePosted,
    pending = pending,
    editing = editing,
)

fun Notes.toDisplayNotes() = this.map { it.toDisplayNote() }
