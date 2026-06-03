plugins {
    kotlin("jvm") version "2.3.21"
    id("io.ktor.plugin") version "3.5.0"
    application
}

group = "com.blanktheevil"
version = "1.0-SNAPSHOT"


repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
}

application {
    mainClass.set("MainKt")
}

kotlin {
    jvmToolchain(21)
}

ktor {
    fatJar {
        archiveFileName.set("pi-listener.jar")
    }
}
