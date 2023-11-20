import java.io.Serializable;
import java.io.*;

public class Logger {

    // FILEPATH: /Users/sally/dev/Java/CSA/WheelOfFortuneJava/Game.java

    /**
     * The Game class is a serializable class that stores the state of the game.
     * 
     * Needed:
     * Players in the game, the current phrase, the solved phrase,
     * the current player, the board
     * 
     * @see java.io.Serializable
     * @see java.io.ObjectOutputStream for writing objects to a file
     */
    public static class Game implements Serializable {
        private WheelOfFortune wof;
        private String filename;

        public Game() {
            wof = new WheelOfFortune();
        }

        public void save(String filename) {
            try {
                FileOutputStream fileOut = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                this.filename = filename;
                out.writeObject(this);
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static Game load(String filename) {
            Game game = null;
            try {
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                game = (Game) in.readObject();
                in.close();
                fileIn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return game;
        }

        public WheelOfFortune getWof() {
            return wof;
        }

        public void setWof(WheelOfFortune wof) {
            this.wof = wof;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }
    }
}