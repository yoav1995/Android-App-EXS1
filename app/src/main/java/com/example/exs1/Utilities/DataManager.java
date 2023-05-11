package com.example.exs1.Utilities;

import com.example.exs1.Models.HighScore;

import java.util.ArrayList;

public class DataManager {
    private ArrayList<HighScore> highScores = new ArrayList<>();

    public DataManager() {
    }

    public static ArrayList<HighScore> getHighScores(){
        ArrayList<HighScore> highscores=new ArrayList<>();


        /*
        /// hard coded!
        highscores.add(new HighScore()
                .setRankTitle("Yoav Herman").setScore(5000)
               );
        highscores.add(new HighScore()
                .setRankTitle("Yoav ").setScore(4000)
        );
        highscores.add(new HighScore()
                .setRankTitle(" Herman").setScore(3000)
        );
        */
        return highscores;
    }
}
