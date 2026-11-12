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
    implementation(compose.material3)
    
    // JUnit 5 Testing Dependencies
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    // Required by Gradle to launch JUnit Platform tests
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
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