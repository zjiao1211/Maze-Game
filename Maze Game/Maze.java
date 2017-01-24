// Name: ZHE JIAO
// USC loginid: zjiao
// CS 455 PA3
// Fall 2016

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
     
   Representation invariant: there are two newly-created arrays(visited and newMaze). The visited array 
   		keeps track of which positions we've already visited in our search for a path. And newMaze is an
   		extended array which pads the original mazeDate matrix with phantom walls all around it.
   
 */

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   public static final int outerWALLs = 2;
   public static final int jumpIndex = 2;
   private MazeCoord startLoc;
   private MazeCoord endLoc;
   private boolean[][] mazeData;
   private LinkedList<MazeCoord> path;
   /**
      Constructs a maze. And we initializes the path to a empty LinkedList;
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
	   this.mazeData = mazeData;
	   this.startLoc = startLoc;
	   this.endLoc = endLoc;
	   this.path = new LinkedList<MazeCoord>();
   }


   /**
   Returns the number of rows in the maze
   @return number of rows
   */
   public int numRows() {
      return mazeData.length; 
   }

   
   /**
   Returns the number of columns in the maze
   @return number of columns
   */   
   public int numCols() {
      return mazeData[0].length; 
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
	  if(mazeData[loc.getRow()][loc.getCol()]) {
		  return true;
	  }
      return false;  
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return new MazeCoord(startLoc.getRow(),startLoc.getCol());  
   }
   
   
   /**
   Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
	   return new MazeCoord(endLoc.getRow(),endLoc.getCol());  
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {
	  return path;  
   }

   /**
      Find a path through the maze if there is one.  Client can access the
      path found via getPath method.
      
      Representation invariant: there are two newly-created arrays(visited and newMaze). 
      		The specific instructions are at the beginning.
      		
      This part is mainly to initializes the path, visited array and newMaze array.
      
      @return whether path was found.
    */
   public boolean search()  {
	  path = new LinkedList<MazeCoord>();
      boolean[][] visited = new boolean[mazeData.length][mazeData[0].length];
      boolean[][] newMaze = new boolean[mazeData.length + outerWALLs][mazeData[0].length + outerWALLs];
      
      for(int i = 0; i < newMaze.length; i++) {
    	  newMaze[i][0] = true;
    	  newMaze[i][newMaze[0].length - 1] = true;
      }
      for(int i = 0; i < newMaze[0].length; i++) {
    	  newMaze[0][i] = true;
    	  newMaze[newMaze.length - 1][i] = true;
      }
      for(int i = 1; i < newMaze.length - 1; i++) {
    	  for(int j = 1; j < newMaze[0].length - 1; j++) {
    		  newMaze[i][j] = mazeData[i - 1][j - 1];
    	  }
      }
      path.add(startLoc);
      return helper(newMaze, visited, new MazeCoord(startLoc.getRow() + 1, startLoc.getCol() + 1) , path);

   }
   
   /**
   		A helper routine (private method) which does the actual recursion. It will search 
   		from a particular location. If there is a free path from current position to the end 
   		position, and it returns true. Otherwise, it returns false.

   		@return the maze path
   */
   private boolean helper(boolean[][] newMaze, boolean[][] visited, MazeCoord currLoc, LinkedList<MazeCoord> path) {
	   if(newMaze[currLoc.getRow()][currLoc.getCol()] || visited[currLoc.getRow() - 1][currLoc.getCol() - 1]) {
		   return false;
	   }
	   if(currLoc.equals(new MazeCoord(endLoc.getRow() + 1, endLoc.getCol() + 1))) {
		   return true;
	   }
	   visited[currLoc.getRow() - 1][currLoc.getCol() - 1] = true;
	   for(int i = -1; i <= 1; i += jumpIndex) {
		   path.add(new MazeCoord(currLoc.getRow() - 1 + i, currLoc.getCol() - 1));
		   if(helper(newMaze, visited, new MazeCoord(currLoc.getRow() + i, currLoc.getCol()), path)) {
			   return true;
		   }
		   path.removeLast();
	   }
	   for(int i = -1; i <= 1; i += jumpIndex) {
		   path.add(new MazeCoord(currLoc.getRow() - 1, currLoc.getCol() + i - 1));
		   if(helper(newMaze, visited, new MazeCoord(currLoc.getRow(), currLoc.getCol() + i), path)) {
			   return true;
		   }
		   path.removeLast();
	   }
	   return false;
   }
}
