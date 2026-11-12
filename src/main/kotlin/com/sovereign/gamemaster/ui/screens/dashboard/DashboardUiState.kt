package com.sovereign.gamemaster.ui.screens.dashboard

import com.sovereign.gamemaster.model.ServerSummary

data class DashboardUiState(
    // Embed shared server model
    val server: ServerSummary = ServerSummary(),
    
    // Dashboard-specific details (not needed on the home screen card)
    val ipAddress: String = "IP ADDRESS",
    val logs: String = "[INFO] PLACEHOLDER LOG READING"\n"[INFO] PLACEHOLDER"

    //TODO: add metrics stuff here later
)