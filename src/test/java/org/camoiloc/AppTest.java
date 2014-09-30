package org.camoiloc;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AppTest {
    private static final String EOL =
            System.getProperty("line.separator");
    private static final String INCORRECT_NUMBER_INPUT = "%^@ukef";

    private ByteArrayOutputStream outStream, errStream;
    private PrintStream stdout, stderr;

    @Before
    public void setUp() {
        stdout = System.out;
        stderr = System.err;

        outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));

        errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));

    }

    @After
    public void tearDown() {
        System.setOut(stdout);
        System.setErr(stderr);
        App.getPlayers().clear();
    }

    @Test
    public void testIncorrectPlayerInput() {
        String correctText = String.format("Incorrect input! Please try again.%n", EOL);

        App.processPlayersNumber(INCORRECT_NUMBER_INPUT);

        Assert.assertEquals(correctText, errStream.toString());
    }

    @Test
    public void testIncorrectPlayerNumberInput() {
        Random random = new Random();
        int playersNumber = random.nextInt();
        while (playersNumber > 0 && playersNumber <5) {
            playersNumber = random.nextInt();
        }
        String correctText = String.format("Incorrect number of players! Please try again.%n", EOL);

        App.processPlayersNumber(Integer.toString(playersNumber));

        Assert.assertEquals(correctText, errStream.toString());
    }

    @Test
    public void testCorrectPlayerNumberInput() {
        Random random = new Random();

        int playersNumber = random.nextInt(5);
        while (playersNumber < 1) {
            playersNumber = random.nextInt(5);
        }
        String correctText = String.format("Great! A game for %d player(s) is being prepared.%n", playersNumber, EOL);

        App.processPlayersNumber(Integer.toString(playersNumber));

        Assert.assertEquals(correctText, outStream.toString());
    }

    @Test
    public void testPlayerNameExists() {
        App.addPlayer("Foo");
        App.addPlayer("Bar");
        App.addPlayer("Foo Bar");

        Assert.assertTrue(App.isNameExists("Foo"));
        Assert.assertTrue(App.isNameExists("Bar"));
        Assert.assertTrue(App.isNameExists("Foo Bar"));

        Assert.assertFalse(App.isNameExists("fOO"));
        Assert.assertFalse(App.isNameExists("foo"));
        Assert.assertFalse(App.isNameExists("bar foo"));
        Assert.assertFalse(App.isNameExists("FooBar"));
    }
    
    @Test
    public void testExistingPlayersCannotBeSelected() {
        Player foo = App.addPlayer("Foo");
        Player bar = App.addPlayer("Bar");
        Player foobar = App.addPlayer("FooBar");
        Player test = App.addPlayer("Test");

        List<Player> selectedPlayers = new ArrayList<>();

        Assert.assertTrue(App.canExistingPlayersBeSelected(selectedPlayers));

        selectedPlayers.add(foo);
        selectedPlayers.add(bar);
        selectedPlayers.add(foobar);

        Assert.assertTrue(App.canExistingPlayersBeSelected(selectedPlayers));

        selectedPlayers.add(test);

        Assert.assertFalse(App.canExistingPlayersBeSelected(selectedPlayers));
    }

}
