package com.sovereign.gamemaster.orchestration

/**
 * RESPONSIBILITY: Handles container diagnostics, resource monitoring, and log streaming.
 */

 class DockerMonitorManager(private val executor: ProcessExecutor) {

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
    fun getContainerLogs(containerName: String, tailLines: Int = 100): DockerExecutionResult {
        return executor.executeCommand("logs", "--tail", tailLines.toString(), containerName)
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