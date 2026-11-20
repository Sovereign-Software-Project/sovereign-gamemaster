package com.sovereign.gamemaster.core

import com.sovereign.gamemaster.orchestration.DockerMonitorManager
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

class ContainerLogManager (
    private val monitorManager: DockerMonitorManager
) {
    // store timestamp of last clear action per container id
    private val clearTimestamps = ConcurrentHashMap<String, String>()

    private val noisePatterns = listOf(
        "supervisord",
        //"CRIT Supervisor is running as root",
        //"INFO spawned:"
    )

    fun clearLogs(containerId: String) {
        clearTimestamps[containerId] = Instant.now().toString()
    }

    fun resetClearHistory(containerId: String) {
        clearTimestamps.remove(containerId)
    }

    fun getCleanLogs(containerId: String, tailLines: Int = 150): Result<String> {
        val sinceTimeStamp = clearTimestamps[containerId]

        val rawResult = monitorManager.getContainerLogs(
            containerName = containerId,
            tailLines = if (sinceTimeStamp == null) tailLines else null,
            since = sinceTimeStamp
        )

        if (rawResult.code != 0) {
            return Result.failure(RuntimeException(rawResult.outputString))
        }

        val cleanedOutput = rawResult.outputString
            .lineSequence()
            .filter { line -> line.isNotBlank() }
            .filterNot { line -> noisePatterns.any { noise -> line.contains(noise, ignoreCase = true) } }
            .joinToString("\n")
        return Result.success(cleanedOutput)
    }
}