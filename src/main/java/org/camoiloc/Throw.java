package org.camoiloc;

/**
 * Represent a single bowling ball throw
 */
public class Throw {

    /**
     * How many pins were hit by this ball throw
     */
    private int score;

    public Throw(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
