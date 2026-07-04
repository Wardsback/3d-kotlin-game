# 3D Kotlin Game

A simple 3D game built with **Kotlin** and **LibGDX**, featuring interactive 3D objects with animations.

## Features

- 🎮 Real-time 3D rendering
- 🔄 Animated rotating cube
- 📐 Rotating pyramid
- 🌍 Orbiting sphere
- 📷 Dynamic camera that orbits around the scene
- ⚡ 60 FPS gameplay
- 🎨 Color-coded 3D objects (Red cube, Green pyramid, Blue sphere)

## Requirements

- Java 11 or higher
- Gradle 7.0+

## Build & Run

### Build the project:
```bash
./gradlew build
```

### Run the game:
```bash
./gradlew run
```

### Create a fat JAR:
```bash
./gradlew jar
java -jar build/libs/3d-kotlin-game-1.0.0.jar
```

## How It Works

The game creates three 3D objects:

1. **Red Cube** - Rotates on multiple axes
2. **Green Pyramid** - Rotates around the Y-axis
3. **Blue Sphere** - Orbits around the center while rotating

The camera automatically orbits around the scene, providing a dynamic viewing experience.

## Project Structure

```
src/main/kotlin/com/example/game3d/
├── Main.kt                 # Game initialization and main game loop
build.gradle.kts           # Gradle build configuration
```

## Libraries Used

- **LibGDX 1.12.1** - Game development framework
- **LWJGL3** - Lightweight Java Game Library for native rendering
- **Kotlin** - Modern JVM language

## Controls

The game runs with automatic animations. No user input required (yet!).

## Next Steps

You can extend this game by:
- Adding keyboard/mouse controls
- Loading 3D models from files
- Adding physics simulation with Bullet
- Implementing lighting effects
- Creating multiple scenes
- Adding sound effects and music

## License

MIT License
