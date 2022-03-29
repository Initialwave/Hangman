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
        Hangman hangman;
        do {
            hangman = new Hangman();
            String s = hangmanWord.get(ThreadLocalRandom.current().nextInt(0, hangmanWord.size()));
            do {
                hangman.draw(s);
                hangman.logic(s);
            } while (hangman.isRunning(s));
            hangman.draw(s);
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
    public void draw(String secretWord) {
        //it's going to call all the draw related functions
        drawHangMan();
        drawSecretWord(secretWord);
//        drawPiecesOfSecretWord();
        drawAlreadyGuessed();
    }
    public void drawHangMan() {
        //to draw the hangman
        System.out.println("      ____");
        System.out.println("     |    |");
        System.out.printf("     |    %s%n", (incorrect.length() > 0) ? "0": "");
        System.out.printf("     |   %s%s%s%n",(incorrect.length() > 1) ? "/": " ", (incorrect.length() > 2) ? "|": " ", (incorrect.length() > 3) ? "\\": "");
        System.out.printf("     |   %s %s%n",(incorrect.length() > 4) ? "/": " ", (incorrect.length() > 5) ? "\\": "");
        System.out.println(" ____|____");
    }
    public void drawSecretWord(String secretWord) {
        //to start the secret word
        String hidden = secretWord.replaceAll("[^ "+correct.toLowerCase() + correct.toUpperCase()+"]", "_");
        System.out.println(hidden);
        System.out.println("Secret Word");
    }
//    public void drawPiecesOfSecretWord() {
//        //to put the pieces in place
//    }
    public void drawAlreadyGuessed() {
        //to show the incorrect guesses and already drawn words.
        System.out.println("Guessed characters:");
        String guessedLetters = String.join(" ", incorrect.split(""));
        System.out.println(guessedLetters);
    }

}
