package com.example.exs1.Models;

public class HighScore {
    private String rankTitle="";
    private double lat,lng;

    public double getLat() {
        return lat;
    }

    public HighScore setLat(double lat) {
        this.lat = lat;
        return this;
    }

    public double getLng() {
        return lng;
    }

    public HighScore setLng(double lng) {
        this.lng = lng;
        return this;
    }

    private int score;

    public HighScore() {
    }

    public String getRankTitle() {
        return rankTitle;
    }

    public int getScore() {
        return score;
    }

    public HighScore setRankTitle(String rankTitle) {
        this.rankTitle = rankTitle;
        return this;
    }

    public HighScore setScore(int score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return "HighScore{" +
                "rankTitle='" + rankTitle + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", score=" + score +
                '}';
    }
}
