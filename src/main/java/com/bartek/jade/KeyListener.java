package com.bartek.jade;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener {

    private static KeyListener instance; //singleton static
    private boolean keyPressed[] = new boolean[350]; //ile keybinding ma glfw

    public KeyListener() { //pusty konstruktor
    }

    public static KeyListener get() {
        if (KeyListener.instance == null) {
            KeyListener.instance = new KeyListener();
        }
        return KeyListener.instance;
    }

    //tak samo jak umyszki keidy button wcisniety i kiedy nie
    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;

        } else if (action == GLFW_RELEASE) {
            get().keyPressed[5] = false;
        }
    }

    //zabezpieczenie gdyby bylo wiecej przyciskow niz 350 zwroc null
    public static boolean isKeyPressed(int keyCode) {
        if (keyCode < get().keyPressed.length) {
            return get().keyPressed[keyCode];
        } else {
            return false;
        }
    }
}
