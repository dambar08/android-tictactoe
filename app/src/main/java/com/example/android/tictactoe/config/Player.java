package com.example.android.tictactoe.config;

import android.support.annotation.Nullable;

/**
 * Created by Dambar Bahadur Pun on 2019-10-19.
 */
public class Player {
    private PlayerType playerType;
    public static final PlayerType DEFAULT_PLAYER_TYPE = PlayerType.CROSS;

    public Player() {
        setToDefaultPlayerType();
    }

    public Player(PlayerType playerType){
        this.playerType = playerType;
    }

    public void setPlayerType(PlayerType playerType){
        this.playerType = playerType;
    }

    public void setToDefaultPlayerType(){
        setPlayerType(DEFAULT_PLAYER_TYPE);
    }

    public PlayerType getPlayerType(){
        return this.playerType;
    }

    public static PlayerType getDefaultPlayerType(){
        return DEFAULT_PLAYER_TYPE;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof  Player){
            return this.playerType == ((Player) obj).playerType;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Player, PlayerType: "+playerType;
    }
}
