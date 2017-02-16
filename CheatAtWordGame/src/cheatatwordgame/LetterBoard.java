package cheatatwordgame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Vincent Yahna
 */
public class LetterBoard implements Cloneable {
    //letters should not be changed once it is set
    private String[][] letters;
    private boolean[][] isOpen;
    
    /**
     * Instantiate a LetterBoard
     * @param letters A 4 by 4 array
     */
    LetterBoard(String[][] letters) throws IllegalArgumentException{
        if(letters.length != 4 || letters[0].length != 4){
            throw new IllegalArgumentException("parameter letters must be 4x4 array");
        }
        this.letters = letters;
        //set all to true
        this.isOpen = new boolean[4][4];
        for(int i=0; i< 4; i++){
            Arrays.fill(this.isOpen[i], true);
        }
    }
    
    /**
     * Instantiate a LetterBoard
     * @param letters a string where letters are delineated by whitespace
     * @throws IllegalArgumentException if string contains less than 16 
     * white space delimited substrings
     * Should give each 4x1 row starting with the top one
     */
    LetterBoard(String letters) throws IllegalArgumentException{
        this.letters = new String[4][4];
        String[] splitChars = letters.trim().split("\\s+");
        if(splitChars.length < 16){
            //there needs to be 16 characters to fill the board
            throw new IllegalArgumentException("Not enough characters in string. Need 16.");
        }
        //x,y grid with bottom left corner at 0,0
        for(int i=3, k=0; i >= 0; i--){
            for(int j=0; j < 4; j++){
                this.letters[j][i] = splitChars[k].trim();
                k++;
            }
        }
        //set all to true
        this.isOpen = new boolean[4][4];
        for(int i=0; i< 4; i++){
            Arrays.fill(this.isOpen[i], true);
        }
        
            
    }
    
    public LetterBoard(LetterBoard otherLetterBoard){
        //letters won't be changed, so it's fine to not make a deep copy
        this.letters = otherLetterBoard.letters;
        //need to copy the isOpen array
        this.isOpen = new boolean[4][];
        for(int i= 0; i< 4; i++){
            this.isOpen[i] = Arrays.copyOf(otherLetterBoard.isOpen[i], otherLetterBoard.isOpen[i].length);
        }
    }
    
    /**
     * public getter
     * @param x int
     * @param y int
     * @return the string at that position
     */
    public String at(int x, int y){
        return this.letters[x][y];
        
    }
    
    /**
     * Tells whether the board is open at the position or if it has already been
     * passed through
     * @param row int
     * @param column int
     * @return boolean indicating whether the position is marked as open
     */
    public boolean openAt(int x, int y){
        return this.isOpen[x][y];
    }
    
    /**
     * Marks the position on the board with the given boolean
     * @param row
     * @param column
     * @param isOpen what to mark the position
     */
    public void mark(int x, int y, boolean isOpen){
        this.isOpen[x][y] = isOpen;
    }
    
    public ArrayList<Point> getOpenSpaces(int x, int y){
        ArrayList<Point> adjacentSpaces = new ArrayList();
        //loop through all spaces around this space
        for(int i=-1; i <= 1; i++){
            for(int j=-1; j<=1; j++){
                Point test = new Point(x + i, y + j);
                if(!(test.x == x && test.y == y) &&
                        (test.x >= 0 && test.x < 4) &&
                        (test.y >=0 && test.y < 4)
                        &&this.openAt(test.x, test.y)){
                    adjacentSpaces.add(test);
                }
            }
        }
        return adjacentSpaces;
    }
    
    
    @Override
    public LetterBoard clone(){
        LetterBoard clone = new LetterBoard(this);
        return clone;
        
    }
    
    /**
     * convenience method
     * @param p
     * @return 
     */
    public LetterBoard cloneWithPointBlocked(int x, int y){
        LetterBoard clone = this.clone();
        clone.mark(x,y,false);
        return clone;
    }
    
    @Override
    public String toString(){
        String s = "";
        //print top to bottom, left to right
        for(int i=3; i >=0; i--){
            for(int j=0; j < 4; j++){
                s = s + this.letters[j][i] + ":" + this.isOpen[j][i] + " ";
                
            }
            s = s + "\n";  
        }
        return s;        
    }

}
