package com.bartek.jade;

import com.bartek.util.Time;
import lombok.extern.slf4j.Slf4j;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.system.MemoryUtil.NULL;

@Slf4j
public class Window {

    private int width;
    private int height;
    private String title;
    private long glfwWindow; //memory address where the window is
    private boolean fadeToBlack = false;

    public float r, g, b, a;

    private static Window window;

    private static Scene currentScene = new LevelScene();//obecna scena

    public Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Game";
        r = 1;
        b = 1;
        g = 1;
        a = 1;
    }

    //do jakiej Scene chcemy zmienic uzywamy switcha
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();
                //currentSceme.init();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknow scene '" + newScene + "'";
        }

    }

    public static Window get() { //tworzone tylko raz podczas uruchamiania apki
        if (Window.window == null) {
            Window.window = new Window();
        }
        return window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion());

        init();
        loop();

        //Free the memory when the loop exited
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        //Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

        Window.changeScene(0);
    }

    private void loop() { //petla naszej gry !!!
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            //Poll events
            glfwPollEvents();

            // Set the clear color
            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT); //flush color to entire screen


            if (dt >= 0) {
                currentScene.update(dt);
            }


//            //we should fade to black when using space key
//            if (fadeToBlack) {
//                r = Math.max(r - 0.01f, 0);
//                g = Math.max(g - 0.01f, 0);
//                b = Math.max(b - 0.01f, 0);
//            }
//            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)) {
//                fadeToBlack = true;
//            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime(); //koniec loopa jaki jest czas
            dt = endTime - beginTime; //delta czyli roznica miedzy czasem koncowym a początkiem
            beginTime = endTime; // i od nowa loop
        }

    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

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
        log.info("Window is running with settings: width " + window.width + ", and height " + window.height);
        // in the memory space !!!
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to creat the GLFW window.");
        }

        //ustawwienie pozycji myszki, przcisków oraz listener na eventy
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

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
