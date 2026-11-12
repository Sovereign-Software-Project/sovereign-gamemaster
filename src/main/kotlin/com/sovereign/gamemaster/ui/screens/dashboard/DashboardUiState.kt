package com.sovereign.gamemaster.ui.screens.dashboard

import com.sovereign.gamemaster.model.ServerSummary
import com.sovereign.gamemaster.model.ContainerState

data class DashboardUiState(

    val containerState: ContainerState = ContainerState.UNKNOWN,

    // Embed shared server model
    val server: ServerSummary = ServerSummary(),
    
    // Dashboard-specific details (not needed on the home screen card)
    val ipAddress: String = "IP ADDRESS",
    val logs: String = "[INFO] PLACEHOLDER LOG READING\n[INFO] PLACEHOLDER"

    //TODO: add metrics stuff here later
)