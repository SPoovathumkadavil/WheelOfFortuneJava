import java.util.Scanner;

/**
 * The Player class is a serializable class that stores the state of the player.
 * 
 * Needed: Name, points
 */
public class Player implements java.io.Serializable {
    private String name;
    private int points = 0;

    public Player(String s) {
        name = s;
    } // constructs object with name filled :)

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

    public void setPoints(int score) {
        this.points = score;
    }
}
