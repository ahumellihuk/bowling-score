package org.camoiloc;

import java.util.*;

/**
 * Static class for interacting with user via menu
 */
public class App {

    public final static String INCORRECT_NUMBER = "Incorrect input! Please enter a correct choice number";
    public final static String INCORRECT_INPUT = "Incorrect input! Please try again.";

    private final static Scanner scanner = new Scanner(System.in);

    /**
     * Stores existing players during application runtime
     */
    private final static Map<Integer, Player> players = new HashMap<> ();

    public static Map<Integer, Player> getPlayers() {
        return players;
    }

    public static void main(String... args) {
        String input = "";
        do {
            printMainMenu();
            input = scanner.nextLine();

            try {
                int choice = Integer.parseInt(input);

                if (choice == 1) {
                    prepareAndStartNewGame();
                } else if (choice == 2 && !players.isEmpty()) {
                    showPlayersHistory();
                }
            } catch (NumberFormatException nfe) {

            }
        } while (!input.equalsIgnoreCase("exit"));

        System.exit(1);
    }

    /**
     * Sorts and displays all existing players stats
     */
    private static void showPlayersHistory() {
        System.out.println("Players History (sorted by name):");

        //Copy to List as Collections.sort only sorts Lists
        List<Player> players = new ArrayList<>(App.players.values());
        Collections.sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
        for (Player player : players) {
            System.out.println("\nName: "+player.getName());
            System.out.println("Lifetime Balls Bowled: "+player.getLifetimeBallsBowled());
            System.out.println("Lifetime Average Frame Score: "+player.getLifetimeAvgFrameScore());
            System.out.println("Lifetime Average Game Score: "+player.getLifetimeAvgScore());
            System.out.print("Last "+(player.getGamesHistory().size() >= 3 ? 3 : player.getGamesHistory().size())+" game(s) score(s) (last game is first): ");

            //Get Descending Iterator in order to go backwards in a list
            Iterator<PlayerGameHistory> iterator = player.getGamesHistory().descendingIterator();
            int i = 0;
            while (iterator.hasNext() && i<3) {
                PlayerGameHistory gameHistory = iterator.next();
                System.out.print(gameHistory.getTotalScore() + (gameHistory.isInterrupted() ? " (interrupted)" : "") + "; ");
                i++;
            }
            System.out.println("\n");
        }
    }

    /**
     *  Collects players information and launches a new game
     */
    private static void prepareAndStartNewGame() {
        Integer playersNumber;
        do {
            System.out.print("Please input the number of players (1-4): ");
            String input = scanner.nextLine();
            playersNumber = processPlayersNumber(input);
        } while (playersNumber == null);

        List<Player> currentPlayers = new ArrayList<>();
        for (int i = 1; i <= playersNumber; i++) {
            System.out.println("Player "+i+":");
            Player player = askToSelectPlayer(currentPlayers);
            currentPlayers.add(player);
            System.out.println("Player " + i + " added: " + player.getName() + " !");
        }

        //Passing Scanner to Game in order to reuse object
        Game game = new Game(scanner, currentPlayers);
        game.start();

        System.out.println("That was a good game!");
    }

    /**
     * Displays a menu for player selection
     * @param selectedPlayers List of players, already selected for the current game
     * @return A player to be added to the current game
     */
    private static Player askToSelectPlayer(List<Player> selectedPlayers) {
        if (players.isEmpty() || !canExistingPlayersBeSelected(selectedPlayers)) {
            return askAddNewPlayer();
        } else {
            System.out.println("Do you want to: (1) add a new player or (2) select from previously added players?");
            int choice = 0;
            do {
                System.out.print("Please enter your choice: ");
                String input = scanner.nextLine();

                try {
                    choice = Integer.parseInt(input);
                    if (choice < 1 || choice > 2) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println(INCORRECT_NUMBER);
                }

            } while (choice < 1 || choice > 2);

            switch (choice) {
                case 2:
                    return askSelectExistingPlayer(selectedPlayers);
                case 1:
                default:
                    return askAddNewPlayer();
            }
        }
    }

    /**
     * Checks whether any more existing players can be selected
     * @param selectedPlayers List of players, already selected for the current game
     * @return Whether any existing player has not been selected for the game yet
     */
    protected static boolean canExistingPlayersBeSelected(List<Player> selectedPlayers) {
        for (Player player : players.values()) {
            if (!selectedPlayers.contains(player)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays menu for selection of an existing player
     * @param selectedPlayers List of players, already selected for the current game
     * @return Existing player to be added to the current game
     */
    private static Player askSelectExistingPlayer(List<Player> selectedPlayers) {
        System.out.println("Please select one of the existing players:");
        List<Integer> selectedPlayerNumbers = new ArrayList<>();
        for (Map.Entry<Integer, Player> entry : players.entrySet()) {
            if (!selectedPlayers.contains(entry.getValue())) {
                System.out.println("("+entry.getKey()+") "+entry.getValue().getName());
            } else {
                selectedPlayerNumbers.add(entry.getKey());
                System.out.println("(selected) "+entry.getValue().getName());
            }
        }
        int choice = 0;
        do {
            System.out.print("Please enter your choice: ");
            String input = scanner.nextLine();

            try {
                choice = Integer.parseInt(input);
                if (choice <= 0 || choice > players.size() || selectedPlayerNumbers.contains(choice)) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException nfe) {
                System.err.println(INCORRECT_NUMBER);
            }
        } while (choice <= 0 || choice > players.size() || selectedPlayerNumbers.contains(choice));

        return players.get(choice);
    }

    /**
     * Displays menu for addition of a new player
     * @return New player to be added to the current game
     */
    private static Player askAddNewPlayer() {
        String name;
        do {
            System.out.print("Please enter new player name: ");
            name = scanner.nextLine();
            if (isNameExists(name)) {
                System.err.println("A player with this name already exists!");
                name = null;
            }
        } while (name == null);

        return addPlayer(name);
    }

    /**
     * Adds a new player to the list of existing players
     * @param name Name of the new player
     * @return New Player object
     */
    protected static Player addPlayer(String name) {
        Player player = new Player(name);
        players.put(players.size() + 1, player);
        return player;
    }

    /**
     * Checks whether a player with this name already exists
     * @param name Name to check for existence
     * @return true if a player with such name already exists
     */
    protected static boolean isNameExists(String name) {
        for (Player player : players.values()) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Displays main menu
     */
    protected static void printMainMenu() {
        System.out.println("Welcome to the 10-pin Bowling Game!\n");

        System.out.println("Main Menu\n");

        System.out.println("1) Start a new game");
        if (!players.isEmpty()) {
            System.out.println("2) View Players History");
        }

        System.out.println("\nTo quit type 'exit'");

        System.out.print("Enter your choice: ");
    }

    /**
     * Checks whether input string contains a correct player number (1-4)
     * @param input Input String
     * @return Correct Integer that the input String contained, or null
     */
    protected static Integer processPlayersNumber(String input) {
        Integer playersNumber = null;
        try {
            int parsedInt = Integer.parseInt(input);
            if (parsedInt>0 && parsedInt<5) {
                playersNumber = parsedInt;
                System.out.println("Great! A game for "+playersNumber+" player(s) is being prepared.");
            } else {
                System.err.println("Incorrect number of players! Please try again.");
            }
        } catch (NumberFormatException nfe) {
            System.err.println(INCORRECT_INPUT);
        }
        System.err.flush();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return playersNumber;
    }

}
