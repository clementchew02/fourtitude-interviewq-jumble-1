package asia.fourtitude.interviewq.jumble.console;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        System.out.println("=== Welcome to the Jumble Word Game (Console Version) ===");

        // create a new GuessWord object
        GuessWord guessWord = new GuessWord();

        // pass Scanner to the startGame method (fix for compilation error)
        Scanner scanner = new Scanner(System.in);
        guessWord.startGame(scanner);

        scanner.close();
        System.out.println("=== Thanks for playing! ===");
    }
}
