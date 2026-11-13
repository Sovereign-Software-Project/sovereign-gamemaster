package com.sovereign.gamemaster.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sovereign.gamemaster.core.ContainerLogManager
import com.sovereign.gamemaster.core.ServerRegistry
import com.sovereign.gamemaster.model.ContainerState
import com.sovereign.gamemaster.orchestration.DockerContainerManager
import com.sovereign.gamemaster.ui.screens.dashboard.DashboardUiState
import kotlinx.coroutines.*
import kotlin.time.Duration.Companion.milliseconds

class DashboardViewModel(
    private val containerManager: DockerContainerManager,
    private val logManager: ContainerLogManager,
    private val serverRegistry: ServerRegistry
) {
    var uiState by mutableStateOf(DashboardUiState())
        private set

    private val viewModelScope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    private var logPollingJob: Job? = null

    init {
        // Automatically listen to the active runtime server from the registry
        viewModelScope.launch {
            serverRegistry.activeRuntimeServer.collect { runtimeModel ->
                logPollingJob?.cancel() // Stop previous log loop if server changes

                uiState = uiState.copy(runtimeModel = runtimeModel)

                if (runtimeModel != null) {
                    startLogPolling(runtimeModel.id)
                } else {
                    uiState = uiState.copy(logs = "")
                }
            }
        }
    }

    private fun startLogPolling(containerId: String) {
        logPollingJob = viewModelScope.launch {
            while (true) {
                fetchLogs(containerId)
                delay(300L.milliseconds) // Poll fresh logs every 3 seconds
            }
        }
    }

    private fun fetchLogs(containerName: String) {
        val result = logManager.getCleanLogs(containerName, tailLines = 150)
        uiState = if (result.isSuccess) {
            uiState.copy(logs = result.getOrNull() ?: "")
        } else {
            uiState.copy(logs = "Error loading logs:\n${result.exceptionOrNull()?.localizedMessage}")
        }
    }

    fun onPrimaryActionClick() {
        val model = uiState.runtimeModel ?: return
        val containerId = model.id

        val result = when (model.containerState) {
            ContainerState.OFFLINE -> containerManager.startExistingContainer(containerId)
            ContainerState.ONLINE -> containerManager.stopContainer(containerId)
            else -> return
        }

        if (result.code == 0) {
            fetchLogs(containerId)
            serverRegistry.refreshServers() // Force immediate registry status update
        }
    }

    fun onRestartClick() {
        uiState.runtimeModel?.id?.let { containerId ->
            val result = containerManager.restartContainer(containerId)
            if (result.code == 0) {
                fetchLogs(containerId)
                serverRegistry.refreshServers()
            }
        }
    }

    fun onClearLogsClick() {
        uiState.runtimeModel?.id?.let { containerName ->
            logManager.clearLogs(containerName)
        }
        uiState = uiState.copy(logs = "")
    }
}