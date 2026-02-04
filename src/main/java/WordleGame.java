import java.util.*;
import java.io.*;

public class WordleGame {

    private int wordLen;
    private int numGuess;
    private boolean gameStarted; // TODO: Implement game state, start, finish, fail, etc.

    private String curWord; // Only for normal wordle; figure out later

    private File pathToWordBank;

    /**
     * Initializes a new WordleGame.
     * @param wordLen The length of each guess.
     * @param numGuess The number of allowed guesses.
     */
    public WordleGame(int wordLen, int numGuess) {
        this.wordLen = wordLen;
        this.numGuess = numGuess;
        curWord = null;
    }

    /**
     * Sets the path to the word bank file.
     * @param path The path to the word bank file.
     */
    public void setWordBankPath(File path) {
        pathToWordBank = path;
    }

    /**
     * Set the current word for this Wordle Game.
     * @param word The word to set as the current word.
     */
    public void setAnswer(String word) {
        if (word.length() != wordLen)
            throw new IllegalArgumentException("Given word's length does not match the game's set word length.");

        curWord = word;
    }

    /**
     * TODO: Implement reading from word bank and random selection
     * @param rand
     */
    public void randomizeAnswer(Random rand) {
        curWord = "DEITY";
    }

    /**
     * Makes a guess in the current Wordle Game.
     * @param word The word to be guessed.
     * @return The feedback for the guess.
     */
    public String makeGuess(String word) {
        if (curWord == null)
            throw new IllegalStateException("Answer word not set. Set using setAnswer() or randomize using randomizeAnswer().");
        if (word.length() != wordLen)
            throw new IllegalArgumentException("Given word's length does not match the game's set word length.");

        System.out.println("Guess = " + word + ", Answer = " + curWord);
//        System.out.println("Feedback: \t" + getWordFeedback(word, curWord));
//        System.out.println();

        return getWordFeedback(word, curWord);
    }


    private String getWordFeedback(String guess, String answer) {
        assert answer.length() == guess.length() && guess.length() == wordLen;

        // Initialize feedback array
        char[] feedback = new char[wordLen];
        for (int i = 0; i < wordLen; i++) {
            feedback[i] = '-';
        }

        Map<Character, ArrayList<Integer>> guessMap = getLetterMap(guess);
        Map<Character, ArrayList<Integer>> answerMap = getLetterMap(answer);

        // Iterate through each distinct letter in the guess.
        for (char c : guessMap.keySet()) {
            // Do nothing if letter is incorrect.
            if (!answerMap.containsKey(c))
                continue;

            // At this point, the letter is correct to some extent.
            ArrayList<Integer> guessIndices = guessMap.get(c);
            ArrayList<Integer> answerIndices = answerMap.get(c);

            // Letter appears the same number of times in the guess and the array
            if (guessIndices.size() == answerIndices.size()) {
                for (int i : guessIndices) {
                    if (answerIndices.contains(i))
                        feedback[i] = 'G';
                    else
                        feedback[i] = 'Y';
                }
            }
            // Letter appears more times in the guess than in the array
            else if (guessIndices.size() > answerIndices.size()) {
                int numGreens = 0;
                // First pass; find all matching letters
                for (int i : guessIndices) {
                    if (answerIndices.contains(i)) {
                        feedback[i] = 'G';
                        ++numGreens;
                    }
                }

                // Number of yellows left to fill in
                int numYellowsLeft = answerIndices.size() - numGreens;
                // Second pass; fill in all yellows
                for (int i : guessIndices) {
                    if (numYellowsLeft <= 0 || feedback[i] == 'G')
                        continue;
                    feedback[i] = 'Y';
                    --numYellowsLeft;
                }
            }
            // Letter appears fewer times in the guess than in the array
            else {
                for (int i : guessIndices) {
                    if (answerIndices.contains(i))
                        feedback[i] = 'G';
                    else
                        feedback[i] = 'Y';
                }
            }
        }
        return new String(feedback);
    }

    Map<Character, ArrayList<Integer>> getLetterMap(String word) {
        Map<Character, ArrayList<Integer>> letterMap = new HashMap<>();
        for (int i = 0; i < word.length(); i++) {
            if (!letterMap.containsKey(word.charAt(i))) {
                letterMap.put(word.charAt(i), new ArrayList<>());
            }
            letterMap.get(word.charAt(i)).add(i);
        }
        return letterMap;
    }

    private void printLetterMap(Map<Character, ArrayList<Integer>> letterMap) {
//        System.out.println("Printing out letterMap:");
        for (char c : letterMap.keySet()) {
            System.out.println("" + c + ": " + letterMap.get(c).toString());
        }
    }
}
