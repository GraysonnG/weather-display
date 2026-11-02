@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.blanktheevil.violetnotes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.blanktheevil.violetnotes.R
import com.blanktheevil.violetnotes.data.NoteColor
import com.blanktheevil.violetnotes.ui.DefaultPreview
import com.blanktheevil.violetnotes.ui.DisplayNote

@Composable
fun LazyItemScope.Note(
    note: DisplayNote,
    onUpdateNote: (String, String, NoteColor) -> Unit,
    onRemoveNote: (DisplayNote) -> Unit,
) {
    val color = colorResource(note.color)
    val onSurface = MaterialTheme.colorScheme.onSurface
    val contentColor = onSurface.copy(alpha = if (note.pending) 0.5f else 1f)
    var editMode by remember { mutableStateOf(note.editing) }

    CompositionLocalProvider(
        LocalContentColor provides contentColor,
    ) {
        Row(
            Modifier
                .animateItem()
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(color.copy(alpha = 0.2f))
                .clickable(
                    role = Role.Button,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    if (!editMode) editMode = true
                }
                .padding(8.dp)
        ) {
            if (!editMode) {
                StandardMode(
                    note,
                    color,
                    onRemoveNote,
                )
            } else {
                EditMode(note,
                    {id, text, color ->
                        editMode = false
                        onUpdateNote(id, text, color)
                    },
                    {
                        editMode = false
                    }
                )
            }
        }
    }
}

@Composable
private fun RowScope.StandardMode(
    note: DisplayNote,
    color: Color,
    onRemoveNote: (DisplayNote) -> Unit,
) {
    Box(modifier = Modifier
        .padding(top= 7.dp,end = 8.dp, bottom = 4.dp)
        .clip(CircleShape)
        .background(color)
        .width(8.dp)
        .height(8.dp)
    )
    Column(modifier = Modifier.weight(1f, fill = true)) {
        Text(text = note.text)
        Text(text = "-${note.author}", Modifier.align(Alignment.End))
    }
    if (note.pending) {
        LoadingIndicator(Modifier
            .padding(12.dp)
            .size(24.dp), color = color)
    } else {
        IconButton(onClick = { onRemoveNote(note) }) {
            Icon(painter = painterResource(R.drawable.round_close_24), contentDescription = null)
        }
    }
}

@Composable
private fun EditMode(
    note: DisplayNote,
    onUpdateNote: (String, String, NoteColor) -> Unit,
    onCancel: () -> Unit,
) {
    var editText by remember { mutableStateOf(note.text) }
    var editColor by remember { mutableStateOf(NoteColor.fromColorRes(note.color)) }

    Column {
        OutlinedTextField(
            editText, onValueChange = { editText = it },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 10,
            singleLine = false,
            placeholder = { Text("Your note...") },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom,
        ) {
            ColorPicker(
                modifier = Modifier.weight(weight = 1f, fill = true),
                color = editColor,
            ) { newColor ->
                editColor = newColor
            }

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier.padding(bottom = 2.dp)
            ) {
                OutlinedIconButton({
                    onCancel()
                    editText = note.text
                    editColor = NoteColor.fromColorRes(note.color)
                }) {
                    Icon(painterResource(R.drawable.round_close_24), null)
                }

                FilledIconButton({
                    onUpdateNote(note.id, editText, editColor)
                }) {
                    Icon(painterResource(R.drawable.round_check_24), null)
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun PreviewNotesScreenFull() = DefaultPreview {
    LazyColumn(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(listOf(
            DisplayNote("0", "Hello World! With really really long text that causes an overflow on to the next line and maybe has some new lines of its own.\n\nlike this", "blank", NoteColor.Red.colorResId, createdTime = 0L, pending = false, editing = false),
            DisplayNote("1", "Hello World 2", "blank", NoteColor.Green.colorResId, createdTime = 0L, pending = true, editing = false),
            DisplayNote("2", "Hello World 3", "blank", NoteColor.Blue.colorResId, createdTime = 0L, pending = false, editing = true),
            DisplayNote("3", "Hello World 4", "blank", NoteColor.Yellow.colorResId, createdTime = 0L, pending = false, editing = false),
            DisplayNote("4", "Hello World 5", "blank", NoteColor.Orange.colorResId, createdTime = 0L, pending = false, editing = false)
        )) { note ->
            Note(note, {_,_,_ ->}, {})
        }
    }
}