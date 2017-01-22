// Name: Tian Tan
// USC loginid: tiantan@usc.edu
// CS 455 PA3
// Fall 2016

import java.util.Arrays;
import java.util.LinkedList;

/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

public class Maze {
   
   public static final boolean FREE = false; // FREE means false
   public static final boolean WALL = true; // WALL means true
   public static final int RIGHT = 0; // RIGHT means integer 0
   public static final int UP = 1; // UP means integer 1
   public static final int LEFT = 2; // LEFT means integer 2
   public static final int DOWN = 3; // DOWN means integer 3
   public static final int DIRECTION = 4; // Only 4 directions can a block get to
   public static final int STEP = 1; // We can only take a step by change 1 direction at a time
   
   private LinkedList<MazeCoord> pathList; // to store the path from start to end with MazeCoord on the way
   private boolean[][] maze; // A boolean type array, used to store the maze
   private boolean[][] visited; // A boolean type array, used to store the MazeCoord we had been searched for
   private MazeCoord mazeStart; // A MazeCoord type object, used to store the position of the start point
   private MazeCoord mazeEnd; // A MazeCoord type object, used to store the position of the target ending point
   private MazeCoord currentLoc; // A MazeCoord type object, used to store the current location

   
   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
	   maze = new boolean[mazeData.length][mazeData[0].length];
	   for(int i = 0; i < mazeData.length; i++){
		   for(int j = 0; j < mazeData[0].length; j++){
			   if(mazeData[i][j] == true){
				   maze[i][j] = FREE;
			   }
			   else{
				   maze[i][j] = WALL;
			   }
		   }
	   }
	   mazeStart = new MazeCoord(startLoc.getRow(),startLoc.getCol());
	   mazeEnd = new MazeCoord(endLoc.getRow(),endLoc.getCol());
	   currentLoc = mazeStart;
	   visited = new boolean[mazeData.length][mazeData[0].length];
	   pathList = new LinkedList<MazeCoord>();
   }


   /**
   Returns the number of rows in the maze
   @return number of rows
   */
   public int numRows() {
      return maze.length;
   }

   
   /**
   Returns the number of columns in the maze
   @return number of columns
   */   
   public int numCols() {
      return maze[0].length;
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
      return maze[loc.getRow()][loc.getCol()];
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return mazeStart;
   }
   
   
   /**
   Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
      return mazeEnd;
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {
      return pathList;
   }


   /**
      Find a path through the maze if there is one.  Client can access the
      path found via getPath method.
      @return whether path was found.
    */
   public boolean search()  {
	   if(currentLoc.getCol() < 0 || currentLoc.getCol() >= numCols() || 
	    	currentLoc.getRow() < 0 || currentLoc.getRow() >= numRows()) // if current location is out of range, return false
		   return false;
	   if(hasWallAt(currentLoc)||hasWallAt(mazeEnd)) // if current location has wall, return false
		   return false;
       if(visited[currentLoc.getRow()][currentLoc.getCol()]) // if place has already been checked, return false
    	   return false;
       if(currentLoc.equals(mazeEnd)){ // if reach mazeEnd, then return true
    	   pathList.add(0, currentLoc); // to insert all results in the front of the list
    	   for(int i = 0; i < visited.length; i++){ // upon finishing searching, clear up the visited array for next round of search
    		   Arrays.fill(visited[i], false);
    	   }
    	   return true;  
       }
       
      visited[currentLoc.getRow()][currentLoc.getCol()] = true;
      for(int i = 0; i < DIRECTION; i++){
    	  move(i);
    	  if(search()){
    		  move(((i + STEP * 2) % DIRECTION)); // back to the original location
    		  pathList.add(0, currentLoc); // to insert all results in the front of the list
    		  return true;
    	  }
    	  else{
    		  move(((i + STEP * 2) % DIRECTION)); // back to the original location
    	  }   	  
      }
      return false;
   }
   
   /**
    * move current location to a certain direction relatively to former location
    * @param nextDirection  next direction we should look up to
    */
   public void move(int nextDirection){
	   if(nextDirection == RIGHT){
		   currentLoc = new MazeCoord(currentLoc.getRow(), currentLoc.getCol() + STEP);
	   }
	   else if(nextDirection == UP){
		   currentLoc = new MazeCoord(currentLoc.getRow() + STEP, currentLoc.getCol());
	   }
	   else if(nextDirection == LEFT){
		   currentLoc = new MazeCoord(currentLoc.getRow(), currentLoc.getCol() - STEP);
	   }
	   else{
		   currentLoc = new MazeCoord(currentLoc.getRow() - STEP, currentLoc.getCol());
	   }
   }

}
