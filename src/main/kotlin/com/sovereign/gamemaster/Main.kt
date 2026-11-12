package com.sovereign.gamemaster

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.sovereign.gamemaster.ui.screens.dashboard.DashboardScreen
import com.sovereign.gamemaster.viewmodel.DashboardViewModel

fun main() = application {
    val viewModel = remember { DashboardViewModel() }

    Window(onCloseRequest = ::exitApplication, title = "Sovereign GameMaster") {
        DashboardScreen(
            containerState = viewModel.uiState.containerState,
            logOutput = viewModel.uiState.logs,
            onPrimaryActionClick = viewModel::onPrimaryActionClick,
            onRestartClick = viewModel::onRestartClick,
            onClearLogsClick = viewModel::onClearLogsClick
        )
    }
}