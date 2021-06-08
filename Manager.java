// Tara Ghazanfari
// CSE 143: Assignment #4
// TA: Khusi Chaudhari
// This program will allow delay the picking of
// a word until it absolutely has to
 
import java.util.*;
 
 
public class Manager {
    private Set<String> words; // dictionary of words
    private int guessesremaining; // keeps track of user guesses
    private String wordpattern; // keeps track of pattern of word
    private Set<Character> userInput; // keeps track of inputted characters
    private int length; // length of word attempted to guess
 
    // pre: checks if the length of the word is > 1 or if the length of
    // the max guesses is greater than 0 if it isn't throws IllegalArgumentException
    // post: moves the names from the dictionary into a set of words that all match the length
    // inputed by the user
    // post: creates the pattern for hangman
    public HangmanManager(Collection<String> dictionary, int length, int max) {
        if (length < 1 || max < 0) {
            throw new IllegalArgumentException();
        }
        this.length = length;
        // initialize the words
        words = new TreeSet<String>();
        for (String a : dictionary) {
            if (a.length() == length) {
                words.add(a);
            }
        }
        guessesremaining = max;
        userInput = new TreeSet<Character>();
        wordpattern = "";
        for (int i = 1; i <= length; i++) {
            wordpattern += '-' + " ";
        }
 
    }
 
    // post: allows for access to the current set of words being considered by the
    // hangman manager
    public Set<String> words() {
        return words;
    }
 
    // post: tells how many guesses are remaining
    public int guessesLeft() {
        return guessesremaining;
    }
 
    // post: tells currrent set of letters that have already been guessed
    public Set<Character> guesses() {
        return userInput;
    }
 
    // pre: if the set of words is empty throws IllegalStateException
    // post: returns the current pattern to be displayed for the hangman game taking into
    // account guesses that have been made
    public String pattern() {
        if (words.isEmpty()) {
            throw new IllegalStateException();
        }
        return wordpattern;
    }
 
    //pre: if number of guesses is < 1 or the set of words is empty throws IllegalStateException
    // pre: if the user attempts to guess the same letter they guessed previously throws IllegalArgumentException
    // post: records the user guess
    // post: uses the user's guess to decide what patterns to build going forward
    // post: creates a map and associates a particular
    // pattern based on user guesses
    // with a set of words and finds the pattern occuring the most and choses the word from there
    public int record(char guess) {
        if (words.isEmpty() || guessesremaining < 1) {
            throw new IllegalStateException();
        }
        if (userInput.contains(guess)) {
            throw new IllegalArgumentException();
        }
        userInput.add(guess);
 
        Map<String, Set<String>> findword = new TreeMap<String, Set<String>>();
        for (String w : words) {
            String temp = patternfound(w, guess);
            if (findword.containsKey(temp)) {
                findword.get(temp).add(w);
            } else {
                findword.put(temp, new TreeSet<String>());
                findword.get(temp).add(w);
            }
 
        }
        resizeSet(findword);
        int grabOccurance = occurance(wordpattern, guess);
        return grabOccurance;
    }
 
    // post: resizes the set via pattern from user input and chooses the string pattern
    // with the largest set
    private void resizeSet(Map<String, Set<String>> findword) {
        int maxsize = 0;
        int findmax = 0;
        String setassociate = " ";
        for (String s : findword.keySet()) {
            findmax = findword.get(s).size();
            if (findmax > maxsize) {
                setassociate = s;
                wordpattern = s;
                maxsize = findmax; // finds the correct size
                words = findword.get(s);
 
            }
        }
    }
 
    // post: uses user input to find the pattern
    private String patternfound(String w, char guess) {
        String p = " ";
        for (int i = 0; i < length; i++) {
            if (w.charAt(i) == guess) {
                p += guess + " ";
            } else if (userInput.contains(w.charAt(i))) {
                p += w.charAt(i) + " ";
            } else {
                p += '-' + " ";
            }
        }
        return p;
 
    }
 
    // post: returns the number of occurances of the guessed letter in the new pattern
    private int occurance(String wordpattern, char guess) {
        int occurance = 0;
        int tempLength = length * 2;
        for (int i = 0; i <= tempLength; i++) {
            if (wordpattern.charAt(i) == guess) {
                occurance++;
 
            }
 
        }
        if (occurance == 0) {
            guessesremaining--;
        }
        return occurance;
    }
 
}