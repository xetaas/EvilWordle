public class Driver {
    public static void main(String[] args) {
        WordleGame wg = new WordleGame(5, 6);
        wg.setAnswer("TRUST");
        System.out.println(wg.makeGuess("TRUST"));
        System.out.println(wg.makeGuess("CRATE"));
        System.out.println(wg.makeGuess("PINKY"));
        System.out.println(wg.makeGuess("DEITY"));
    }

}
