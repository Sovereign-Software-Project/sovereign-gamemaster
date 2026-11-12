package com.sovereign.gamemaster.ui.composites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sovereign.gamemaster.model.ContainerState
import com.sovereign.gamemaster.ui.components.ButtonIntent
import com.sovereign.gamemaster.ui.components.CustomButton
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServerActionBar(
    containerState: ContainerState,
    theme: SovereignThemeConfig,
    onPrimaryAction: () -> Unit,
    onRestart: () -> Unit,
    onClearLogs: () -> Unit,
    isVertical: Boolean = false,
    modifier: Modifier = Modifier
) {
    val primaryIntent = when (containerState) {
        ContainerState.OFFLINE -> ButtonIntent.SUCCESS
        ContainerState.ONLINE -> ButtonIntent.DANGER
        ContainerState.UNKNOWN -> ButtonIntent.WARNING
        ContainerState.STARTING, ContainerState.STOPPING -> ButtonIntent.NEUTRAL
    }

    val buttonModifier = if (isVertical) Modifier.fillMaxWidth() else Modifier

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomButton(
            text = containerState.primaryButtonText,
            onClick = onPrimaryAction,
            theme = theme,
            intent = primaryIntent,
            enabled = containerState.isPrimaryButtonEnabled,
            modifier = buttonModifier
        )

        CustomButton(
            text = "Restart",
            onClick = onRestart,
            theme = theme,
            intent = ButtonIntent.WARNING,
            enabled = containerState.isSecondaryActionEnabled,
            modifier = buttonModifier
        )

        CustomButton(
            text = "Clear Logs",
            onClick = onClearLogs,
            theme = theme,
            intent = ButtonIntent.NEUTRAL,
            modifier = buttonModifier
        )
    }
}