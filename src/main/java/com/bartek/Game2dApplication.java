package com.bartek;

import com.bartek.jade.Window;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Game2dApplication {

    public static void main(String[] args) {
        SpringApplication.run(Game2dApplication.class, args);

        Window window = Window.get();
        window.run();
    }

}
