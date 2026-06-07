package com.blanktheevil.violetnotes.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import com.blanktheevil.violetnotes.R

@Composable
private fun createDarkTheme() = darkColorScheme(
    primary = colorResource(R.color.slate_primary_dark),
    secondary = colorResource(R.color.slate_secondary_dark),
    tertiary = colorResource(R.color.slate_tertiary_dark),
)

@Composable
private fun createLightTheme() = lightColorScheme(
    primary = colorResource(R.color.slate_primary_light),
    secondary = colorResource(R.color.slate_secondary_light),
    tertiary = colorResource(R.color.slate_tertiary_light),
)

@Composable
fun VioletNotesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> createDarkTheme()
        else -> createLightTheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}