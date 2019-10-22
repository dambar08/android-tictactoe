package com.example.android.tictactoe.config;


/**
 * Created by Dambar Bahadur Pun on 2019-07-03.
 */
public final class TwoPlayer extends Mode {
    @Override
    public boolean equals(Object obj){
        return obj instanceof TwoPlayer;
    }

    @Override
    public String toString() {
        return "TwoPlayer";
    }
}
