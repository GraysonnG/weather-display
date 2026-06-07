package com.blanktheevil.violetnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val notes by vm.notes.collectAsState()

                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(8.dp)
                        ,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        notes.forEach {
                            Column{
                                Text("text: ${it.text}")
                                Text("id: ${it.id}")
                                Text("time_posted: ${it.timePosted}")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VioletNotesTheme {
        Greeting("Android")
    }
}