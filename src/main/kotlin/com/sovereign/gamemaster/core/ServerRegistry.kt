package com.sovereign.gamemaster.core

import com.sovereign.gamemaster.model.ContainerState
import com.sovereign.gamemaster.model.ServerSummary
import com.sovereign.gamemaster.orchestration.DockerMonitorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class ServerRegistry(
    private val monitorManager: DockerMonitorManager
) {
    private val _servers = MutableStateFlow<List<ServerSummary>>(emptyList())
    val servers: StateFlow<List<ServerSummary>> = _servers.asStateFlow()

    private val _selectedServerId = MutableStateFlow<String?>(null)
    val selectedServerId: StateFlow<String?> = _selectedServerId.asStateFlow()

    val activeServer: StateFlow<ServerSummary?> = combine(_servers, _selectedServerId) { serverList, id ->
        serverList.find { it.id == id }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = SharingStarted.Eagerly,
        initialValue = null
    )

    fun refreshServers() {
        val result = monitorManager.listContainers()
        if (result.code != 0) return

        val parsedServers = result.outputString.lines()
            .filter { it.isNotBlank() }
            .mapNotNull { line ->
                val parts = line.split("|")
                if (parts.size < 4) return@mapNotNull null

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