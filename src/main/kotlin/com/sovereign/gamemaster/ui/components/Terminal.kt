package com.sovereign.gamemaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig

@Composable
fun TerminalCard(
    logs: String,
    theme: SovereignThemeConfig,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // wrap in a styled surface using theme config
    Box(
        modifier = modifier
        .background(theme.surface, shape = RoundedCornerShape(theme.cornerRadius))
        .border(theme.borderWidth, theme.borderColor, shape = RoundedCornerShape(theme.cornerRadius))
        .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = logs.ifEmpty{"Waiting for container output..."},
                color = theme.textMain,
                fontFamily = theme.fontTerminal,
                fontSize = theme.fontSizeSmall
            )
        }
    }
}