package com.example.android_lab_1.model;


import android.annotation.SuppressLint;

public class Game {
    private  int score=0;

    public void scorePoints(){
        score+=2;
    }
    @SuppressLint("SuspiciousIndentation")
    public void takePoints(){
        if(score>0)
        score--;
    }
    public int getScore(){
        return score;
    }
}
