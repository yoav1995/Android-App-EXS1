package com.example.exs1;

import android.app.Application;

import com.example.exs1.Utilities.MySP;
import com.example.exs1.Utilities.SignalGen;


public class App extends Application {



        @Override
        public void onCreate() {
            super.onCreate();
            String json = "{\"highScores\":[{\"rankTitle\":Yoav Herman,\"score\":5000},{\"rankTitle\":Yoav ,\"score\":4000},{\"rankTitle\":Herman,\"score\":3000}]}";
            SignalGen.init(this);
            MySP.init(this);
            MySP.getInstance().putString("EXS2", json);
            String fromSP = MySP.getInstance().getString("EXS2","");

            //don't work properly
            //DataManager highscoreListFromJson = new Gson().fromJson(fromSP,DataManager.class);

        }
    }



