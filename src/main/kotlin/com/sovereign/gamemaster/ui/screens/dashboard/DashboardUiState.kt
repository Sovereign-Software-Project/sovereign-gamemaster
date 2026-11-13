package com.sovereign.gamemaster.ui.screens.dashboard

import com.sovereign.gamemaster.model.ServerRuntimeUiModel

data class DashboardUiState(
    val runtimeModel: ServerRuntimeUiModel? = null,
    val logs: String = "",
    val isLoadingLogs: Boolean = false
)