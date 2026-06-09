package com.blanktheevil.violetnotes.ui.pages

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.blanktheevil.violetnotes.data.NoteColor
import com.blanktheevil.violetnotes.ui.DefaultPreview

@Composable
fun CreateNotePage(
    addNote: (String, NoteColor) -> Unit,
    dismiss: () -> Unit,
) {
    var selectedColor by remember { mutableStateOf(NoteColor.Red) }
    var text by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = dismiss,
        title = { Text("New Note") },
        text = {
            Column {
                OutlinedTextField(
                    text, onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    maxLines = 10,
                    singleLine = false,
                    placeholder = { Text("Your note...") },
                )
                LazyRow (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 2.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(color = MaterialTheme.colorScheme.surfaceContainer)
                    ,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(NoteColor.entries) {
                        val borderColor = MaterialTheme.colorScheme.onSurface
                        val surfaceColor = MaterialTheme.colorScheme.surface
                        val selected by remember { derivedStateOf { selectedColor == it } }

                        val shapeDp by animateDpAsState(
                            if (selected) 20.dp else 10.dp,
                            animationSpec = MaterialTheme.motionScheme.fastSpatialSpec()
                        )


                        val shape by remember { derivedStateOf {
                            RoundedCornerShape(shapeDp)
                        } }
                        val border by remember { derivedStateOf {
                            if (selected) BorderStroke(2.dp, borderColor) else BorderStroke(1.dp, Color.White)
                        } }
                        val innerBorder by remember {  derivedStateOf {
                            if (selected) BorderStroke(4.dp, surfaceColor) else BorderStroke(0.dp, Color.Transparent)
                        } }

                        Box(
                            Modifier
                                .clip(shape)
                                .border(border, shape)
                                .border(innerBorder, shape)
                                .background(color = it.toColor())
                                .size(40.dp)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = ripple(),
                                    role = Role.RadioButton,
                                ) {
                                    selectedColor = it
                                }
                        )
                    }
                }
            }
        },
        confirmButton = {
            val enabled by remember { derivedStateOf { text.isNotBlank() } }
            Button(enabled = enabled, onClick = { addNote(text, selectedColor) }) { Text("Add") }
        },
        dismissButton = {
            TextButton(onClick = { dismiss() }) { Text("Cancel") }
        },
    )
}

@Composable
@PreviewLightDark
private fun PreviewCreateNotePage() = DefaultPreview {
    CreateNotePage({_,_->}, {})
}