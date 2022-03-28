import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Hangman {
    public static Scanner play;
    public String incorrect;
    public String correct;
    public Hangman(){
        incorrect = "";
        correct = "";
    }
    public static void main(String[] args) {
        play = new Scanner(System.in);
        // array with elements that can be randomized for the guessed word
        List<String> hangmanWord = List.of("bean", "hat", "gift", "heroic", "spontaneous", "windshield", "Boolean");
        Hangman hangman = new Hangman();
        do {
            String s = hangmanWord.get(ThreadLocalRandom.current().nextInt(0, hangmanWord.size()));
            do {
                hangman.logic(s);
            } while (hangman.isRunning(s));
            hangman.gameOver();
        }while(hangman.playAgain());
        System.exit(0);

    }
    public Boolean contains(String hangmanWord, String pieces) {
        if(hangmanWord.toLowerCase().contains(pieces.toLowerCase())) {
            return true;
        }
        return false;
    }
    //we want it to update board state base on guessed characters. board state is wrong and right guess characters
    //we need to know the guessed char and the secret word
    //compare the chosen char to the secret word. update the correct, or incorrect guesses.
    public void updateSate(String secretWord, String pieces) {
       if (!contains(secretWord, pieces)) {
           incorrect += pieces;
       }else {
           correct += pieces;
       }
    }
    public String guess() {
        System.out.println("guess a character");
        try {
            String choice = play.nextLine();
            if (choice.isEmpty() || choice.contains(" ") ||  choice.length() > 1 || !Character.isAlphabetic(choice.charAt(0))){
                System.out.println("please make a proper guess.");
                return guess();
            }
            if (correct.contains(choice) || incorrect.contains(choice)) {
                System.out.println("You have already guessed this letter");
                return guess();
            }
            return choice;
        }catch (Exception e) {
            System.out.println("please enter a valid character");
            return guess();
        }
    }
    //if they guess a character that has multiple spots in the secretword, how do we place them?
    //game play logic. handles collecting and updating game state
    public void logic(String secretWord) {
        String guess = guess();
        updateSate(secretWord, guess);
    }

    public Boolean isRunning(String secretWord) {
        if (incorrect.length() >= 6 || wordbuilder(correct, secretWord)){
            return false;
        }
        return true;
    }
    public Boolean wordbuilder(String pt1, String pt2) {
        HashSet<String> h1 = new HashSet<>(List.of(pt1.toLowerCase().split("")));
        HashSet<String> h2 = new HashSet<>(List.of(pt2.toLowerCase().split("")));
        return h1.equals(h2);
    }
    public void gameOver() {
        if (incorrect.length() >= 6) {
            System.out.println("you have failed to guess the word.");
        }else {
            System.out.println("You have guessed the correct word.");
        }
    }
    public Boolean playAgain() {
        System.out.println("would you like to play again?");
        try {
            String yes = play.nextLine();
            if (!yes.equalsIgnoreCase("yes") && !yes.equalsIgnoreCase("no")) {
                System.out.println("please choose yes or no");
                return playAgain();
            }
            return yes.equalsIgnoreCase("yes");

        }catch (Exception e) {
            System.out.println("");
            return false;
        }
    }

}
