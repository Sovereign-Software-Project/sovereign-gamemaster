package com.sovereign.gamemaster.orchestration

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DockerContainerManagerTest {

    // A lightweight fake executor that records commands and returns canned responses
    class FakeProcessExecutor(
        private val mockResult: DockerExecutionResult = DockerExecutionResult(0, "mock_container_id")
    ) : ProcessExecutor {
        val executedCommands = mutableListOf<List<String>>()
        val streamedCommands = mutableListOf<List<String>>()

        override fun executeCommand(vararg command: String): DockerExecutionResult {
            executedCommands.add(command.toList())
            return mockResult
        }

        override suspend fun streamCommand(command: List<String>, onNewLine: (String) -> Unit) {
            streamedCommands.add(command)
            onNewLine(mockResult.outputString)
        }
    }

    @Test
    fun `startContainer passes correct arguments to docker run`() {
        // Arrange
        val config = ContainerConfig(
            name = "test-game-server",
            image = "itzg/minecraft-server",
            hostPort = 25565,
            containerPort = 25565
        )
        val fakeExecutor = FakeProcessExecutor(DockerExecutionResult(0, "sha256:12345"))
        
        // Stateless manager takes only the executor
        val manager = DockerContainerManager(fakeExecutor)

        // Act - config is passed directly into the method now
        val result = manager.startContainer(config)

        // Assert
        assertEquals(0, result.code)
        assertEquals("sha256:12345", result.outputString)

        // Verify the command structure sent to Docker
        val expectedCommand = listOf(
            "run", "-d",
            "--name", "test-game-server",
            "-p", "25565:25565",
            "itzg/minecraft-server"
        )
        assertEquals(expectedCommand, fakeExecutor.executedCommands[0])
    }

    @Test
    fun `all lifecycle methods send correct docker commands`() {
        val fakeExecutor = FakeProcessExecutor()
        val manager = DockerContainerManager(fakeExecutor)

        manager.stopContainer("my-server")
        manager.pauseContainer("my-server")
        manager.unpauseContainer("my-server")
        manager.deleteContainer("my-server")
        manager.restartContainer("my-server")

        val expectedCommands = listOf(
            listOf("stop", "my-server"),
            listOf("pause", "my-server"),
            listOf("unpause", "my-server"),
            listOf("rm", "my-server"),
            listOf("restart", "my-server")
        )

        assertEquals(expectedCommands, fakeExecutor.executedCommands)
    }
}