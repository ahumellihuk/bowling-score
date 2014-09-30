package org.camoiloc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dmitri Samoilov on 25/09/14.
 */
public class PlayerTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAvgGameScore() {
        int correctLifetimeAvgFrameScore = 16;
        int correctLifetimeAvgScore = 167;
        int correctLifetimeBallsBowled = 51;

        Player player = new Player("Test");

        //First game
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

        PlayerGameHistory gameHistory1 = new PlayerGameHistory();
        gameHistory1.addFrame(frame1);
        gameHistory1.addFrame(frame2);
        gameHistory1.addFrame(frame3);
        gameHistory1.addFrame(frame4);
        gameHistory1.addFrame(frame5);
        gameHistory1.addFrame(frame6);
        gameHistory1.addFrame(frame7);
        gameHistory1.addFrame(frame8);
        gameHistory1.addFrame(frame9);
        gameHistory1.addFrame(frame10);

        //Second game
        PlayerFrameHistory aFrame10 = new PlayerFrameHistory(9, 0);
        PlayerFrameHistory aFrame9 = new PlayerFrameHistory(aFrame10, 3, 6);
        PlayerFrameHistory aFrame8 = new PlayerFrameHistory(aFrame9, 9, 0);
        PlayerFrameHistory aFrame7 = new PlayerFrameHistory(aFrame8, 0, 8);
        PlayerFrameHistory aFrame6 = new PlayerFrameHistory(aFrame7, 10);
        PlayerFrameHistory aFrame5 = new PlayerFrameHistory(aFrame6, 3, 2);
        PlayerFrameHistory aFrame4 = new PlayerFrameHistory(aFrame5, 6, 2);
        PlayerFrameHistory aFrame3 = new PlayerFrameHistory(aFrame4, 3, 4);
        PlayerFrameHistory aFrame2 = new PlayerFrameHistory(aFrame3, 2, 8);
        PlayerFrameHistory aFrame1 = new PlayerFrameHistory(aFrame2, 6, 3);

        PlayerGameHistory gameHistory2 = new PlayerGameHistory();
        gameHistory2.addFrame(aFrame1);
        gameHistory2.addFrame(aFrame2);
        gameHistory2.addFrame(aFrame3);
        gameHistory2.addFrame(aFrame4);
        gameHistory2.addFrame(aFrame5);
        gameHistory2.addFrame(aFrame6);
        gameHistory2.addFrame(aFrame7);
        gameHistory2.addFrame(aFrame8);
        gameHistory2.addFrame(aFrame9);
        gameHistory2.addFrame(aFrame10);

        //Third game
        PlayerFrameHistory bFrame10 = new PlayerFrameHistory(10, 0, 8);
        PlayerFrameHistory bFrame9 = new PlayerFrameHistory(bFrame10, 4, 2);
        PlayerFrameHistory bFrame8 = new PlayerFrameHistory(bFrame9, 9, 1);
        PlayerFrameHistory bFrame7 = new PlayerFrameHistory(bFrame8, 1, 5);
        PlayerFrameHistory bFrame6 = new PlayerFrameHistory(bFrame7, 1, 0);
        PlayerFrameHistory bFrame5 = new PlayerFrameHistory(bFrame6, 3, 5);
        PlayerFrameHistory bFrame4 = new PlayerFrameHistory(bFrame5, 6, 1);
        PlayerFrameHistory bFrame3 = new PlayerFrameHistory(bFrame4, 10);
        PlayerFrameHistory bFrame2 = new PlayerFrameHistory(bFrame3, 0, 10);
        PlayerFrameHistory bFrame1 = new PlayerFrameHistory(bFrame2, 8, 2);

        PlayerGameHistory gameHistory3 = new PlayerGameHistory();
        gameHistory3.addFrame(bFrame1);
        gameHistory3.addFrame(bFrame2);
        gameHistory3.addFrame(bFrame3);
        gameHistory3.addFrame(bFrame4);
        gameHistory3.addFrame(bFrame5);
        gameHistory3.addFrame(bFrame6);
        gameHistory3.addFrame(bFrame7);
        gameHistory3.addFrame(bFrame8);
        gameHistory3.addFrame(bFrame9);
        gameHistory3.addFrame(bFrame10);

        player.addGameHistory(gameHistory1);
        player.addGameHistory(gameHistory2);
        player.addGameHistory(gameHistory3);

        assertEquals(correctLifetimeAvgFrameScore, player.getLifetimeAvgFrameScore());
        assertEquals(correctLifetimeAvgScore, player.getLifetimeAvgScore());
        assertEquals(correctLifetimeBallsBowled, player.getLifetimeBallsBowled());
    }
}
