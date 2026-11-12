package com.sovereign.gamemaster.model

data class ContainerConfig(
    val name: String,
    val image: String,
    val hostPort: Int,
    val containerPort: Int
)