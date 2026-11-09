package com.sovereign.gamemaster.storage

import java.io.File

object StoragePathProvider {
    val basePath: String by lazy {
        System.getenv("SOVEREIGN_DATA_DIR") ?: "${System.getProperty("user.home")}/.sovereign/servers"
    }

    fun getServerDir(serverName: String): File = File(basePath, serverName)
    fun getDataDir(serverName: String): File = File(getServerDir(serverName), "data")
    fun getConfigDir(serverName: String): File = File(getServerDir(serverName), "config")
}