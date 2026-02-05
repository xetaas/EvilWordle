import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws IOException {
        Driver d = new Driver();
        WordleGame wg = new WordleGame(5, 6);

        ArrayList<String> wordList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(d.getClass().getResourceAsStream("/words.dat")));
        for (String word = reader.readLine(); word != null; word = reader.readLine()) {
            wordList.add(word.toUpperCase());
        }

        // Input loop
        while (true) {
            Scanner read = new Scanner(System.in);
            System.out.print("Enter guess: ");
            String in = read.next();
            if (in.equals("STFU")) break;
            wg.setAnswer(in.toUpperCase());

            HashMap<String, ArrayList<String>> wordMap = new HashMap<>();
            for (String word : wordList) {
                String feedback = wg.makeGuess(word);
                if (!wordMap.containsKey(feedback)) {
                    wordMap.put(feedback, new ArrayList<>());
                }
                wordMap.get(feedback).add(word);
            }

            String finalCode = "";
            int maxSize = 0;
            for (String code : wordMap.keySet()) {
                if (wordMap.get(code).size() > maxSize) {
                    wordList = wordMap.get(code);
//                    System.out.println("picked");
                    maxSize = wordMap.get(code).size();
                    finalCode = code;
                }
            }
            System.out.println("Picked " + finalCode + ", size=" + maxSize + ", wordList = " + wordList);
        }

    }

}
