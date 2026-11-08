plugins {
    kotlin("jvm") version "2.1.10"
    id("org.jetbrains.compose") version "1.7.3"
    id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
}

group = "com.sovereign.gamemaster"
version = "0.1.0-alpha"

repositories {
    mavenCentral()
    google()
    // JetBrains Compose repository
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    // Standard Kotlin library dependencies
    implementation(kotlin("stdlib"))

    implementation(compose.desktop.currentOs)
}

kotlin {
    jvmToolchain(17) 
}

compose.desktop {
    application {
        mainClass = "com.sovereign.gamemaster.MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Deb
            )
            packageName = "Sovereign GameMaster"
            packageVersion = "0.1.0"
        }
    }
}