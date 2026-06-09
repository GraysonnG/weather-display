@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.blanktheevil.violetnotes.ui.pages

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blanktheevil.violetnotes.R
import com.blanktheevil.violetnotes.ui.DefaultPreview
import com.blanktheevil.violetnotes.ui.theme.Fancy

@Composable
fun SetupPage(
    initialPart: Int = 0,
    onComplete: (String) -> Unit,
) {
    val textNext = stringResource(R.string.setup_part_1_button_text)
    val textDone = stringResource(R.string.setup_part_2_button_text)
    val textHello = stringResource(R.string.setup_hello)
    val textPromptPart1 = stringResource(R.string.setup_part_1_prompt)
    val textPromptPart2 = stringResource(R.string.setup_part_2_prompt)
    val textInputPlaceholder = stringResource(R.string.setup_part_2_input_placeholder)

    var setupState by remember { mutableIntStateOf(initialPart) }
    val buttonText by remember { derivedStateOf {
        when (setupState) {
            0 -> textNext
            else -> textDone
        }
    } }
    var nameText by remember { mutableStateOf("") }
    val buttonEnabled by remember { derivedStateOf {
        setupState == 0 || nameText.isNotEmpty()
    } }

    Scaffold(
        floatingActionButton = {
            Button(
                onClick = {
                    if (setupState == 0) {
                        setupState += 1
                    } else {
                        onComplete(nameText)
                    }
                },
                enabled = buttonEnabled,
                contentPadding = ButtonDefaults.SmallContentPadding
            ) {
                Text(buttonText)
            }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            AnimatedContent(
                setupState,
                modifier = Modifier.align(Alignment.Center)
            ) {
                when(it) {
                    0 -> {
                        PartOne(textHello = textHello, textPromptPart1 = textPromptPart1)
                    }
                    else -> {
                        PartTwo(
                            textPromptPart2 = textPromptPart2,
                            nameText = nameText,
                            textInputPlaceholder = textInputPlaceholder
                        ) { newText ->
                            nameText = newText
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PartOne(
    textHello: String,
    textPromptPart1: String,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Box(Modifier.size(300.dp)) {
        LoadingIndicator(
            modifier = Modifier.size(300.dp)
        )
        Text(
            modifier = Modifier.align(Alignment.Center).offset(y = 4.dp),
            text = textHello,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = Fancy.fontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 48.sp,
            lineHeight = 48.sp,
        )
    }

    Text(textPromptPart1, fontWeight = FontWeight.Bold)
}

@Composable
private fun PartTwo(
    textPromptPart2: String,
    nameText: String,
    textInputPlaceholder: String,
    onValueChange: (String) -> Unit,
) = Column(
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = textPromptPart2,
        fontFamily = Fancy.fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp,
        lineHeight = 48.sp,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.size(8.dp))

    OutlinedTextField(
        value = nameText,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(100.dp),
        placeholder = { Text(textInputPlaceholder) }
    )
}

@Composable
@PreviewLightDark
private fun SetupPagePreview() = DefaultPreview {
    SetupPage {

    }
}

@Composable
@PreviewLightDark
private fun SetupPagePartTwoPreview() = DefaultPreview {
    SetupPage(initialPart = 1) {

    }
}