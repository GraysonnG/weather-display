package com.blanktheevil.violetnotes.ui.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.blanktheevil.violetnotes.R
import com.blanktheevil.violetnotes.data.NoteColor
import com.blanktheevil.violetnotes.ui.DefaultPreview
import com.blanktheevil.violetnotes.ui.DisplayNote
import com.blanktheevil.violetnotes.ui.components.Note
import com.blanktheevil.violetnotes.viewmodels.NotesViewModel

@Composable
fun NotesScreen(
    snackbarHostState: SnackbarHostState,
    notesViewModel: NotesViewModel
) {
    var showCreateDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(painter = painterResource(R.drawable.round_add_24), contentDescription = null)
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                    shape = RoundedCornerShape(10.dp)
                )
            }
        },
    ) { innerPadding ->
        val notes by notesViewModel.notes.collectAsState()
        val loading by notesViewModel.loading.collectAsState()

        NotesScreenContent(
            modifier = Modifier.padding(innerPadding),
            notes = notes,
            onRemoveNote = notesViewModel::removeNote,
            isRefreshing = loading,
            onRefresh = notesViewModel::refresh,
            onNoteUpdated = notesViewModel::updateNote
        )

        if (showCreateDialog) {
            CreateNotePage(isEdit = false,{ text, color ->
                notesViewModel.addNote(text, color)
                showCreateDialog = false
            }) { showCreateDialog = false }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun NotesScreenContent(
    notes: List<DisplayNote>,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onRemoveNote: (DisplayNote) -> Unit,
    onNoteUpdated: (String, String, NoteColor) -> Unit,
) {
    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
    ) {
        if (notes.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(notes, key = { it.id }) {
                    Note(it, onNoteUpdated, onRemoveNote)
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Create a note by pressing the + icon!", color = LocalContentColor.current.copy(alpha = 0.5f))
            }
        }
    }
}



@Composable
@PreviewLightDark
private fun PreviewNotesScreenFull() = DefaultPreview {
    NotesScreenContent(
        listOf(
            DisplayNote("0", "Hello World! With really really long text that causes an overflow on to the next line and maybe has some new lines of its own.\n\nlike this", "blank", NoteColor.Red.colorResId, createdTime = 0L, pending = false, editing = false),
            DisplayNote("1", "Hello World 2", "blank", NoteColor.Green.colorResId, createdTime = 0L, pending = true, editing = false),
            DisplayNote("2", "Hello World 3", "blank", NoteColor.Blue.colorResId, createdTime = 0L, pending = false, editing = true),
            DisplayNote("3", "Hello World 4", "blank", NoteColor.Yellow.colorResId, createdTime = 0L, pending = false, editing = false),
            DisplayNote("4", "Hello World 5", "blank", NoteColor.Orange.colorResId, createdTime = 0L, pending = false, editing = false)
        ),
        isRefreshing = false,
        onRefresh = {},
        onRemoveNote = {},
        onNoteUpdated = {_,_,_->},
    )
}

@Composable
@PreviewLightDark
private fun PreviewNotesScreenEmpty() = DefaultPreview {
    NotesScreenContent(
        emptyList(),
        isRefreshing = false,
        onRefresh = {},
        onRemoveNote = {},
        onNoteUpdated = {_,_,_->},
    )
}
