/**
 * Vincent Yahna 
 */
package cheatatwordgame;

import java.awt.Point;
import java.util.ArrayList;

/**
 * A class for representing a word on the board
 * @author Vincent Yahna
 */
public class BoardWord implements Cloneable, Comparable{
    private LetterBoard boardReference;
    private ArrayList<Point> letterPoints;
    
    public BoardWord(LetterBoard boardReference, Point firstPoint){
        this.boardReference = boardReference;
        this.letterPoints = new ArrayList();
        this.letterPoints.add(firstPoint);
    }
    public BoardWord(LetterBoard boardReference, ArrayList<Point> letterPoints){
        //this class doesn't care about whether the letters are blocked,
        //so it's fine to just make a copy to a reference of the board
        this.boardReference = boardReference;
        this.letterPoints = (ArrayList)letterPoints.clone();
    }
    
    public String getWord(){
        String word = "";
        for(int i=0; i < letterPoints.size(); i++){
            Point p = letterPoints.get(i);
            word += boardReference.at(p.x, p.y);
        }
        return word;
    }
    
    public void addPoint(Point p){
        letterPoints.add(p);
    }
    
    public int size(){
        return this.getWord().length();
    }
    
    @Override
    public BoardWord clone(){
        return new BoardWord(this.boardReference, this.letterPoints);     
    }
    
    /**
     * convenience method
     * @param p
     * @return 
     */
    public BoardWord cloneWithAddedPoint(Point p){
        BoardWord clone = this.clone();
        clone.addPoint(p);
        return clone;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof BoardWord){
            return this.size() - ((BoardWord)o).size();
        }
        else{
            return 1;
        }
    }
    
    public Point getLastPoint(){
        return this.letterPoints.get(this.letterPoints.size() -1);
    }
    
    public Point getFirstPoint(){
        return this.letterPoints.get(0);
    }
}
