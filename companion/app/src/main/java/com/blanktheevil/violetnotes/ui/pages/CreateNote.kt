package com.blanktheevil.violetnotes.ui.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.blanktheevil.violetnotes.data.NoteColor
import com.blanktheevil.violetnotes.ui.DefaultPreview
import com.blanktheevil.violetnotes.ui.components.ColorPicker

@Composable
fun CreateNotePage(
    isEdit: Boolean,
    submit: (String, NoteColor) -> Unit,
    initialText: String = "",
    initialColor: NoteColor = NoteColor.Red,
    dismiss: () -> Unit,
) {
    var selectedColor by remember { mutableStateOf(initialColor) }
    var text by remember { mutableStateOf(initialText) }
    val titleText by remember { derivedStateOf {
        if (isEdit) {
            "Edit Note"
        } else {
            "New Note"
        }
    } }

    val buttonText by remember { derivedStateOf {
        if (isEdit) {
            "Update"
        } else {
            "Add"
        }
    }}


    AlertDialog(
        onDismissRequest = dismiss,
        title = { Text(titleText) },
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
                ColorPicker(
                    color = selectedColor,
                ) { selectedColor = it }
            }
        },
        confirmButton = {
            val enabled by remember { derivedStateOf { text.isNotBlank() } }
            Button(enabled = enabled, onClick = { submit(text, selectedColor) }) { Text(buttonText) }
        },
        dismissButton = {
            TextButton(onClick = { dismiss() }) { Text("Cancel") }
        },
    )
}

@Composable
@PreviewLightDark
private fun PreviewCreateNotePage() = DefaultPreview {
    CreateNotePage(isEdit = true, initialText="hello", initialColor = NoteColor.Pink, submit = {_,_->}) {}
}