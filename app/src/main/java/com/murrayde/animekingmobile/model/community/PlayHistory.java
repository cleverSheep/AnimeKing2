package com.murrayde.animekingmobile.model.community;

import androidx.annotation.Keep;

@Keep
public class PlayHistory {
    private int highScore;

    public PlayHistory() {
    }

    public PlayHistory(int highScore) {
        this.highScore = highScore;
    }

    public int getHighScore() {
        return highScore;
    }
}
