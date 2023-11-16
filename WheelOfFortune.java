import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

public class WheelOfFortune implements Serializable {

    private List<Player> players = new ArrayList<>(); // TODO: make it mod index based so can start from anywheer
    private int numPlayers;
    private int roundsPlayed = 0;
    private Board gameDisplay;
    private boolean isFromFile = false;
    private static double defaultTypewriteDelay = 0.02;
    private boolean solved;
    private int vowelCost = 250;

    public WheelOfFortune() {
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void waitSeconds(double seconds) {
        // Do a while loop until time elapesed is equal to the parameter seconds
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < seconds * 1000) {
        }
    }

    public static void typewrite(String str) {
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
            waitSeconds(defaultTypewriteDelay);
        }
    }

    public static void typewrite(String str, double delaySeconds) {
        for (int i = 0; i < str.length(); i++) {
            System.out.print(str.charAt(i));
            waitSeconds(delaySeconds);
        }
    }

    public int getInitialChoice(Player player, boolean previousCorrect) {
        typewrite(Colorizer.colorize(
                "It's Player " + player.getName() + "'s turn" + (previousCorrect ? " again" : "") + "!",
                Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        clearScreen();

        typewrite(Colorizer.colorize("Your current score is: " + player.getPoints(), Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();

        typewrite(Colorizer.colorize("The current phrase is: ", Colorizer.ANSI_BLUE, false));
        typewrite(gameDisplay.getSolutionDisplay(), 0.006);
        System.out.println();
        waitSeconds(0.5);

        // Choices
        typewrite(Colorizer.colorize("You have the following options: ", Colorizer.ANSI_YELLOW));
        System.out.println();
        typewrite(Colorizer.colorize("1. Guess a letter", Colorizer.ANSI_YELLOW));
        System.out.println();
        typewrite(Colorizer.colorize("2. Guess the phrase", Colorizer.ANSI_YELLOW));
        System.out.println();
        typewrite(Colorizer.colorize("3. Pass", Colorizer.ANSI_YELLOW));
        System.out.println();
        if (previousCorrect)
            typewrite(Colorizer.colorize("4. Buy a vowel", Colorizer.ANSI_YELLOW));
        System.out.println();

        int choice = 0;
        boolean badInput = false; // If the user enters a non-integer, this will be set to true
        boolean recieved = false;
        while (!recieved) {
            typewrite(Colorizer.colorize("Please enter your choice: ", Colorizer.ANSI_BLUE, false));
            try {
                if (badInput)
                    PlayGame.sc.next(); // Eats bad line so no interference
                choice = PlayGame.sc.nextInt();
                if (choice <= (previousCorrect ? 4 : 3) && choice > 0)
                    recieved = true;
            } catch (Exception e) {
                typewrite(Colorizer.colorize("Please enter a valid choice.", Colorizer.ANSI_RED, true));
                waitSeconds(0.5);
                System.out.println();
                badInput = true;
            }
        }
        return choice;
    }

    public boolean isVowel(String str) {
        return str.equals("a") || str.equals("e") || str.equals("i") || str.equals("o") || str.equals("u");
    }

    public boolean letterChoice(Player player) {
        typewrite(Colorizer.colorize("You chose to guess a letter.", Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        typewrite(Colorizer.colorize("You have $" + player.getPoints(), Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        gameDisplay.rerollLetterValue();
        typewrite(Colorizer.colorize("The current letter value is $" + gameDisplay.getCurrentLetterValue(),
                Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        typewrite(Colorizer.colorize("Please enter your guess: ", Colorizer.ANSI_BLUE, false));
        String guess = PlayGame.sc.next();
        if (isVowel(guess)) {
            typewrite(Colorizer.colorize("Please enter a consonant.", Colorizer.ANSI_RED, true));
            waitSeconds(0.5);
            return false;
        }
        int points = gameDisplay.guessLetter(guess);
        if (points == -1) {
            typewrite(Colorizer.colorize("Please enter a valid letter.", Colorizer.ANSI_RED, true));
            waitSeconds(0.5);
            return false;
        } else if (points == 0) {
            typewrite(Colorizer.colorize("Sorry, that letter is not in the phrase.", Colorizer.ANSI_RED, true));
            waitSeconds(0.5);
            return false;
        } else {
            typewrite(
                    Colorizer.colorize(
                            "Congratulations! You earned $" + gameDisplay.getCurrentLetterValue() * points + "!",
                            Colorizer.ANSI_GREEN,
                            true));
            waitSeconds(0.5);
            player.addPoints(gameDisplay.getCurrentLetterValue() * points);
            return true;
        }
    }

    public boolean phraseChoice(Player player) {
        typewrite(Colorizer.colorize("You chose to guess the phrase.", Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        typewrite(Colorizer.colorize("You have $" + player.getPoints(), Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        typewrite(Colorizer.colorize("Please enter your guess: ", Colorizer.ANSI_BLUE, false));
        PlayGame.sc.nextLine();
        String guess = PlayGame.sc.nextLine();

        if (gameDisplay.isSolved(guess)) {
            typewrite(Colorizer.colorize("Congratulations! You solved the phrase!", Colorizer.ANSI_GREEN, true));
            waitSeconds(0.5);
            player.addPoints(player.getPoints()); // DOUBLE THE POINTS IF GUESS CURRECTLY !!!! :D
            solved = true;
            return false;
        } else {
            typewrite(Colorizer.colorize("Sorry, that is not the phrase.", Colorizer.ANSI_RED, true));
            waitSeconds(0.5);
            return false;
        }
    }

    public boolean passChoice() {
        typewrite(Colorizer.colorize("You chose to pass.", Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        return false;
    }

    public boolean buyVowel(Player player) {
        typewrite(Colorizer.colorize("You chose to buy a vowel.", Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        typewrite(Colorizer.colorize("You have $" + player.getPoints(), Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        typewrite(Colorizer.colorize("The current vowel cost is $" + vowelCost, Colorizer.ANSI_BLUE, false));
        waitSeconds(0.5);
        System.out.println();
        typewrite(Colorizer.colorize("Please enter your guess: ", Colorizer.ANSI_BLUE, false));
        String guess = PlayGame.sc.next();
        if (guess.length() != 1) {
            typewrite(Colorizer.colorize("Please enter a valid letter.", Colorizer.ANSI_RED, true));
            waitSeconds(0.5);
            return false;
        }
        if (player.getPoints() < vowelCost) {
            typewrite(Colorizer.colorize("Sorry, you don't have enough money to buy a vowel.", Colorizer.ANSI_RED,
                    true));
            waitSeconds(0.5);
            return false;
        }
        if (gameDisplay.guessLetter(guess) == -1) {
            typewrite(Colorizer.colorize("Please enter a valid letter.", Colorizer.ANSI_RED, true));
            waitSeconds(0.5);
            return false;
        }
        player.addPoints(-vowelCost);
        typewrite(Colorizer.colorize("Congratulations! You bought a vowel for $" + vowelCost + "!",
                Colorizer.ANSI_GREEN, true));
        waitSeconds(0.5);
        return true;
    }

    public boolean singleCycle(Player player, boolean previousCorrect) {

        clearScreen();

        int choice = getInitialChoice(player, previousCorrect);

        if (choice == 1) // Guess a Letter
            return letterChoice(player);
        else if (choice == 2) // Guess the phrase
            return phraseChoice(player);
        else if (choice == 3) // Pass turn
            passChoice();
        else if (choice == 4) // Buy a vowel
            return buyVowel(player);

        waitSeconds(1);

        return false;
    }

    public void initialize() {
        boolean recieved = false;
        while (!recieved) {
            typewrite(Colorizer.colorize("How many players are there? ", Colorizer.ANSI_BLUE, false));
            try {
                numPlayers = PlayGame.sc.nextInt();
                recieved = true;
            } catch (Exception e) {
                typewrite(Colorizer.colorize("Please enter a valid number of players. Play nice to my domb game :(",
                        Colorizer.ANSI_RED, true));
                waitSeconds(0.5);
                System.exit(1);
            }
        }

        clearScreen();

        for (int i = 0; i < numPlayers; i++) {
            typewrite(Colorizer.colorize("Player " + (i + 1) + ", what is your name? ", Colorizer.ANSI_BLUE, false));
            String name = PlayGame.sc.next();
            players.add(new Player(name));
        }

        clearScreen();

        gameDisplay = new Board();
        gameDisplay.loadPhrases("phrases.txt");
        gameDisplay.assignRandomPhrase();
    }

    public void play() {

        clearScreen();
        typewrite(Colorizer.colorize("Welcome to Wheel of Fortune!", Colorizer.ANSI_PURPLE, false));
        waitSeconds(1);
        clearScreen();

        String saveName = "";

        if (!isFromFile) {
            initialize();

            while (saveName.equals("")) {
                typewrite(
                        Colorizer.colorize("What would you like to name your save file? ", Colorizer.ANSI_BLUE, false));
                saveName = "saves/" + PlayGame.sc.next() + ".ser";
            }
        } else {
            saveName = PlayGame.game.getFilename();
        }

        boolean playing = true;

        // System.out.println(saveName);
        PlayGame.game.save(saveName);

        while (playing) {

            for (Player player : players) {

                boolean turn = true;
                turn = singleCycle(player, false);

                while (turn) {

                    turn = singleCycle(player, true);

                    if (solved) {
                        System.out.println();
                        typewrite(Colorizer.colorize("Congratulations, " + player.getName() + "! You won!",
                                Colorizer.ANSI_GREEN, true));
                        waitSeconds(0.5);
                        clearScreen();
                        break;
                    }

                    roundsPlayed++;

                }

                PlayGame.game.save("saves/" + saveName + ".ser");

                if (solved) {
                    break;
                }

            }

            if (solved) {
                // play again?
                clearScreen();
                typewrite(Colorizer.colorize("Would you like to play again? (y/n) ", Colorizer.ANSI_BLUE, false));
                String choice = PlayGame.sc.next();
                if (choice.equalsIgnoreCase("y")) {
                    solved = false;
                    clearScreen();
                    gameDisplay.assignRandomPhrase();
                } else {
                    playing = false;
                }
            }
        }
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public void setGameDisplay(Board gameDisplay) {
        this.gameDisplay = gameDisplay;
    }

    public void setFromFile(boolean isFromFile) {
        this.isFromFile = isFromFile;
    }

    public static void setDefaultTypewriteDelay(double defaultTypewriteDelay) {
        WheelOfFortune.defaultTypewriteDelay = defaultTypewriteDelay;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public void setVowelCost(int vowelCost) {
        this.vowelCost = vowelCost;
    }
}
