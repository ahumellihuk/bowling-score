package org.camoiloc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents a single bowling game session
 */
public class Game {

    /**
     * Stores currently playing players
     */
    private Map<Integer, Player> players = new HashMap<>();

    /**
     * Stores player's game histories (can be reached by the same key as in 'players' map)
     */
    private Map<Integer, PlayerGameHistory> gameHistoryMap = new HashMap<>();

    /**
     * current frame number
     */
    private int frameNumber;

    /**
     * Whether a game has been interrupted
     */
    private boolean interrupted;

    private Scanner scanner;

    public Game(Scanner scanner, List<Player> players) {
        this.scanner = scanner;
        int n = 1;
        for (Player player : players) {
            this.players.put(n, player);
            gameHistoryMap.put(n, new PlayerGameHistory());
            n++;
        }
    }

    /**
     * Starts and maintains game flow for 10 frames
     * Causes the game to finish if it was interrupted by a player
     */
    public void start() {
        do {
            frameNumber++;
            for (Map.Entry<Integer, Player> entry : players.entrySet()) {
                PlayerGameHistory playerGameHistory = gameHistoryMap.get(entry.getKey());
                Player player = entry.getValue();

                printFramePlayerInfo(player, playerGameHistory);

                PlayerFrameHistory frame = playFrame(player);

                //interrupted 'event' from playFrame() method
                if (interrupted) {
                    finish(true);
                    return;
                }

                //Last frame strike causes 2 extra throws
                if (frameNumber == 10 && frame.isStrike()) {
                    playExtraThrows(player, frame);
                }

                playerGameHistory.addFrame(frame);

                System.out.println("\n"+player.getName()+" has scored "+frame.getScore()+" points in this frame.");
                System.out.println(player.getName()+" has a total score of "+playerGameHistory.getTotalScore()+" points.");
            }
        } while (frameNumber < 10);
        finish(false);
    }

    /**
     * Displays player info
     * @param player a player
     * @param playerGameHistory Player's game history
     */
    private void printFramePlayerInfo(Player player, PlayerGameHistory playerGameHistory) {
        System.out.println("\n-------------------------------------");
        System.out.println("\nFrame "+frameNumber+".");
        System.out.println("Player: "+player.getName());
        System.out.println("Current score: "+playerGameHistory.getTotalScore());
        System.out.println("Average frame score: "+playerGameHistory.getAvgFrameScore());
        System.out.println("Total balls bowled: " + playerGameHistory.getTotalBallsBowled() + "\n");
    }

    /**
     * Finishes the game flow: finds winner and displays corresponding info
     * @param interrupted Whether game was interrupted or not
     */
    private void finish(boolean interrupted) {
        Integer winnerNumber = findWinningPlayerNumber();
        Player winner = players.get(winnerNumber);
        PlayerGameHistory playerGameHistory = gameHistoryMap.get(winnerNumber);
        System.out.println("\n============================");
        System.out.println("============================\n");
        System.out.println(winner.getName() + " has won with a total score of "+playerGameHistory.getTotalScore()+" points!");

        //adding finished game to player's games history
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            Player player = entry.getValue();
            PlayerGameHistory gameHistory = gameHistoryMap.get(entry.getKey());
            gameHistory.setInterrupted(interrupted);
            player.addGameHistory(gameHistory);
        }
    }

    private Integer findWinningPlayerNumber() {
        Integer winningPlayerNumber = null;
        int highestScore = -1;
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            PlayerGameHistory gameHistory = gameHistoryMap.get(entry.getKey());
            if (gameHistory.getTotalScore() > highestScore) {
                winningPlayerNumber = entry.getKey();
                highestScore = gameHistory.getTotalScore();
            }
        }
        return winningPlayerNumber;
    }

    /**
     * Launches a sequence of throws in a frame, and controls whether player has to make 1 or 2 throws
     * @param player Player that has to make throws
     * @return New frame history
     */
    private PlayerFrameHistory playFrame(Player player) {

        PlayerFrameHistory frame = new PlayerFrameHistory();

        System.out.println("1st Throw!");
        int firstThrowScore = playFirstThrow(player, frame);
        if (interrupted) {
            return frame;
        }

        if (!frame.isStrike()) {
            System.out.println("2nd Throw!");
            playSecondThrow(player, frame, firstThrowScore);
        }

        return frame;
    }

    /**
     * Displays info and takes user input when player has to make the second throw
     * @param player Player that has to make a throw
     * @param frame Current frame history
     */
    private int playSecondThrow(Player player, PlayerFrameHistory frame, int previousThrowScore) {
        int maxPossibleScore = 10-previousThrowScore;

        int score = -1;
        do {
            System.out.print("How much did you score, " + player.getName() + "?  (Type 'exit' to interrupt the game)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                interrupted = true;
                return 0;
            }
            try {
                score = Integer.parseInt(input);
                if (score < 0 || score > maxPossibleScore) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Incorrect input! Please specify correct score (0-"+maxPossibleScore+").");
            }
        } while (score < 0 || score > maxPossibleScore);

        frame.addThrow(new Throw(score));

        if (score + previousThrowScore == 10) {
            System.out.println("You got a Spare! Well done!");
        }

        return score;
    }

    /**
     * Displays info and takes user input when player has to make the first throw
     * @param player Player that has to make a throw
     * @param frame Current frame  history
     */
    private int playFirstThrow(Player player, PlayerFrameHistory frame) {
        int score = -1;
        do {
            System.out.print("How much did you score, " + player.getName() + "? (Type 'exit' to interrupt the game)");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                interrupted = true;
                return 0;
            }
            try {
                score = Integer.parseInt(input);
                if (score < 0 || score > 10) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Incorrect input! Please specify correct score (0-10).");
            }
        } while (score < 0 || score > 10);

        frame.addThrow(new Throw(score));

        if (score == 10) {
            System.out.println("You got a Strike! Congratulations!");
        }
        return score;
    }

    /**
     * Displays info and takes user input when player has to make extra throws (10th Frame)
     * @param player Player that has to make extra throws
     * @param frame Current frame history
     */
    private void playExtraThrows(Player player, PlayerFrameHistory frame) {
        System.out.println("Extra 2 throws!");

        System.out.println("1st Throw!");
        int firstThrowScore = playFirstThrow(player, frame);

        if (interrupted) {
            return;
        }

        boolean extraStrike = firstThrowScore == 10;

        System.out.println("2nd Throw!");
        if (extraStrike) {
            playFirstThrow(player, frame);
        } else {
            playSecondThrow(player, frame, firstThrowScore);
        }
    }

}
