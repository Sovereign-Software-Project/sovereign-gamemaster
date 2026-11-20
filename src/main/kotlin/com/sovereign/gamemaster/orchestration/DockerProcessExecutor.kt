package com.sovereign.gamemaster.orchestration

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader

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
    suspend fun streamCommand(command: List<String>, onNewLine: (String) -> Unit)
}

class DockerProcessExecutor : ProcessExecutor {
    override fun executeCommand(vararg command: String): DockerExecutionResult 
    {
        // prepend "docker" to the command and take the command to this variable
        val fullCommand = listOf("docker", *command)

        val processBuilder = ProcessBuilder(fullCommand).apply {
            redirectErrorStream(true)
        }

        val process = processBuilder.start()
        val output = process.inputStream.use { inputStream ->
            inputStream.bufferedReader().readText()
        }
        val exitCode = process.waitFor()

        return DockerExecutionResult(code = exitCode, outputString = output)
    }

    override suspend fun streamCommand(command: List<String>, onNewLine: (String) -> Unit) {
        withContext(Dispatchers.IO) {
            try {
                val fullCommand = listOf("docker") + command
                println("[EXEC STREAM] Starting: ${fullCommand.joinToString(" ")}")

                val processBuilder = ProcessBuilder(fullCommand).apply {
                    redirectErrorStream(true) // merges stdout & stderr
                }

                val process = processBuilder.start()

                // wrap process input stream in a bufferedReader for line by line reading
                val reader = BufferedReader(InputStreamReader(process.inputStream))

                // read lines iteratively as produced
                var line: String? = reader.readLine()
                while (line != null) {
                    onNewLine(line) // pass to UI/caller
                    line = reader.readLine()
                }

                process.waitFor()
                onNewLine("[Process exited with code ${process.exitValue()}]")
            } catch (e: Exception) {
                onNewLine("[Error streaming process: ${e.localizedMessage}]")
            }
        }
    }
}