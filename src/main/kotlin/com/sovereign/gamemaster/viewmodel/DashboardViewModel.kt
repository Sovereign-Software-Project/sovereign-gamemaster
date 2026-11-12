package com.sovereign.gamemaster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sovereign.gamemaster.orchestration.DockerMonitorManager
import com.sovereign.gamemaster.ui.screens.dashboard.DashboardUiState

/**
 * ViewModel (State & Control Layer) for the Dashboard.
 *
 * Responsibilities:
 * - Holds and updates [DashboardUiState] (the single source of truth for the UI).
 * - Exposes action handlers (start, stop, restart, clear logs) wired to UI callbacks.
 * - Translates backend/Docker execution results into UI-ready state.
 */

class DashboardViewModel(
    private val monitorManager: DockerMonitorManager
) {
    var uiState by mutableStateOf(DashboardUiState())
        private set

    fun onPrimaryActionClick() {
        // TODO: Implement start/stop logic later
    }

    fun onRestartClick() {
        // TODO: Implement restart logic later
    }

    // Called when the active server changes from Main/SovereignApp
    fun loadLogs(containerName: String) {
        // If your DashboardUiState also updates selectedServer elsewhere, 
        // fetchLogs will use it directly.
        fetchLogs(containerName)
    }

    private fun fetchLogs(containerName: String) {
        val result = monitorManager.getContainerLogs(containerName, tailLines = 150)
        uiState = if (result.code == 0) {
            uiState.copy(logs = result.outputString)
        } else {
            uiState.copy(logs = "Error loading logs:\n${result.outputString}")
        }
    }

    // Refreshes logs using the currently selected server's ID/name
    fun onRefreshLogsClick() {
        uiState.selectedServer?.id?.let { containerName ->
            fetchLogs(containerName)
        }
    }

    fun onClearLogsClick() {
        uiState = uiState.copy(logs = "")
    }
}