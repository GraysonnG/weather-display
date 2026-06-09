package com.blanktheevil.violetnotes.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.blanktheevil.violetnotes.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val Fancy = TextStyle(
    fontFamily = FontFamily(
        Font(R.font.vibur, FontWeight.Normal)
    ),
    fontWeight = FontWeight.Normal,
    fontSize = 24.sp,
    lineHeight = 28.sp,
)
