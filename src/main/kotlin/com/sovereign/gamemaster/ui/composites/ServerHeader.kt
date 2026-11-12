package com.sovereign.gamemaster.ui.composites

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sovereign.gamemaster.model.ServerSummary
import com.sovereign.gamemaster.ui.components.ContainerStatusBadge
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig

@Composable
fun ServerHeader(
    server: ServerSummary,
    theme: SovereignThemeConfig,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val backgroundColor = if (isSelected) {
        theme.surface.copy(alpha = 0.95f)
    } else {
        theme.surface.copy(alpha = 0.5f)
    }

    val borderColor = if (isSelected) theme.primary else theme.borderColor
    val shape = RoundedCornerShape(theme.cornerRadius)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(backgroundColor)
            .border(width = theme.borderWidth, color = borderColor, shape = shape)
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Top Row: Big Title & Dynamic Status Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = server.name,
                    color = theme.textMain,
                    fontSize = theme.fontSizeLarge,
                    fontWeight = FontWeight.Bold,
                    fontFamily = theme.fontPrimary
                )

                ContainerStatusBadge(
                    state = server.status,
                    theme = theme
                )
            }

            // Details List Below
            Text(
                text = "Image: ${server.gamePreset}",
                color = theme.textMuted,
                fontSize = theme.fontSizeSmall,
                fontFamily = theme.fontPrimary
            )

            Text(
                text = "ID: ${server.id}",
                color = theme.textMuted.copy(alpha = 0.7f),
                fontSize = 10.sp,
                fontFamily = theme.fontTerminal
            )
        }
    }
}