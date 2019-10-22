package com.example.android.tictactoe.config;

/**
 * Created by Dambar Bahadur Pun on 2019-07-02.
 */
public final class Setting {
    private final static Player DEFAULT_PLAYER = new Player();
    private final static Mode DEFAULT_MODE = new Computer();

    public static Mode getDefaultMode() {
        return DEFAULT_MODE;
    }

    public static Player getDefaultPlayer(){
        return DEFAULT_PLAYER;
    }
}