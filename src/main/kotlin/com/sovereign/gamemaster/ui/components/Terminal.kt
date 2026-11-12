package com.sovereign.gamemaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig

@Composable
fun TerminalCard(
    logs: String,
    theme: SovereignThemeConfig,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Auto scroll to the latest log line whenever logs update
    LaunchedEffect(logs) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

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
                text = logs.ifEmpty{""},
                color = theme.textMain,
                fontFamily = theme.fontTerminal,
                fontSize = theme.fontSizeSmall
            )
        }
    }
}