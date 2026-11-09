package com.sovereign.gamemaster.orchestration

// Data class defining variables to pass when building a container

data class ContainerConfig(
    val name: String,
    val image: String,
    val hostPort: Int,
    val containerPort: Int
)