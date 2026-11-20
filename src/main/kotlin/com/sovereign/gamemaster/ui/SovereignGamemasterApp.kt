package com.sovereign.gamemaster.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ApplicationScope
import androidx.compose.ui.window.Window
import com.sovereign.gamemaster.core.ContainerLogManager
import com.sovereign.gamemaster.core.ServerRegistry
import com.sovereign.gamemaster.orchestration.DockerContainerManager
import com.sovereign.gamemaster.orchestration.DockerMonitorManager
import com.sovereign.gamemaster.orchestration.DockerProcessExecutor
import com.sovereign.gamemaster.ui.screens.dashboard.DashboardScreen
import com.sovereign.gamemaster.viewmodel.DashboardViewModel

@Composable
fun ApplicationScope.SovereignGamemasterApp() {
    val processExecutor = remember { DockerProcessExecutor() }
    val monitorManager = remember { DockerMonitorManager(processExecutor) }
    val containerManager = remember { DockerContainerManager(processExecutor) }
    val logManager = remember { ContainerLogManager(monitorManager) }

    // ServerRegistry automatically starts its own global background polling on init
    val serverRegistry = remember { ServerRegistry(monitorManager) }

    // DashboardViewModel automatically observes the registry and polls its own logs
    val viewModel = remember {
        DashboardViewModel(
            containerManager = containerManager,
            logManager = logManager,
            serverRegistry = serverRegistry
        )
    }

    // Collect the rich runtime model from the registry (via activeRuntimeServer)
    val activeRuntimeServer by serverRegistry.activeRuntimeServer.collectAsState()

    Window(onCloseRequest = ::exitApplication, title = "Sovereign GameMaster") {
        DashboardScreen(
            activeServer = activeRuntimeServer,
            logOutput = viewModel.uiState.logs,
            onPrimaryActionClick = viewModel::onPrimaryActionClick,
            onRestartClick = viewModel::onRestartClick,
            onClearLogsClick = viewModel::onClearLogsClick
        )
    }
}