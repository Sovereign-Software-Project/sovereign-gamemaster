package com.sovereign.gamemaster.config

import com.sovereign.gamemaster.model.ContainerConfig

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