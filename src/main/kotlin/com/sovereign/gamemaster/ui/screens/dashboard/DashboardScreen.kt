package com.sovereign.gamemaster.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sovereign.gamemaster.model.ContainerState
import com.sovereign.gamemaster.ui.components.TerminalCard
import com.sovereign.gamemaster.ui.composites.ServerActionBar
import com.sovereign.gamemaster.ui.theme.SovereignThemeConfig
import com.sovereign.gamemaster.ui.theme.ThemePreset

@Composable
fun DashboardScreen(
    containerState: ContainerState,
    logOutput: String,
    onPrimaryActionClick: () -> Unit,
    onRestartClick: () -> Unit,
    onClearLogsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTheme = remember { SovereignThemeConfig.fromPreset(ThemePreset.MINIMAL_DARK) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(currentTheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ServerHeader(
        //     serverName = "SERVER NAME HERE",
        //     details = "LIST DETAILS HERE",
        //     containerState = containerState,
        //     theme = currentTheme
        // )

        ServerActionBar(
            containerState = containerState,
            theme = currentTheme,
            onPrimaryAction = onPrimaryActionClick,
            onRestart = onRestartClick,
            onClearLogs = onClearLogsClick
        )

        TerminalCard(
            logs = logOutput,
            theme = currentTheme,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}