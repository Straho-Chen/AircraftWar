package com.hitsz.aircraftwar.dao;

import java.io.Serializable;

/**
 * @author Strah
 */
public class Player implements Serializable, Comparable<Player> {

    private int rank;
    private String name;
    private int score;
    private String time;
    private String difficulty;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String toString() {
        return difficulty+"第"+rank+"名："+name+','+score+','+time;
    }

    @Override
    public int compareTo(Player o) {
        return o.getScore() - this.getScore();
    }
}
