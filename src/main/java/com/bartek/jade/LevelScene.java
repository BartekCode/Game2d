package com.bartek.jade;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LevelScene extends Scene{

    public LevelScene(){
        log.info("Inside level scene");

//co sie stanie jak zmienimy na LevelScene bedzie znowu biale
        Window.get().r = 1;
        Window.get().g = 1;
        Window.get().b = 1;


    }
    @Override
    public void update(float dt) {}
}
