package org.camoiloc;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents player's stats history for a single frame in a bowling game
 */
public class PlayerFrameHistory {
    /**
     * List of ball throws made during this frame
     */
    private List<Throw> throwList = new ArrayList<> ();

    /**
     * was there a Strike during this frame
     */
    private boolean isStrike;

    /**
     * was there a Spare during this frame
     */
    private boolean isSpare;

    /**
     * Next consecutive frame history of the same player
     * Used for calculating Strike and Spare scores
     */
    private PlayerFrameHistory nextFrame;

    public PlayerFrameHistory() {
    }

    protected PlayerFrameHistory(PlayerFrameHistory nextFrame, int... throwScores) {
        this(throwScores);
        this.nextFrame = nextFrame;
    }

    protected PlayerFrameHistory(int... throwScores) {
        for (int score : throwScores) {
            addThrow(new Throw(score));
        }
    }

    public boolean isStrike() {
        return isStrike;
    }

    public boolean isSpare() {
        return isSpare;
    }

    /**
     * Calculates score for this frame based only on the throws added
     * @return Total score
     */
    protected int getThrowsScore() {
        int score = 0;
        for (Throw aThrow : throwList) {
            score += aThrow.getScore();
        }
        return score;
    }

    /**
     * Calculates the total score for this frame
     * @return
     */
    public int getScore() {
        if (isStrike) {
            return calculateStrikeScore();
        } else if (isSpare) {
            return calculateSpareScore();
        } else {
            return getThrowsScore();
        }
    }

    /**
     * Calculates Spare score using this and the next frame's scores
     * @return Total score for this frame
     */
    private int calculateSpareScore() {
        int score = getThrowsScore();
        if (nextFrame != null && !nextFrame.getThrows().isEmpty()) {
            score += nextFrame.getThrows().get(0).getScore();
        }
        return score;
    }

    /**
     * Calculates Strike score using this and next frames' scores
     * @return Total score for this frame
     */
    private int calculateStrikeScore() {
        int score = getThrowsScore();
        if (nextFrame != null) {
            if (nextFrame.getThrows().size() == 1) {
                score += nextFrame.getThrowsScore();
                if (nextFrame.getNextFrame() != null) {
                    if (nextFrame.getNextFrame().getThrows().size() == 1) {
                        score += nextFrame.getNextFrame().getThrowsScore();
                    } else if (!nextFrame.getNextFrame().getThrows().isEmpty()) {
                        score += nextFrame.getNextFrame().getThrows().get(0).getScore();
                    }
                }
            } else if (!nextFrame.getThrows().isEmpty()) {
                score += nextFrame.getThrows().get(0).getScore();
                score += nextFrame.getThrows().get(1).getScore();
            }
        }
        return score;
    }

    /**
     * Adds a new throw to the current frame history
     * @param aThrow new throw
     */
    public void addThrow(Throw aThrow) {
        throwList.add(aThrow);
        if (throwList.size() == 1 && aThrow.getScore() == 10) {
            isStrike = true;
        } else if (getThrowsScore() == 10) {
            isSpare = true;
        }
    }

    public PlayerFrameHistory getNextFrame() {
        return nextFrame;
    }

    public void setNextFrame(PlayerFrameHistory nextFrame) {
        this.nextFrame = nextFrame;
    }

    public List<Throw> getThrows() {
        return throwList;
    }
}
