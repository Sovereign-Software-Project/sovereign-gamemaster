package com.sovereign.gamemaster.ui

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.ApplicationScope
import com.sovereign.gamemaster.core.ServerRegistry
import com.sovereign.gamemaster.orchestration.DockerMonitorManager
import com.sovereign.gamemaster.orchestration.DockerProcessExecutor
import com.sovereign.gamemaster.ui.screens.dashboard.DashboardScreen
import com.sovereign.gamemaster.viewmodel.DashboardViewModel

@androidx.compose.runtime.Composable
fun ApplicationScope.SovereignGamemasterApp() {
    val processExecutor = remember { DockerProcessExecutor() }
    val monitorManager = remember { DockerMonitorManager(processExecutor) }
    val serverRegistry = remember { ServerRegistry(monitorManager) }
    val viewModel = remember { DashboardViewModel(monitorManager) }

    val activeServer by serverRegistry.activeServer.collectAsState()

    LaunchedEffect(Unit) {
        serverRegistry.refreshServers()
        if (serverRegistry.selectedServerId.value == null) {
            serverRegistry.servers.value.firstOrNull()?.let { firstServer ->
                serverRegistry.selectServer(firstServer.id)
            }
        }
    }

    // Automatically load logs when active server changes, and poll every 3 seconds
    LaunchedEffect(activeServer?.id) {
        activeServer?.id?.let { containerName ->
            viewModel.loadLogs(containerName)
            while(true) {
                kotlinx.coroutines.delay(3000) // Poll every 3 seconds
                viewModel.loadLogs(containerName)
            }
        }
    }

    Window(onCloseRequest = ::exitApplication, title = "Sovereign GameMaster") {
        DashboardScreen(
            activeServer = activeServer,
            logOutput = viewModel.uiState.logs,
            onPrimaryActionClick = viewModel::onPrimaryActionClick,
            onRestartClick = viewModel::onRestartClick,
            onClearLogsClick = viewModel::onClearLogsClick
        )
    }
}