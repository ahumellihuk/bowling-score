package org.camoiloc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dmitri Samoilov on 25/09/14.
 */
public class GameHistoryTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testPerfectGameScore() {
        int correctScore = 300;

        PlayerFrameHistory frame10 = new PlayerFrameHistory(10,10,10);
        PlayerFrameHistory frame9 = new PlayerFrameHistory(frame10, 10);
        PlayerFrameHistory frame8 = new PlayerFrameHistory(frame9, 10);
        PlayerFrameHistory frame7 = new PlayerFrameHistory(frame8, 10);
        PlayerFrameHistory frame6 = new PlayerFrameHistory(frame7, 10);
        PlayerFrameHistory frame5 = new PlayerFrameHistory(frame6, 10);
        PlayerFrameHistory frame4 = new PlayerFrameHistory(frame5, 10);
        PlayerFrameHistory frame3 = new PlayerFrameHistory(frame4, 10);
        PlayerFrameHistory frame2 = new PlayerFrameHistory(frame3, 10);
        PlayerFrameHistory frame1 = new PlayerFrameHistory(frame2, 10);

        PlayerGameHistory gameHistory = new PlayerGameHistory();
        gameHistory.addFrame(frame1);
        gameHistory.addFrame(frame2);
        gameHistory.addFrame(frame3);
        gameHistory.addFrame(frame4);
        gameHistory.addFrame(frame5);
        gameHistory.addFrame(frame6);
        gameHistory.addFrame(frame7);
        gameHistory.addFrame(frame8);
        gameHistory.addFrame(frame9);
        gameHistory.addFrame(frame10);

        assertEquals(correctScore, gameHistory.getTotalScore());
    }

    @Test
    public void testFrameScore() {
        int correctScore = 114;
        int correctBallsBowled = 18;
        int correctAvgFrameScore = 11;

        PlayerFrameHistory frame10 = new PlayerFrameHistory(9,0);
        PlayerFrameHistory frame9 = new PlayerFrameHistory(frame10, 3, 5);
        PlayerFrameHistory frame8 = new PlayerFrameHistory(frame9, 7, 2);
        PlayerFrameHistory frame7 = new PlayerFrameHistory(frame8, 10);
        PlayerFrameHistory frame6 = new PlayerFrameHistory(frame7, 6, 3);
        PlayerFrameHistory frame5 = new PlayerFrameHistory(frame6, 8, 0);
        PlayerFrameHistory frame4 = new PlayerFrameHistory(frame5, 9, 1);
        PlayerFrameHistory frame3 = new PlayerFrameHistory(frame4, 4, 4);
        PlayerFrameHistory frame2 = new PlayerFrameHistory(frame3, 7, 1);
        PlayerFrameHistory frame1 = new PlayerFrameHistory(frame2, 10);

        PlayerGameHistory gameHistory = new PlayerGameHistory();
        gameHistory.addFrame(frame1);
        gameHistory.addFrame(frame2);
        gameHistory.addFrame(frame3);
        gameHistory.addFrame(frame4);
        gameHistory.addFrame(frame5);
        gameHistory.addFrame(frame6);
        gameHistory.addFrame(frame7);
        gameHistory.addFrame(frame8);
        gameHistory.addFrame(frame9);
        gameHistory.addFrame(frame10);

        assertEquals(correctScore, gameHistory.getTotalScore());
        assertEquals(correctBallsBowled, gameHistory.getTotalBallsBowled());
        assertEquals(correctAvgFrameScore, gameHistory.getAvgFrameScore());
    }

}
