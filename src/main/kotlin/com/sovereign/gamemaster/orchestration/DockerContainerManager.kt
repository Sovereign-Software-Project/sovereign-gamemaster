package com.sovereign.gamemaster.orchestration

/**
 * DockerContainerManager.kt
 *
 * RESPONSIBILITY: High-level orchestration and container lifecycle management.
 * Translates intent (create, start, stop, pause, restart, delete) into concrete
 * Docker parameters, managing host volume mounts, port mappings, and environment variables.
 *
 * CONSTRAINTS: Relies on DockerProcessExecutor to interact with the host system.
 * Does not directly handle process execution or raw system calls.
 */

class DockerContainerManager(private val executor: ProcessExecutor) {

    // start new container
    fun startContainer(config: ContainerConfig): DockerExecutionResult {
        return executor.executeCommand(
            "run",
            "-d",
            "--name", config.name,
            "-p", "${config.hostPort}:${config.containerPort}",
            config.image
        )
    }

    // Stop container
    fun stopContainer(containerName: String): DockerExecutionResult {
        return executor.executeCommand("stop", containerName)
    }

    fun pauseContainer(containerName: String): DockerExecutionResult {
        return executor.executeCommand("pause", containerName)
    }

    fun unpauseContainer(containerName: String): DockerExecutionResult {
        return executor.executeCommand("unpause", containerName)
    }

    fun deleteContainer(containerName: String): DockerExecutionResult {
        return executor.executeCommand("rm", containerName)
    }

}