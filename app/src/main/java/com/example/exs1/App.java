package com.example.exs1;

import android.app.Application;
import android.util.Log;

import com.example.exs1.Models.HighScore;
import com.example.exs1.Utilities.DataManager;
import com.example.exs1.Utilities.MySP;
import com.example.exs1.Utilities.SignalGen;
import com.google.gson.Gson;

import java.util.ArrayList;


public class App extends Application {



        @Override

        public void onCreate() {
            super.onCreate();
            ArrayList<HighScore>  highScore = DataManager.getHighScores();
            DataManager highscores= new DataManager();
            String highscoreList=new Gson().toJson(highscores);
            Log.d("JSON:", highscoreList);
            SignalGen.init(this);
            MySP.init(this);
            MySP.getInstance().putString("EXS2", highscoreList);
            String fromSP = MySP.getInstance().getString("EXS2","");
            DataManager highscoreListFromJson = new Gson().fromJson(fromSP,DataManager.class);
            Log.d(" HighScore from JSON:", highscoreListFromJson.toString());
        }
    }



