package com.game.AndroidLaserPointer;

public class LeaderboardItem {

    private long id;
    private String name;
    private int score;

    public LeaderboardItem() {
        this.id = 0;
        this.name = "";
        this.score = 0;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "[ name=" + name + ", score=" + score + "]";
    }
}
