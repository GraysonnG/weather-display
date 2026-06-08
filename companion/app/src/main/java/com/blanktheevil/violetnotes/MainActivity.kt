package com.blanktheevil.violetnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                val vm = koinViewModel<NotesViewModel>()
//                val notes by vm.notes.collectAsState()
//                val pendingNotes by vm.pendingNotes.collectAsState()
                val username by vm.username.collectAsState()
                val loading by vm.loading.collectAsState()

                val homeState by remember { derivedStateOf {
                    when {
                        loading -> HomeState.Loading
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
                                TempTesting(vm)
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

    @Composable
    private fun TempTesting(
        viewModel: NotesViewModel,
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = {

            }
        ) { innerPadding ->
            val notes by viewModel.notes.collectAsState()

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                notes.forEach {
                    Column {
                        Text("text: ${it.text}")
                        Text("id: ${it.id}")
                        Text("time_posted: ${it.timePosted}")
                    }
                }
            }
        }
    }

    enum class HomeState {
        Loading,
        Setup,
        Ready,
    }
}