import java.util.Scanner;
import java.io.File;

public class PlayGame {

    public static Scanner sc = new Scanner(System.in);
    public static Logger.Game game;
    public static void main(String args[]) {

        // Ask User if they want to load a game, listing the loadable files in an
        // enumerated list
        // If they do, load the game
        // If they don't, start a new game

        WheelOfFortune.clearScreen();

        WheelOfFortune.typewrite(Colorizer.colorize("Would you like to load a game? (y/n) ", Colorizer.ANSI_CYAN));
        String response = sc.next();

        if (response.equalsIgnoreCase("y")) {
            // List save files in directory
            // Ask user to select a file
            // Load file

            File folder = new File("saves/");
            File[] listOfFiles = folder.listFiles();

            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println(
                            Colorizer.colorize(i + 1 + " - " + listOfFiles[i].getName(), Colorizer.ANSI_GREEN));
                }
            }

            WheelOfFortune
                    .typewrite(Colorizer.colorize("Please select a file to load (1-" + listOfFiles.length + 1 + "): ",
                            Colorizer.ANSI_CYAN));
            int fileNumber = sc.nextInt();

            game = Logger.Game.load("saves/" + listOfFiles[fileNumber - 1].getName());

            WheelOfFortune wof = game.getWof();
            wof.setFromFile(true);
            wof.play();

        } else {
            // Start a new game
            game = new Logger.Game();
            WheelOfFortune wof = game.getWof();
            wof.play();
        }
    }
}