package com.example.exs1;

import static com.example.exs1.MainActivity.KEY_NAME;
import static com.example.exs1.MainActivity.KEY_SCORE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exs1.Fregmants.ListFragment;
import com.example.exs1.Fregmants.MapFragment;
import com.example.exs1.Interfaces.CoordinateCallBack;
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
    private double lat,lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        Intent highScoreIntent = getIntent();
        player_name = highScoreIntent.getStringExtra(KEY_NAME);
        score = highScoreIntent.getIntExtra(KEY_SCORE, 0);
        handleHighScores();
        initFragments();
        beginTransactions();
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
        mapFragment = new MapFragment();
        listFragment = new ListFragment();
        listFragment.setCoordinateCallBack(coordinateCallBack);
    }
    private void beginTransactions() {
        getSupportFragmentManager().beginTransaction().add(R.id.highscore_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.highscore_FRAME_map, mapFragment).commit();
    }
    private CoordinateCallBack coordinateCallBack=new CoordinateCallBack() {
        @Override
        public void itemClicked(HighScore highScore, int position) {
            mapFragment.zoomOnRecord(highScore.getLat(),highScore.getLng());
            Log.d("foo:", "itemClicked: "+highScore.toString()+" "+DataManager.getHighScores().toString());
        }
    };
}