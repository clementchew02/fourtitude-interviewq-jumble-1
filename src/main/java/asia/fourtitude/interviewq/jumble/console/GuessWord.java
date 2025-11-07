package asia.fourtitude.interviewq.jumble.console;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.core.JumbleEngine;

import java.util.Scanner;
import java.util.Set;

/**
 * Console-based front-end for playing a Jumble game.
 * Provides a no-arg constructor for ConsoleApp compatibility and
 * also a constructor that accepts a GameState and JumbleEngine.
 */
public class GuessWord {

    private final JumbleEngine engine;
    private final GameState gameState;

    /**
     * No-arg constructor used by ConsoleApp.
     * Creates a default JumbleEngine and a default game using a random word.
     */
    public GuessWord() {
        this.engine = new JumbleEngine();
        // create a default game with a random word (engine.createGame accepts null to choose random)
        this.gameState = engine.createGame(null);
    }

    /**
     * Construct with an explicit GameState and JumbleEngine (useful for tests).
     */
    public GuessWord(GameState gameState, JumbleEngine engine) {
        this.gameState = gameState;
        this.engine = engine;
    }

    /**
     * Start interactive console game. Accepts a Scanner so callers manage System.in lifecycle.
     */
    public void startGame(Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner must not be null");
        }

        System.out.println("=== Jumble Console Game ===");
        System.out.println("Scrambled: " + displayScramble());
        System.out.println("Find words made from the scrambled letters. Type 'exit' to quit.");
        System.out.println();

        while (true) {
            System.out.print("Your guess: ");
            String input = scanner.nextLine();
            if (input == null) {
                break;
            }
            String guess = input.trim().toLowerCase();

            if ("exit".equalsIgnoreCase(guess) || "quit".equalsIgnoreCase(guess)) {
                System.out.println("Goodbye!");
                break;
            }
            if (guess.isEmpty()) {
                System.out.println("Please type a word or 'exit'.");
                continue;
            }

            handleGuess(guess);
            if (allFound()) {
                System.out.println("🎉 Congratulations — you found all words!");
                System.out.println("Original word: " + gameState.getOriginal());
                break;
            }
        }
    }

    private void handleGuess(String guess) {
        Set<String> subWords = gameState.getSubWords();
        Set<String> guessed = gameState.getGuessedWords();

        if (guessed.contains(guess)) {
            System.out.println("You already guessed: " + guess);
            return;
        }

        if (subWords.contains(guess)) {
            gameState.updateGuessWord(guess);
            System.out.println("✅ Correct! Found " + guessed.size() + " / " + subWords.size());
        } else {
            System.out.println("❌ Not valid (or not constructible from letters). Try again.");
        }

        // show a small hint every few guesses
        if (!guessed.isEmpty() && guessed.size() % 5 == 0) {
            System.out.println("Guessed so far: " + guessed);
        }
    }

    private boolean allFound() {
        return gameState.getGuessedWords().contains(gameState.getOriginal()) ||
               gameState.getGuessedWords().size() >= gameState.getSubWords().size();
    }

    private String displayScramble() {
        // Prefer a presentable scramble display; use GameState helper if present
        try {
            return gameState.getScrambleAsDisplay();
        } catch (Throwable t) {
            // fallback to raw scramble
            String s = gameState.getScramble();
            return s == null ? "" : s;
        }
    }
}
