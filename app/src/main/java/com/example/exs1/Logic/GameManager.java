package com.example.exs1.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

//import com.example.a23b_11345a_l1.Models.Question;

import com.example.exs1.MainActivity;

import java.util.ArrayList;

public class GameManager {
    private final int regScore = 10;
    private int score;
    private int life;





    public GameManager(int life) {
        this.life = life;
        this.score = 0;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void crash(Context context, Vibrator v)
    {
        //Toast.makeText(context,":-( no! the rocks hit you!",Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(150);
        }

        /// updating score when crashing
     if(getScore()>500)
     {
         setScore(getScore()-400);
     } else if (getScore()<500&& getScore()>150) {
         setScore(getScore()-150);
     }
     else {
         setScore(getScore()-50);
     }
    }


}