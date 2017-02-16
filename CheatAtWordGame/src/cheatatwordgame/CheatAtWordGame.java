
package cheatatwordgame;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/**
 * Vincent Yahna
 * February 14, 2017
 * This project is designed to cheat at a four by four word game
 * where the goal is to find many words by traversing through the board
 * Words can be formed by moving from letter to letter diagonally,
 * left or right, or up and down, but a letter cannot be
 * revisited. A single letter box may occasionally contain two letters.
 */
public class CheatAtWordGame {
    final static String DICTIONARY_LOCATION ="C:\\Users\\Vince\\Documents\\Projects\\words.txt";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner inputScanner = new Scanner(System.in);
        //put words in dictionary for fast matching
        Map<String, String> dictionary = new HashMap();
        //load a list of words
        loadWords(dictionary);
        
        //input mechanism for board
        System.out.println("Input the letters, separated by spaces, of the board:");
        String input = inputScanner.nextLine();//"a b  c d e f g h i j k l m n o p q r s t u v w x y z";
        LetterBoard board = new LetterBoard(input);
        System.out.println("Board created from input. Please wait...");
       
        
        //end result: array of words with coordinats of all the letters (in-order)
        ArrayList<BoardWord> words = findAllWords(board, dictionary);
        //put high scoring (longer) words first
        Collections.sort(words, Collections.reverseOrder());
        //only output first coordinate
        //only ouput twenty words at a time
        for(int i=0; i< words.size() && i < 20; i++){
            Point p = words.get(i).getFirstPoint();
            System.out.println(words.get(i).getWord() + ": " + p.x + "," + p.y);
        }
    }

    public static void loadWords(Map<String, String> dictionary) throws FileNotFoundException{
        String fileLocation = DICTIONARY_LOCATION;
        File f = new File(fileLocation);
        Scanner fReader = new Scanner(f);
        while(fReader.hasNextLine()){
            String word = fReader.nextLine().trim().toLowerCase();
            //don't want words less than two letters or words that contain
            //numbers or symbols (actually, why is this necessary? They won't match anyway.)
            if(word.length() > 2){
                boolean onlyLetters = true;
                for(int i=0; i< word.length(); i++){
                    if(!Character.isLetter(word.charAt(i))){
                        onlyLetters = false;
                    }
                }
             if(onlyLetters){
                 dictionary.put(word, word);
             }   
                
            }
         }
        
    }
    
    /**
     * Use recursion to find all words
     * @param board
     * @param dictionary
     * @return an arraylist containing all the possible words stored as BoardWords
     */
    public static ArrayList<BoardWord> findAllWords(LetterBoard board, Map<String, String> dictionary){
        
        ArrayList<BoardWord> listOfWords = new ArrayList();
        //go through all starting points
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                BoardWord word = new BoardWord(board, new Point(i,j));
                findAllWordsHelper(board.cloneWithPointBlocked(i, j), dictionary, word, listOfWords);
            }
        }
        
        return listOfWords;
    }
    
    /**
     * Recursive helper method
     * @param board all positions in wordSoFar should be blocked on the board
     * @param dictionary list of English words
     * @param wordSoFar a BoardWord that contains the path followed so far
     * @param listOfWords the complete list of English words found on the board
     */
    public static void findAllWordsHelper(LetterBoard board, Map<String, String> dictionary, BoardWord wordSoFar, ArrayList<BoardWord> listOfWords){
        //check if current word has to be added to list
        if(dictionary.containsKey(wordSoFar.getWord())){
            listOfWords.add(wordSoFar);
        }
        //get all derrivative words
        Point currentPoint = wordSoFar.getLastPoint();
        ArrayList<Point> points = board.getOpenSpaces(currentPoint.x, currentPoint.y);
        //recursive call with clones and board blocked
        for(int i=0; i < points.size(); i++){
            Point p = points.get(i);
            int x = p.x;
            int y = p.y;
            findAllWordsHelper(board.cloneWithPointBlocked(x,y), dictionary, wordSoFar.cloneWithAddedPoint(p), listOfWords);
        }
    }
}

