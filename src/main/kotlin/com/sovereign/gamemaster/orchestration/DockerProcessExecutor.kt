package com.sovereign.gamemaster.orchestration

/**
 * DockerProcessExecutor.kt
 *
 * RESPONSIBILITY: Low-level system wrapper. Executes raw Docker CLI commands
 * via ProcessBuilder and returns raw execution results (exit code + output).
 *
 * CONSTRAINTS: Does not contain game-specific logic or container configuration
 * defaults. Purely a transport adapter for the host system.
 */

interface ProcessExecutor {
    fun executeCommand(vararg command: String): DockerExecutionResult
}
class DockerProcessExecutor : ProcessExecutor {
    override fun executeCommand(vararg command: String): DockerExecutionResult {

        // prepend "docker" to the command and take the command to this variable
        val fullCommand = listOf("docker", *command)

        // Basic development logging
        println("[EXEC] Executing: ${fullCommand.joinToString(" ")}")

        // configure processBuilder
        val processBuilder = ProcessBuilder(fullCommand).apply {

            // combine stdout and stderr into single stream
            // thus normal logs and error messages are captured together
            redirectErrorStream(true)
        }

        // start process on host machine
        val process = processBuilder.start()

        // read everything the process printed out as a string
        val output = process.inputStream.bufferedReader().use { reader ->
            reader.readText()
        }

        // wait for the process to exit and grab exit code
        val exitCode = process.waitFor()

        return DockerExecutionResult(code = exitCode, outputString = output)
    }
}