package com.blanktheevil.violetnotes

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.blanktheevil.violetnotes.ui.pages.CreateNotePage
import com.blanktheevil.violetnotes.ui.pages.NotesScreen
import com.blanktheevil.violetnotes.ui.pages.SetupPage
import com.blanktheevil.violetnotes.ui.theme.VioletNotesTheme
import com.blanktheevil.violetnotes.viewmodels.NotesViewModel
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VioletNotesTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val vm = koinViewModel<NotesViewModel>()
                val username by vm.username.collectAsState()
                val loading by vm.loading.collectAsState()
                val notes by vm.notes.collectAsState()

                LaunchedEffect(Unit) {
                    vm.events.collect { event ->
                        when (event) {
                            is NotesViewModel.UIEvent.ShowToast -> {
                                snackbarHostState.showSnackbar(event.message)
                            }
                        }
                    }
                }

                val homeState by remember { derivedStateOf {
                    when {
                        loading && notes.isEmpty() -> HomeState.Loading
                        username.isNullOrEmpty() -> HomeState.Setup
                        else -> HomeState.Ready
                    }
                } }
                Surface {
                    AnimatedContent(homeState) {
                        when (it) {
                            HomeState.Loading -> { Loading() }

                            HomeState.Setup -> {
                                SetupPage { username ->
                                    vm.updateUsername(username)
                                }
                            }

                            HomeState.Ready -> {
                                NotesScreen(
                                    snackbarHostState = snackbarHostState,
                                    notesViewModel = vm
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3ExpressiveApi::class)
    @Composable
    private fun Loading() = Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadingIndicator(
            modifier = Modifier
                .size(300.dp)
        )
    }

    enum class HomeState {
        Loading,
        Setup,
        Ready,
    }
}