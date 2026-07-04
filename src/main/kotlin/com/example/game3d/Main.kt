package com.example.game3d

import com.badlogic.gdx.ApplicationListener
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.*
import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.math.Vector3
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    val config = Lwjgl3ApplicationConfiguration()
    config.setWindowedMode(1200, 800)
    config.setTitle("3D Kotlin Game - Rotating Cube")
    config.useVsync(true)
    config.setForegroundFPS(60)
    
    Lwjgl3Application(Game3D(), config)
}

class Game3D : ApplicationListener {
    private lateinit var modelBatch: ModelBatch
    private lateinit var environment: Environment
    private lateinit var camera: PerspectiveCamera
    
    // Game objects
    private lateinit var cubeModel: Model
    private lateinit var pyramidModel: Model
    private lateinit var sphereModel: Model
    
    private lateinit var cubeInstance: ModelInstance
    private lateinit var pyramidInstance: ModelInstance
    private lateinit var sphereInstance: ModelInstance
    
    private var rotation = 0f
    
    override fun create() {
        // Initialize camera
        camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.position.set(10f, 10f, 10f)
        camera.lookAt(0f, 0f, 0f)
        camera.update()
        
        // Initialize rendering
        modelBatch = ModelBatch()
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f))
        environment.add(DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f))
        
        // Create 3D models
        val modelBuilder = ModelBuilder()
        
        // Cube
        modelBuilder.begin()
        val cubeMaterial = Material(ColorAttribute.createDiffuse(Color.RED))
        modelBuilder.box(2f, 2f, 2f, cubeMaterial)
        cubeModel = modelBuilder.end()
        cubeInstance = ModelInstance(cubeModel)
        cubeInstance.transform.setToTranslation(-5f, 0f, 0f)
        
        // Pyramid (using a cone)
        modelBuilder.begin()
        val pyramidMaterial = Material(ColorAttribute.createDiffuse(Color.GREEN))
        modelBuilder.cone(2f, 4f, 2f, 8, pyramidMaterial)
        pyramidModel = modelBuilder.end()
        pyramidInstance = ModelInstance(pyramidModel)
        pyramidInstance.transform.setToTranslation(0f, 0f, 0f)
        
        // Sphere
        modelBuilder.begin()
        val sphereMaterial = Material(ColorAttribute.createDiffuse(Color.BLUE))
        modelBuilder.sphere(1.5f, 1.5f, 1.5f, 32, 32, sphereMaterial)
        sphereModel = modelBuilder.end()
        sphereInstance = ModelInstance(sphereModel)
        sphereInstance.transform.setToTranslation(5f, 0f, 0f)
        
        Gdx.input.inputProcessor = GestureDetector(object : GestureDetector.GestureListener {
            override fun touchDown(x: Float, y: Float, pointer: Int, button: Int) = false
            override fun tap(x: Float, y: Float, count: Int, button: Int) = false
            override fun longPress(x: Float, y: Float) = false
            override fun fling(velocityX: Float, velocityY: Float, button: Int) = false
            override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float) = false
            override fun panStop(x: Float, y: Float, pointer: Int, button: Int) = false
            override fun zoom(initialDistance: Float, distance: Float) = false
            override fun pinch(initialPointer1: Vector3, initialPointer2: Vector3, pointer1: Vector3, pointer2: Vector3) = false
            override fun pinchStop() {}
        })
    }
    
    override fun render() {
        // Clear screen
        Gdx.gl.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
        
        // Update rotation
        rotation += 0.5f
        
        // Rotate cube
        cubeInstance.transform.setToTranslation(-5f, 0f, 0f)
        cubeInstance.transform.rotate(1f, 1f, 0f, rotation)
        
        // Rotate pyramid
        pyramidInstance.transform.setToTranslation(0f, 0f, 0f)
        pyramidInstance.transform.rotate(0f, 1f, 0f, rotation)
        
        // Orbit sphere around center
        val orbitRadius = 4f
        val orbitX = orbitRadius * cos(Math.toRadians(rotation.toDouble())).toFloat()
        val orbitZ = orbitRadius * sin(Math.toRadians(rotation.toDouble())).toFloat()
        sphereInstance.transform.setToTranslation(orbitX, 1f, orbitZ)
        sphereInstance.transform.rotate(0f, 1f, 0f, rotation)
        
        // Update camera to orbit around scene
        val camRadius = 15f
        val camX = camRadius * cos(Math.toRadians((rotation * 0.5f).toDouble())).toFloat()
        val camZ = camRadius * sin(Math.toRadians((rotation * 0.5f).toDouble())).toFloat()
        camera.position.set(camX, 8f, camZ)
        camera.lookAt(0f, 0f, 0f)
        camera.update()
        
        // Render
        modelBatch.begin(camera)
        modelBatch.render(cubeInstance, environment)
        modelBatch.render(pyramidInstance, environment)
        modelBatch.render(sphereInstance, environment)
        modelBatch.end()
    }
    
    override fun resize(width: Int, height: Int) {
        camera.viewportWidth = width.toFloat()
        camera.viewportHeight = height.toFloat()
        camera.update()
    }
    
    override fun pause() {}
    override fun resume() {}
    
    override fun dispose() {
        modelBatch.dispose()
        cubeModel.dispose()
        pyramidModel.dispose()
        sphereModel.dispose()
    }
}
