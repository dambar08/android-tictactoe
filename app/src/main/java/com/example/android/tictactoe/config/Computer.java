package com.example.android.tictactoe.config;

import android.support.annotation.Nullable;

/**
 * Created by Dambar Bahadur Pun on 2019-07-03.
 */
public final class Computer extends Mode implements Bot {
    private DifficultyLevel difficultyLevel;

    public Computer() {
         setToDefaultDifficultyLevel();
    }

    public Computer(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public DifficultyLevel getDifficultyLevel() {
        return this.difficultyLevel;
    }

    public static DifficultyLevel getDefaultDifficultLevel() {
        return DEFAULT_DIFFICULTY_LEVEL;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof  Computer){
            return this.difficultyLevel == ((Computer) obj).difficultyLevel;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Computer, DifficultyLevel:"+difficultyLevel;
    }

    @Override
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
