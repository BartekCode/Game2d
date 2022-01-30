package com.bartek.jade;

import lombok.extern.slf4j.Slf4j;

import java.awt.event.KeyEvent;

@Slf4j
public class LevelEditorScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f; //2secundy

    public LevelEditorScene() {
        log.info("Insie level editor scene");

    }

    @Override //jak zmienimy scene na LevelEditor bedzie czarne
    public void update(float dt) {
 //log ile FPS na sekunde uzywamy float !! 1.0f to 1 sec
        log.warn("" + (1.0f/dt) + "FPS");

        if (!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }
        if (changingScene && timeToChangeScene > 0) {
            timeToChangeScene -= dt;
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;

        } else if (changingScene) {
            Window.changeScene(1);
        }
    }

}
