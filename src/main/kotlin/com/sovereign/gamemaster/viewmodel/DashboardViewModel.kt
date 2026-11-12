package com.sovereign.gamemaster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sovereign.gamemaster.ui.screens.dashboard.DashboardUiState

/**
 * ViewModel (State & Control Layer) for the Dashboard.
 *
 * Responsibilities:
 * - Holds and updates [DashboardUiState] (the single source of truth for the UI).
 * - Exposes action handlers (start, stop, restart, clear logs) wired to UI callbacks.
 * - Translates backend/Docker execution results into UI-ready state.
 */

class DashboardViewModel {
    var uiState by mutableStateOf(DashboardUiState())
        private set

    fun onPrimaryActionClick() {
        // TODO: Implement start/stop logic later
    }

    fun onRestartClick() {
        // TODO: Implement restart logic later
    }

    fun onClearLogsClick() {
        uiState = uiState.copy(logs = "")
    }
}