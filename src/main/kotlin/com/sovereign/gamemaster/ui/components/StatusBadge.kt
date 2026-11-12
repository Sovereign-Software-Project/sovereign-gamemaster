package com.sovereign.gamemaster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sovereign.gamemaster.model.ContainerState
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig

@Composable
fun StatusBadge(
    text: String,
    badgeColor: Color, // Pass theme.successColor, theme.warningColor, or theme.dangerColor
    theme: SovereignThemeConfig,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = badgeColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(50.dp)
            )
            .border(
                width = 1.dp,
                color = badgeColor.copy(alpha = 0.4f),
                shape = RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text.uppercase(),
            color = badgeColor,
            fontFamily = theme.fontPrimary,
            fontSize = theme.fontSizeSmall
        )
    }
}

@Composable
fun ContainerStatusBadge(
    state: ContainerState,
    theme: SovereignThemeConfig,
    modifier: Modifier = Modifier
) {
    val (label, color) = when (state) {
        ContainerState.ONLINE -> "Online" to theme.successColor
        ContainerState.OFFLINE -> "Offline" to theme.dangerColor
        ContainerState.STARTING -> "Starting" to theme.warningColor
        ContainerState.STOPPING -> "Stopping" to theme.warningColor
        ContainerState.UNKNOWN -> "Unknown" to Color.Gray
    }

    StatusBadge(
        text = label,
        badgeColor = color,
        theme = theme,
        modifier = modifier
    )
}