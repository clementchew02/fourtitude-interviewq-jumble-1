package asia.fourtitude.interviewq.jumble.core;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Core logic for the Jumble word game.
 * Implements word lookups, searches, and transformations.
 */
public class JumbleEngine {

    private final Set<String> dictionary;
    private final Random random = new Random();

    public JumbleEngine(Collection<String> words) {
        this.dictionary = new HashSet<>();
        for (String w : words) {
            if (w != null && !w.trim().isEmpty()) {
                dictionary.add(w.trim().toLowerCase());
            }
        }
    }

    /**
     * Scrambles (jumbles) the given word's letters randomly.
     * The scrambled word must not be identical to the original if possible.
     */
    public String scramble(String word) {
        if (word == null || word.length() <= 1) return word;
        List<Character> chars = word.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        String scrambled = word;
        int attempts = 0;
        do {
            Collections.shuffle(chars, random);
            scrambled = chars.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining());
            attempts++;
        } while (scrambled.equalsIgnoreCase(word) && attempts < 10);
        return scrambled;
    }

    /**
     * Retrieves all palindrome words (words that read the same forward and backward).
     */
    public Collection<String> retrievePalindromeWords() {
        return dictionary.stream()
                .filter(w -> w.length() > 1 && new StringBuilder(w).reverse().toString().equals(w))
                .collect(Collectors.toList());
    }

    /**
     * Picks one random word of the given length.
     */
    public String pickOneRandomWord(Integer length) {
        if (length == null || length <= 0) return null;
        List<String> filtered = dictionary.stream()
                .filter(w -> w.length() == length)
                .collect(Collectors.toList());
        if (filtered.isEmpty()) return null;
        return filtered.get(random.nextInt(filtered.size()));
    }

    /**
     * Checks if the given word exists in the dictionary.
     */
    public boolean exists(String word) {
        if (word == null) return false;
        return dictionary.contains(word.toLowerCase());
    }

    /**
     * Returns all words that start with the given prefix.
     */
    public Collection<String> wordsMatchingPrefix(String prefix) {
        if (prefix == null || prefix.isEmpty()) return Collections.emptyList();
        String lowerPrefix = prefix.toLowerCase();
        return dictionary.stream()
                .filter(w -> w.startsWith(lowerPrefix))
                .collect(Collectors.toList());
    }

    /**
     * Searches words that start with a specific startChar,
     * end with a specific endChar, and have a specific length.
     */
    public Collection<String> searchWords(Character startChar, Character endChar, Integer length) {
        return dictionary.stream()
                .filter(w -> (startChar == null || w.charAt(0) == Character.toLowerCase(startChar)))
                .filter(w -> (endChar == null || w.charAt(w.length() - 1) == Character.toLowerCase(endChar)))
                .filter(w -> (length == null || w.length() == length))
                .collect(Collectors.toList());
    }

    /**
     * Generates all possible valid subwords (from dictionary) that can be made
     * using the letters of the given word, with at least minLength characters.
     */
    public Collection<String> generateSubWords(String word, Integer minLength) {
        if (word == null || minLength == null || minLength <= 0) return Collections.emptyList();

        String lower = word.toLowerCase();
        Map<Character, Long> letterCount = lower.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return dictionary.stream()
                .filter(w -> w.length() >= minLength && canFormWord(w, letterCount))
                .collect(Collectors.toList());
    }

    /**
     * Helper method to verify if a word can be formed from given letters.
     */
    private boolean canFormWord(String candidate, Map<Character, Long> available) {
        Map<Character, Long> counts = candidate.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        for (Map.Entry<Character, Long> e : counts.entrySet()) {
            if (available.getOrDefault(e.getKey(), 0L) < e.getValue()) {
                return false;
            }
        }
        return true;
    }
}
