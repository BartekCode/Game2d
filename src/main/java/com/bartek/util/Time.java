package com.bartek.util;

public class Time {

    public static float timeStarted = System.nanoTime();  //when the game starts

    public static float getTime(){ //how much time since game starts
        return (float) ((System.nanoTime() - timeStarted) * 1E-9); //i konwertujemy do nanosekund
    }
}
