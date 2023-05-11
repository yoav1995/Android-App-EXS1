package com.example.exs1.Fregmants;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.exs1.Interfaces.HighScoreCallBack;
import com.example.exs1.Models.HighScore;
import com.example.exs1.R;
import com.example.exs1.Utilities.SignalGen;
import com.google.android.material.textview.MaterialTextView;


public class MapFragment extends Fragment {
    private HighScoreCallBack highScoreCallBack;
    private MaterialTextView highscore_Frame_map;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_fregmant, container, false);
        findViews(view);



        return view;
    }

    private void findViews(View view) {
        highscore_Frame_map=view.findViewById(R.id.highscore_FRAME_map);
    }
    public void setHighScoreCallBack(HighScoreCallBack callBack_send) {
        this.highScoreCallBack=callBack_send;
    }
}