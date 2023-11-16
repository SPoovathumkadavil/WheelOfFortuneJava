import java.util.Scanner;

public class Player {
    private String name;
    private int points = 0;

    public Player(String s) {
        name = s;
    } // constructs object with name filled :)

    public String getLetterGuess() {
        WheelOfFortune.clearScreen();
        Scanner sc = new Scanner(System.in);
        boolean recieved = false;
        String guess = "";
        while (!recieved) {
            WheelOfFortune.typewrite(Colorizer.colorize("Please enter a letter: ", Colorizer.ANSI_BLUE, false));
            guess = sc.next();
            if (guess.length() == 1) {
                recieved = true;
            } else {
                WheelOfFortune.typewrite(Colorizer.colorize("Please enter a valid letter.", Colorizer.ANSI_RED, true));
                WheelOfFortune.waitSeconds(0.5);
                WheelOfFortune.clearScreen();
            }
        }
        sc.close();
        return guess;
    }

    public int getPoints() {
        return this.points;
    }

    public void addPoints(int x) {
        this.points += x;
    }

    public void setName(String s) {
        this.name = s;
    }

    public String getName() {
        return this.name;
    }
}
