package org.camoiloc;

import java.util.LinkedList;

/**
 * Represents a single Player and keeps his/her stats for previous games
 */
public class Player {

    private String name;

    private LinkedList<PlayerGameHistory> gamesHistory = new LinkedList<> ();

    private int lifetimeBallsBowled;

    private int lifetimeAvgScore;

    private int lifetimeAvgFrameScore;

    public Player(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<PlayerGameHistory> getGamesHistory() {
        return gamesHistory;
    }

    public int getLifetimeBallsBowled() {
        return lifetimeBallsBowled;
    }

    public int getLifetimeAvgScore() {
        return lifetimeAvgScore;
    }

    public int getLifetimeAvgFrameScore() {
        return lifetimeAvgFrameScore;
    }


    /**
     * Adds new game history to a list and causes player stats to recalculate
     * @param gameHistory new game history to be added
     */
    public void addGameHistory(PlayerGameHistory gameHistory) {
        gamesHistory.add(gameHistory);
        lifetimeAvgFrameScore = calculateLifetimeAvgFrameScore();
        lifetimeAvgScore = calculateLifetimeAvgScore();
        lifetimeBallsBowled = calculateLifetimeBallsBowled();
    }

    /**
     * Calculates Lifetime balls bowled number as a sum of all balls bowled in every game
     * @return Total balls bowled
     */
    private int calculateLifetimeBallsBowled() {
        int balls = 0;
        for (PlayerGameHistory gameHistory : gamesHistory) {
            balls += gameHistory.getTotalBallsBowled();
        }
        return balls;
    }

    /**
     * Calculates lifetime average game score as a sum of all game scores divided by their number
     * @return Lifetime average game score
     */
    private int calculateLifetimeAvgScore() {
        int scoresSum = 0;
        for (PlayerGameHistory gameHistory : gamesHistory) {
            scoresSum += gameHistory.getTotalScore();
        }
        return scoresSum/gamesHistory.size();
    }

    /**
     * Calculates lifetime average frame score as a sum of all average frame scores from every game, divided by games number
     * @return Lifetime average frame score
     */
    private int calculateLifetimeAvgFrameScore() {
        int avgScoresSum = 0;
        for (PlayerGameHistory gameHistory : gamesHistory) {
            avgScoresSum += gameHistory.getAvgFrameScore();
        }
        return avgScoresSum/gamesHistory.size();
    }
}
