import org.lwjgl.glfw.*
import org.lwjgl.glfw.Callbacks.glfwFreeCallbacks
import org.lwjgl.glfw.GLFW.*
import org.lwjgl.system.MemoryUtil.NULL
import java.lang.IllegalStateException
import java.lang.RuntimeException
import org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose

import org.lwjgl.glfw.GLFW.GLFW_RELEASE

import org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE

import org.lwjgl.glfw.GLFW.glfwSetKeyCallback
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GL11.*
import org.lwjgl.system.MemoryStack
import org.lwjgl.system.MemoryStack.stackPush
import java.util.*


fun main() {
    Main()
}

class Main() {

    private var window: Long? = null
    private var camPos: Vec3d = Vec3d()

    init {

        init()
        loop()

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(this.window!!)
        glfwDestroyWindow(this.window!!)

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null)!!.free();

    }

    private fun init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set()

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) throw IllegalStateException()

        // Configure GLFW
        glfwDefaultWindowHints() // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable

        // Create the window
        this.window = glfwCreateWindow(300, 300, "Hello World!", NULL, NULL)
        if (this.window == null) throw RuntimeException()

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(this.window!!) { _, key, _, action, _ ->
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(this.window!!, true) // We will detect this in the rendering loop
            }
        }

        // Get the thread stack and push a new frame
        val stack = stackPush()
        val pWidth = stack.mallocInt(1)
        val pHeight = stack.mallocInt(1)

        // Get the window size passed to glfwCreateWindow
        glfwGetWindowSize(this.window!!, pWidth, pHeight)

        // Get the resolution of the primary monitor
        val vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor())!!

        // Center the window
        glfwSetWindowPos(this.window!!, (vidMode.width() - pWidth.get(0)) / 2, (vidMode.height() - pHeight.get(0)) / 2)

        // Make the OpenGL context current
        glfwMakeContextCurrent(this.window!!)
        // Enable v-sync
        glfwSwapInterval(1)

        // Make the window visible
        glfwShowWindow(this.window!!)
    }

    private fun loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities()

        // Set the clear color
        glClearColor(.1f, .1f, .1f, 0f)

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(this.window!!)) {
            updateCamPos()
            glClear(GL_COLOR_BUFFER_BIT or GL_DEPTH_BUFFER_BIT ) // clear the framebuffer
            Cube(1f, this.camPos)
            glfwSwapBuffers(this.window!!) // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents()
        }

    }

    private fun updateCamPos() {
        if (isPressed(GLFW_KEY_W)) {
            this.camPos = this.camPos.add(Vec3d(0f, 0f, CAM_SPEED))
        }
        if (isPressed(GLFW_KEY_S)) {
            this.camPos = this.camPos.add(Vec3d(0f, 0f, -CAM_SPEED))
        }
        if (isPressed(GLFW_KEY_D)) {
            this.camPos = this.camPos.add(Vec3d(CAM_SPEED, 0f, 0f))
        }
        if (isPressed(GLFW_KEY_A)) {
            this.camPos = this.camPos.add(Vec3d(-CAM_SPEED, 0f, 0f))
        }
        if (isPressed(GLFW_KEY_Q)) {
            this.camPos = this.camPos.add(Vec3d(0f, CAM_SPEED, 0f))
        }
        if (isPressed(GLFW_KEY_E)) {
            this.camPos = this.camPos.add(Vec3d(0f, -CAM_SPEED, 0f))
        }
    }

    private fun isPressed(key: Int): Boolean {
        return glfwGetKey(this.window!!, key) == GLFW_PRESS;
    }

    companion object {
        const val CAM_SPEED = .05f
    }

}
