package com.example.android.tictactoe.config;

import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by Dambar Bahadur Pun on 2019-10-19.
 */
interface Bot {
    DifficultyLevel DEFAULT_DIFFICULTY_LEVEL = DifficultyLevel.EASY;

    void setDifficultyLevel(DifficultyLevel difficultyLevel);

    default void setToDefaultDifficultyLevel(){
        setDifficultyLevel(DEFAULT_DIFFICULTY_LEVEL);
    }
}
