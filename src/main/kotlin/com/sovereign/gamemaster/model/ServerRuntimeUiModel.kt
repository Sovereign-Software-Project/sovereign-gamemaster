package com.sovereign.gamemaster.model

data class ServerRuntimeUiModel(
    val id: String,
    val name: String,
    val gamePreset: String,
    val artworkKey: String,
    val containerState: ContainerState,
    val cpuUsage: String,
    val memoryUsage: String,
    val playerCount: String,
    val isActionInProgress: Boolean = false
)
