package com.sovereign.gamemaster.config

object ServerPresets {
    val defaultPresets = listOf(
        
        ContainerConfig(
            name = "alpine-test-server",
            image = "alpine",
            hostPort = 8080,
            containerPort = 80
        )
    
    )
}