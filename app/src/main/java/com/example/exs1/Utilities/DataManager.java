package com.example.exs1.Utilities;

import com.example.exs1.Models.HighScore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class DataManager {
    private ArrayList<HighScore> highScores = new ArrayList<>();

    public DataManager() {

    }

    public static ArrayList<HighScore> getHighScores(){
        ArrayList<HighScore> highscores;
        String fromSP = MySP.getInstance().getString("usersDetails","");
        highscores= new Gson().fromJson(fromSP,new TypeToken<ArrayList<HighScore>>(){}.getType());
        return highscores == null ? new ArrayList<HighScore>() : highscores;
    }
}
