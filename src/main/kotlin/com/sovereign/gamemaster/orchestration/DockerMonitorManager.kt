package com.sovereign.gamemaster.orchestration

import com.sovereign.gamemaster.model.ContainerState
import java.lang.invoke.StringConcatFactory

/**
 * RESPONSIBILITY: Handles container diagnostics, resource monitoring, and log streaming.
 */

 class DockerMonitorManager(private val executor: ProcessExecutor) {
    // Docker Executor
    private val processExecutor: ProcessExecutor = DockerProcessExecutor()

    // Fetch resource usage snapshot
    fun getContainerStats(containerName: String): DockerExecutionResult {
        return executor.executeCommand(
            "stats", containerName,
            "--no-stream",
            "--format",
            "{{.CPUPerc}}|{{.MemUsage}}|{{.NetIO}}|{{.BlockIO}}"
        )
    }

    // pull recent stdout/sterr logs
    fun getContainerLogs(
        containerName: String,
        tailLines: Int? = 150,
        since: String? = null
    ): DockerExecutionResult {
        val args = mutableListOf("logs")

        if (since != null) {
            args.addAll(listOf("--since", since))
        } else if (tailLines != null) {
            args.addAll(listOf("--tail", tailLines.toString()))
        }

        args.add(containerName)

        // Use executeCommand, and pass varargs using the spread operator (*)
        return processExecutor.executeCommand(*args.toTypedArray())
    }

    // get containers current state by containerId
    fun getContainerState(containerName: String): ContainerState {
        val result = executor.executeCommand(
            "inspect",
            "--format",
            "{{.State.Status}}",
            containerName
        )

        if (result.code != 0) {
            return ContainerState.OFFLINE
        }

        return when (result.outputString.trim().lowercase()) {
            "running" -> ContainerState.ONLINE
            "restarting" -> ContainerState.STARTING
            "exited", "dead", "created" -> ContainerState.OFFLINE
            else -> ContainerState.UNKNOWN
        }
    }

    // deep state inspection
    fun inspectContainer(containerName:String): DockerExecutionResult {
        return executor.executeCommand("inspect", containerName)
    }

    // list all containers with basic stats
    fun listContainers(): DockerExecutionResult {
        return executor.executeCommand(
            "ps",
            "-a",
            "--format",
            "{{.Names}}|{{.Status}}|{{.Image}}|{{.State}}"
        )
    }

 }