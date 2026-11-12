package com.sovereign.gamemaster.ui.screens.dashboard

import com.sovereign.gamemaster.model.ServerSummary
import com.sovereign.gamemaster.model.ContainerState

data class DashboardUiState(

    // selected server
    val selectedServer: ServerSummary? = null,

    // container state ie. up/down/unkown
    val containerState: ContainerState = ContainerState.UNKNOWN,
    
    // Dashboard-specific details
    val ipAddress: String = "IP ADDRESS",
    val logs: String = ""

    //TODO: add metrics stuff here later
)