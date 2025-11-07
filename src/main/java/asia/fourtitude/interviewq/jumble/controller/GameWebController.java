package asia.fourtitude.interviewq.jumble.controller;

import asia.fourtitude.interviewq.jumble.core.GameState;
import asia.fourtitude.interviewq.jumble.core.JumbleEngine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GameWebController {

    private final JumbleEngine engine = new JumbleEngine();
    private GameState gameState;

    @GetMapping("/new")
    public String newGame(@RequestParam(defaultValue = "fusion") String word, Model model) {
        gameState = engine.createGame(word);
        model.addAttribute("scramble", gameState.getScramble());
        model.addAttribute("original", gameState.getOriginal());
        model.addAttribute("guessed", gameState.getGuessedWords());
        return "play";
    }

    @PostMapping("/guess")
    public String makeGuess(@RequestParam String guess, Model model) {
        if (gameState == null) {
            model.addAttribute("error", "Game not started!");
            return "error";
        }

        boolean correct = false;
        if (gameState.getSubWords().contains(guess.toLowerCase())) {
            gameState.updateGuessWord(guess); // fixed: no longer assigned to boolean
            correct = true;                   // mark as correct guess
        }

        model.addAttribute("scramble", gameState.getScramble());
        model.addAttribute("original", gameState.getOriginal());
        model.addAttribute("guessed", gameState.getGuessedWords());
        model.addAttribute("correct", correct);
        model.addAttribute("guess", guess);
        return "play";
    }

    @GetMapping("/state")
    @ResponseBody
    public GameState getGameState() {
        return gameState;
    }
}
