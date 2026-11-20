package com.sovereign.gamemaster.model

enum class ContainerState(
    val displayLabel: String,
    val primaryButtonText: String,
    val isPrimaryButtonEnabled: Boolean,
    val isSecondaryActionEnabled: Boolean,
    val isActive: Boolean
) {
    UNKNOWN("Unavailable", "Unavailable", true, false, false),
    OFFLINE("OFFLINE", "Start Server", true, false, isActive = false),
    STARTING("STARTING...", "Starting...", false, false, isActive = true),
    ONLINE("ONLINE", "Stop Server", true, true, isActive = true),
    STOPPING("STOPPING...", "Stopping...", false, false, isActive = true)
}

data class ServerSummary(
    val id: String = "",
    val name: String = "",
    val gamePreset: String = "",
    val artworkKey: String = "",
    val status: ContainerState = ContainerState.UNKNOWN,
    val cpuUsage: String = "",
    val memoryUsage: String = "",
    val playerCount: String = ""
)