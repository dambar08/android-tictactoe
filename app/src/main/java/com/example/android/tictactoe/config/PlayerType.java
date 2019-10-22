package com.example.android.tictactoe.config;

/**
 * Created by Dambar Bahadur Pun on 2019-07-03.
 */
public enum PlayerType {
    NOUGHT ("O"),
    CROSS ("X");

    private final String mark;

    PlayerType(String mark){
        this.mark = mark;
    }

    public String getMark(){
        return this.mark;
    }
}
