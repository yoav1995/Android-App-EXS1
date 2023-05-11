package com.example.exs1.Fregmants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.exs1.Adapters.HighscoreAdapter;
import com.example.exs1.Interfaces.HighScoreCallBack;
import com.example.exs1.Models.HighScore;
import com.example.exs1.R;
import com.example.exs1.Utilities.DataManager;
import com.example.exs1.Utilities.SignalGen;

public class ListFragment extends Fragment {

    private RecyclerView highScore_LST_players;
    private HighScoreCallBack highScoreCallBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_highscore,container,false);
        highScore_LST_players=view.findViewById(R.id.highScore_LST_players);
        initViews(view);
        return view;
    }
    private void initViews(View view){
        HighscoreAdapter highscoreAdapter=new HighscoreAdapter(DataManager.getHighScores());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        highScore_LST_players.setAdapter(highscoreAdapter);
        highScore_LST_players.setLayoutManager(linearLayoutManager);
        highscoreAdapter.setHighScoreCallBack(new HighScoreCallBack() {
            @Override
            public void itemClicked(HighScore highScore, int position) {
                SignalGen.getInstance().toast(highScore.getRankTitle(), Toast.LENGTH_SHORT);
            }
        });

    }

    public void setHighScoreCallBack(HighScoreCallBack callBack_send) {
        this.highScoreCallBack=callBack_send;
    }
}