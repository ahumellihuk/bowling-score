package org.camoiloc;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents player's stats history for a single bowling game
 */
public class PlayerGameHistory {

    /**
     * Total player's score
     */
    private int totalScore;

    /**
     * Total number of balls bowled during this game
     */
    private int totalBallsBowled;

    /**
     * Average score in a frame based on this game stats
     */
    private int avgFrameScore;

    /**
     * History of frames in the current game
     */
    private List<PlayerFrameHistory> frames = new ArrayList<> ();

    /**
     * Latest added frame history
     */
    private PlayerFrameHistory latestFrame;

    /**
     * Whether the game was interrupted
     */
    private boolean isInterrupted;

    /**
     * Adds a frame history to the current game history
     * @param frame A frame history to be added
     */
    public void addFrame(PlayerFrameHistory frame) {
        if (latestFrame != null) {
            //Create reference to the next frame
            latestFrame.setNextFrame(frame);
        }
        latestFrame = frame;

        frames.add(frame);
        avgFrameScore = calculateAvgFrameScore();
        totalScore = calculateTotalScore();
        //Sum of all bowled balls
        totalBallsBowled += frame.getThrows().size();
    }

    /**
     * Calculates total game score
     * @return score
     */
    private int calculateTotalScore() {
        int score = 0;
        for (PlayerFrameHistory frame : frames) {
            score += frame.getScore();
        }
        return score;
    }

    /**
     * Calculates average frame score fro the current game
     * @return average frame score
     */
    private int calculateAvgFrameScore() {
        int totalScore = 0;
        for (PlayerFrameHistory frame : frames) {
            totalScore += frame.getScore();
        }
        return totalScore/frames.size();
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalBallsBowled() {
        return totalBallsBowled;
    }

    public int getAvgFrameScore() {
        return avgFrameScore;
    }

    public void setInterrupted(boolean isInterrupted) {
        this.isInterrupted = isInterrupted;
    }

    public boolean isInterrupted() {
        return isInterrupted;
    }
}
