package com.example.exs1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.exs1.Fregmants.ListFragment;
import com.example.exs1.Fregmants.MapFragment;
import com.example.exs1.Interfaces.HighScoreCallBack;
import com.example.exs1.Models.HighScore;

public class HighscoreActivity extends AppCompatActivity {

    private ListFragment listFragment;
    private MapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        initFragments();
        beginTransactions();
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