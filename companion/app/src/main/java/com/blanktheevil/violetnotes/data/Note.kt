package com.blanktheevil.violetnotes.data

import androidx.annotation.ColorRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.blanktheevil.violetnotes.R
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
    val owner: String = "test",
    val color: NoteColor = NoteColor.Red,
)

enum class NoteColor(@param:ColorRes val colorResId: Int) {
    Red(R.color.rose_red),
    Green(R.color.sage_green),
    Blue(R.color.sky_blue),
    Yellow(R.color.golden_yellow),
    Orange(R.color.warm_orange),
    Purple(R.color.soft_purple),
    Pink(R.color.blush_pink);

    @Composable
    fun toColor() = colorResource(this.colorResId)
}

typealias Notes = List<Note>