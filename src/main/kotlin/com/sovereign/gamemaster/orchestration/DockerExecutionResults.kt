package com.sovereign.gamemaster.orchestration

// Data class to grab the exit code and message from the docker container

data class DockerExecutionResult(
    val code: Int,
    val outputString: String
)