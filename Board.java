import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * The Board class is a serializable class that stores the state of the board.
 * 
 * Needed: Phrases, current phrase, current letter value
 */
public class Board implements java.io.Serializable {

    public Phrase curPhrase;
    public List<String> phrases = new ArrayList<>();
    private int currentLetterValue;
    private int scale = 5;

    /**
     * Loads phrases from a file into the phrases list
     * 
     * @param fileName
     */
    public void loadPhrases(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) {
                phrases.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        }
    }

    /**
     * Chooses a random phrase from the phrases list, sets the phrase
     */
    public void assignRandomPhrase() {
        int random = (int) (Math.random() * phrases.size());
        curPhrase = new Phrase(phrases.get(random));
    }

    public int guessLetter(String str) {
        if (str.length() > 1)
            return -1;
        return curPhrase.replaceSolvedPhraseWithCount(str);
    }

    public void rerollLetterValue() {
        currentLetterValue = (int) (Math.random() * 50) * scale + 10;
    }

    public boolean isSolved(String str) {
        if (str.equals(curPhrase.getPhrase()))
            return true;
        return false;
    }

    public String getSolvedPhrase() {
        return curPhrase.getSolvedPhrase();
    }

    public String getSolutionDisplay() {
        return curPhrase.getSolutionDisplay();
    }

    public Phrase getPhrase() {
        return curPhrase;
    }

    public int getCurrentLetterValue() {
        return currentLetterValue;
    }

    public void setCurrentLetterValue(int currentLetterValue) {
        this.currentLetterValue = currentLetterValue;
    }

    public void setCurPhrase(Phrase curPhrase) {
        this.curPhrase = curPhrase;
    }

    public void setPhrases(List<String> phrases) {
        this.phrases = phrases;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }
}
