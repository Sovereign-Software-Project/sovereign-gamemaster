package com.sovereign.gamemaster.core

import com.sovereign.gamemaster.model.ContainerState
import com.sovereign.gamemaster.model.ServerRuntimeUiModel
import com.sovereign.gamemaster.model.ServerSummary
import com.sovereign.gamemaster.orchestration.DockerMonitorManager
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.time.LocalTime
import kotlin.time.Duration.Companion.milliseconds

class ServerRegistry(
    private val monitorManager: DockerMonitorManager
) {
    private val registryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _servers = MutableStateFlow<List<ServerSummary>>(emptyList())
    val servers: StateFlow<List<ServerSummary>> = _servers.asStateFlow()

    private val _selectedServerId = MutableStateFlow<String?>(null)
    val selectedServerId: StateFlow<String?> = _selectedServerId.asStateFlow()

    val activeRuntimeServer: StateFlow<ServerRuntimeUiModel?> = combine(_servers, _selectedServerId) { serverList, id ->
        val summary = serverList.find { it.id == id } ?: serverList.firstOrNull()
        summary?.toRuntimeModel(currentState = summary.status)
    }.stateIn(
        scope = registryScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    init {
        refreshServers()
        startGlobalPolling()
    }

    private fun startGlobalPolling() {
        registryScope.launch {
            while (true) {
                delay(3000L.milliseconds)
                refreshServers()
            }
        }
    }

    fun refreshServers() {
        val timestamp = LocalTime.now()
        val result = monitorManager.listContainers()

        if (result.code != 0) {
            println("[$timestamp] ERROR: Docker listContainers failed with output: ${result.outputString}")
            return
        }

        val parsedServers = result.outputString.lines()
            .filter { it.isNotBlank() }
            .mapNotNull { line ->
                val parts = line.split("|")
                if (parts.size < 4) {
                    println("[$timestamp] WARNING: Line skipped (parts size < 4): '$line'")
                    return@mapNotNull null
                }

                val name = parts[0].trim()
                val image = parts[2].trim()
                val stateStr = parts[3].trim()
                val cleanImage = image.substringAfter("/").substringBefore(":")

                ServerSummary(
                    id = name,
                    name = name,
                    gamePreset = cleanImage,
                    artworkKey = cleanImage,
                    status = parseContainerState(stateStr)
                )
            }

        _servers.value = parsedServers

        val currentSelectedId = _selectedServerId.value
        val selectedStillExists = parsedServers.any { it.id == currentSelectedId }

        if (!selectedStillExists && parsedServers.isNotEmpty()) {
            val fallbackId = parsedServers.first().id
            println("[$timestamp] Selection sync: Previous selected '$currentSelectedId' not found. Falling back to '$fallbackId'.")
            _selectedServerId.value = fallbackId
        }
    }

    fun selectServer(serverId: String?) {
        _selectedServerId.value = serverId
    }

    private fun parseContainerState(state: String): ContainerState {
        return when (state.lowercase()) {
            "running" -> ContainerState.ONLINE
            "exited", "dead" -> ContainerState.OFFLINE
            "starting" -> ContainerState.STARTING
            "stopping" -> ContainerState.STOPPING
            else -> ContainerState.UNKNOWN
        }
    }
}

fun ServerSummary.toRuntimeModel(currentState: ContainerState, isBusy: Boolean = false): ServerRuntimeUiModel {
    return ServerRuntimeUiModel(
        id = this.id,
        name = this.name,
        gamePreset = this.gamePreset,
        artworkKey = this.artworkKey,
        containerState = currentState,
        cpuUsage = this.cpuUsage,
        memoryUsage = this.memoryUsage,
        playerCount = this.playerCount,
        isActionInProgress = isBusy
    )
}