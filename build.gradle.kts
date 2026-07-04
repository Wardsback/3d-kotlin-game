plugins {
    kotlin("jvm") version "1.9.10"
    application
}

group = "com.example.game3d"
version = "1.0.0"

repositories {
    mavenCentral()
}

val gdxVersion = "1.12.1"

dependencies {
    // LibGDX Core
    implementation("com.badlogicgames.gdx:gdx:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    
    // For 3D rendering
    implementation("com.badlogicgames.gdx:gdx-bullet:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-bullet-platform:$gdxVersion:natives-desktop")
    
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("com.example.game3d.MainKt")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "com.example.game3d.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}
