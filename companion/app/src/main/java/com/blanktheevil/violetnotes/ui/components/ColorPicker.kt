package com.blanktheevil.violetnotes.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import com.blanktheevil.violetnotes.data.NoteColor

@Composable
fun ColorPicker(
    modifier: Modifier = Modifier,
    color: NoteColor,
    onColorChange: (NoteColor) -> Unit
) {
    var selectedColor by remember { mutableStateOf(color) }

    LazyRow (
        modifier = modifier
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
                        onColorChange(it)
                    }
            )
        }
    }
}