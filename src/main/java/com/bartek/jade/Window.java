package com.bartek.jade;

import lombok.extern.slf4j.Slf4j;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class Window {

    private int width;
    private int height;
    private String title;
    private long glfwWindow; //memory address where the window is

    private static Window window;

    public Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Game";
    }

    public static Window get() { //tworzone tylko raz podczas uruchamiania apki
        if (Window.window == null) {
            Window.window = new Window();
        }
        log.info("Window is running with settings: width %s, and height %s", window.height, window.width);
        return window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion());

        init();
        loop();
    }

    private void loop() {
        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            glfwPollEvents();

            // Set the clear color
            glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
            glClear(GL_COLOR_BUFFER_BIT); //flush color to entire screen

            glfwSwapBuffers(glfwWindow);
        }

    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();
        log.error("We have got an ERROR");

        // Initiallize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW : resizable window for example
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create Window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL); //memory address where the window is
        // in the memory space !!!
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to creat the GLFW window.");
        }

        // Make the OpenGl context
        glfwMakeContextCurrent(glfwWindow);

        //Enable v-sync
        glfwSwapInterval(1); //swap ever single frame

        //Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

}
