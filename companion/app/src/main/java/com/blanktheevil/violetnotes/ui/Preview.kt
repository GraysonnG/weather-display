package com.blanktheevil.violetnotes.ui

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.blanktheevil.violetnotes.di.appModule
import com.blanktheevil.violetnotes.ui.theme.VioletNotesTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

@Composable
fun DefaultPreview(block: @Composable () -> Unit) {
    val context = LocalContext.current
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            androidContext(context)
            modules(appModule)
        }
    }

    VioletNotesTheme {
        Surface {
            block()
        }
    }
}