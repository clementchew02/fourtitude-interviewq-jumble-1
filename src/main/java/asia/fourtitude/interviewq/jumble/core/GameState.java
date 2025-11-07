package asia.fourtitude.interviewq.jumble.core;

import java.util.*;

public class GameState {

    private String original;
    private String scramble;
    private Set<String> subWords;
    private Set<String> guessedWords = new HashSet<>();

    public GameState(String original, String scramble, Set<String> subWords) {
        this.original = original;
        this.scramble = scramble;
        this.subWords = subWords;
    }

    public String getOriginal() { return original; }
    public String getScramble() { return scramble; }
    public void setScramble(String scramble) { this.scramble = scramble; }

    public Set<String> getSubWords() { return subWords; }
    public Set<String> getGuessedWords() { return guessedWords; }

    public void updateGuessWord(String guess) {
        guessedWords.add(guess);
    }

    public String getScrambleAsDisplay() {
        return String.join(" ", scramble.split(""));
    }
}
