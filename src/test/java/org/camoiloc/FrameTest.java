package org.camoiloc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FrameTest {

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testFrameScore() {
        int correctScore = 8;

        PlayerFrameHistory frame = new PlayerFrameHistory(6,2);

        assertEquals(correctScore, frame.getScore());
    }

    @Test
    public void testStrikeFrameScore() {
        int correctScore = 20;

        PlayerFrameHistory frame2 = new PlayerFrameHistory(4,6);
        PlayerFrameHistory frame1 = new PlayerFrameHistory(frame2, 10);

        assertEquals(correctScore, frame1.getScore());
    }

    @Test
    public void testDoubleStrikeFrameScore() {
        int correctScore1 = 23;
        int correctScore2 = 17;

        PlayerFrameHistory frame3 = new PlayerFrameHistory(3,4);
        PlayerFrameHistory frame2 = new PlayerFrameHistory(frame3, 10);
        PlayerFrameHistory frame1 = new PlayerFrameHistory(frame2, 10);

        assertEquals(correctScore1, frame1.getScore());
        assertEquals(correctScore2, frame2.getScore());
    }

    @Test
    public void testIsStrike() {
        PlayerFrameHistory frame1 = new PlayerFrameHistory();
        frame1.addThrow(new Throw(10));

        assertTrue(frame1.isStrike());

        PlayerFrameHistory frame2 = new PlayerFrameHistory();
        frame2.addThrow(new Throw(9));

        assertFalse(frame2.isStrike());
    }

    @Test
    public void testIsSpare() {
        PlayerFrameHistory frame1 = new PlayerFrameHistory();
        frame1.addThrow(new Throw(8));
        frame1.addThrow(new Throw(2));

        assertTrue(frame1.isSpare());

        PlayerFrameHistory frame2 = new PlayerFrameHistory();
        frame2.addThrow(new Throw(9));
        frame2.addThrow(new Throw(0));

        assertFalse(frame2.isSpare());
    }

    @Test
    public void testSpareFrameScore() {
        int correctScore = 13;

        PlayerFrameHistory frame2 = new PlayerFrameHistory(3);
        PlayerFrameHistory frame1 = new PlayerFrameHistory(frame2, 6, 4);

        assertEquals(correctScore, frame1.getScore());
    }

    @Test
    public void testThrowScore() {
        int correctScore = 8;

        PlayerFrameHistory frame = new PlayerFrameHistory();
        frame.addThrow(new Throw(3));
        frame.addThrow(new Throw(5));

        assertEquals(correctScore, frame.getThrowsScore());
    }

}
