import java.io.Serializable;
import java.util.Arrays;

public class Phrase implements Serializable {

    private String phrase = "";
    private String solvedPhrase = "";
    private int letterCount;
    private int wordCount;

    public Phrase(String phrase) {
        this.phrase = phrase;
        createEmptySolvedPhrase();
    }

    /**
     * Replaces all letters with "_ " and sets letter and word frequencies
     */
    public void createEmptySolvedPhrase() {
        int lc = 0;
        int wc = 0;

        for (int i = 0; i < phrase.length(); i++) {
            if (!phrase.substring(i, i + 1).equals(" ")) {
                solvedPhrase += "_ ";
                lc++;
            } else {
                solvedPhrase += " ";
                wc++;
            }
        }

        wordCount++;

        this.letterCount = lc;
        this.wordCount = wc;
    }

    /**
     * Go Through each "_" in the solved phrase and replace it with the letter,
     * incrementing the count
     * 
     * @param replace - letter to replace
     * @return The number of times the letter occurred in the phrase
     */
    public int replaceSolvedPhraseWithCount(String replace) {
        int count = 0;
        StringBuilder sb = new StringBuilder(solvedPhrase);
        int phraseIndex = 0;
        String[] sbl = solvedPhrase.split("");
        for (int i = 0; i < sbl.length; i++) {
            if (sbl[i].equals("_") && phraseIndex < phrase.length()
                    && phrase.charAt(phraseIndex) == replace.charAt(0)) {
                sb.replace(i, i + 1, replace);
                count++;
            }
            if (!sbl[i].equals(" ")) {
                phraseIndex++;
            }
            if (phraseIndex < phrase.length() && phrase.charAt(phraseIndex) == ' ') {
                phraseIndex++;
            }
        }
        solvedPhrase = sb.toString();
        return count;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getSolvedPhrase() {
        return solvedPhrase;
    }

    public void setSolvedPhrase(String solvedPhrase) {
        this.solvedPhrase = solvedPhrase;
    }

    public int getLetterCount() {
        return letterCount;
    }

    public void setLetterCount(int letterCount) {
        this.letterCount = letterCount;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Phrase other = (Phrase) obj;
        if (phrase == null) {
            if (other.phrase != null)
                return false;
        } else if (!phrase.equals(other.phrase))
            return false;
        if (solvedPhrase == null) {
            if (other.solvedPhrase != null)
                return false;
        } else if (!solvedPhrase.equals(other.solvedPhrase))
            return false;
        if (letterCount != other.letterCount)
            return false;
        if (wordCount != other.wordCount)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return phrase;
    }

    public boolean letterIsVowel(String letter) {
        return letter.equalsIgnoreCase("a") || letter.equalsIgnoreCase("e") || letter.equalsIgnoreCase("i")
                || letter.equalsIgnoreCase("o") || letter.equalsIgnoreCase("u");
    }

    public String getSolutionDisplay() {
        String colorizedString = "";
        for (int i = 0; i < solvedPhrase.length(); i++) {
            if (solvedPhrase.substring(i, i + 1).equals("_")) {
                colorizedString += Colorizer.colorize(solvedPhrase.substring(i, i + 1), Colorizer.ANSI_RED);
            } else if (letterIsVowel(solvedPhrase.substring(i, i + 1))) {
                colorizedString += Colorizer.colorize(solvedPhrase.substring(i, i + 1), Colorizer.ANSI_YELLOW);
            } else {
                colorizedString += Colorizer.colorize(solvedPhrase.substring(i, i + 1), Colorizer.ANSI_GREEN);
            }
        }
        return colorizedString;
    }

}
