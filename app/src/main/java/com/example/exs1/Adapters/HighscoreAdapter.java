package com.example.exs1.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exs1.Interfaces.CoordinateCallBack;
import com.example.exs1.Interfaces.HighScoreCallBack;
import com.example.exs1.Models.HighScore;
import com.example.exs1.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class HighscoreAdapter extends  RecyclerView.Adapter<HighscoreAdapter.HighScoreViewHolder>{
    private ArrayList<HighScore> highScores;

    public HighscoreAdapter(ArrayList<HighScore> highScores) {
        this.highScores = highScores;
    }

    private HighScoreCallBack highScoreCallBack;
    private CoordinateCallBack coordinateCallBack;

    public void setHighScoreCallBack(HighScoreCallBack highScoreCallBack) {
        this.highScoreCallBack = highScoreCallBack;
    }

    public void setCoordinateCallBack(CoordinateCallBack coordinateCallBack) {
        this.coordinateCallBack = coordinateCallBack;
    }

    public ArrayList<HighScore> getHighScores() {
        return highScores;
    }

    @NonNull
    @Override
    public HighScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.highscore_item,parent,false);
        HighScoreViewHolder highScoreViewHolder = new HighScoreViewHolder(view);
        return highScoreViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HighScoreViewHolder holder, int position) {
        HighScore highScore=getItem(position);
        holder.highScore_LBL_title.setText(highScore.getRankTitle());
        holder.highScore_LBL_score.setText(highScore.getScore()+"");

    }

    private HighScore getItem(int position) {
        return this.highScores.get(position);
    }

    @Override
    public int getItemCount() {

        return this.highScores == null ? 0 : this.highScores.size();
    }

    public class HighScoreViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView highScore_LBL_title;
        private MaterialTextView highScore_LBL_score;

        public HighScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            highScore_LBL_title = itemView.findViewById(R.id.highScore_LBL_title);
            highScore_LBL_score = itemView.findViewById(R.id.highScore_LBL_score);
            itemView.setOnClickListener(view -> {
                if(highScoreCallBack != null) {
                    highScoreCallBack.itemClicked(getItem(getAbsoluteAdapterPosition()), getAbsoluteAdapterPosition());
                }
            });
        }
    }
}
