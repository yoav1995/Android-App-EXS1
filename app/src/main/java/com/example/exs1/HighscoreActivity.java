package com.example.exs1;

import static com.example.exs1.MainActivity.KEY_NAME;
import static com.example.exs1.MainActivity.KEY_SCORE;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exs1.Fregmants.ListFragment;
import com.example.exs1.Fregmants.MapFragment;
import com.example.exs1.Interfaces.HighScoreCallBack;
import com.example.exs1.Models.HighScore;
import com.example.exs1.Utilities.DataManager;
import com.example.exs1.Utilities.MySP;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;

    private String player_name;
    private int score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Intent highScoreIntent=getIntent();
        player_name=highScoreIntent.getStringExtra(KEY_NAME);
        score=highScoreIntent.getIntExtra(KEY_SCORE,0);
        initFragments();
        beginTransactions();
        handleHighScores();
    }

    private void handleHighScores() {
        ArrayList<HighScore> highScore = DataManager.getHighScores();
        if(player_name==null)
            player_name="";
        highScore.add(new HighScore().setRankTitle(player_name).setScore(score));
        String highScoreJson=new Gson().toJson(highScore);
        MySP.getInstance().putString("usersDetails",highScoreJson);
    }

    private void initFragments() {
        listFragment = new ListFragment();
        listFragment.setHighScoreCallBack(callBack_send);
        mapFragment = new MapFragment();
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.highscore_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.highscore_FRAME_map, mapFragment).commit();
    }
    private HighScoreCallBack callBack_send = new HighScoreCallBack() {
        @Override
        public void itemClicked(HighScore highScore, int position) {
        }
    };

}