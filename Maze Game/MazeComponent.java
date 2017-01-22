// Name: Tian Tan
// USC loginid: tiantan@usc.edu
// CS 455 PA3
// Fall 2016

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JComponent;
import java.util.ListIterator;
import java.util.LinkedList;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   private Maze maze;
   
   private static final int START_X = 10; // where to start drawing maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;  // how much smaller on each side to make entry/exit inner box
   
   
   /**
      Constructs the component.
      @param maze   the maze to display
   */
   public MazeComponent(Maze maze) 
   {   
      this.maze = maze;
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
	   Graphics2D g2 = (Graphics2D) g;
	   
	   
	   g2.drawRect(START_X, START_Y, BOX_WIDTH * maze.numCols(), 
			   BOX_HEIGHT * maze.numRows());// draw outline of the maze
	   
	   
	   for(int i = 0; i < maze.numRows(); i++){
		   for(int j = 0; j < maze.numCols(); j++){
			   if(maze.hasWallAt(new MazeCoord(i,j))){
				   g2.setColor(Color.BLACK);
				   Rectangle wall = new Rectangle(START_X + BOX_WIDTH * j, 
						   START_Y + BOX_HEIGHT * i, 
						   BOX_WIDTH, BOX_HEIGHT);
				   g2.fill(wall);
			   }
		   }
	   }// paint wall with black solid square
	   
	   g2.setColor(Color.YELLOW);
	   Rectangle entry = new Rectangle(START_X + BOX_WIDTH * maze.getEntryLoc().getCol() + INSET, 
			   START_Y + BOX_HEIGHT * maze.getEntryLoc().getRow() + INSET, 
			   BOX_WIDTH - 2 * INSET, BOX_HEIGHT - 2 * INSET); 
	   				// times 2 is because we need to set square at the center, every side should be smaller
	   g2.fill(entry);// paint start location with yellow solid square
	   
	   g2.setColor(Color.GREEN);
	   Rectangle exit = new Rectangle(START_X + BOX_WIDTH * maze.getExitLoc().getCol() + INSET, 
			   START_Y + BOX_HEIGHT * maze.getExitLoc().getRow() + INSET, 
			   BOX_WIDTH - 2 * INSET, BOX_HEIGHT - 2 * INSET);
	   g2.fill(exit);// paint target ending location with green solid square
	      
	   LinkedList<MazeCoord> pathList = maze.getPath();
	   if(!pathList.isEmpty()){
		   ListIterator<MazeCoord> pathIter = pathList.listIterator();
		   MazeCoord currentLoc = null, nextLoc = null;
		   g2.setColor(Color.BLUE);
		   if(pathIter.hasNext()){
			   currentLoc = pathIter.next();
		   }
		   while(pathIter.hasNext()){
			   nextLoc = pathIter.next();
			   g2.drawLine(START_X + BOX_WIDTH * (currentLoc.getCol() * 2 + 1) / 2,
						   START_Y + BOX_HEIGHT * (currentLoc.getRow() * 2 + 1) / 2, 
						   START_X + BOX_WIDTH * (nextLoc.getCol() * 2 + 1) / 2, 
						   START_Y + BOX_HEIGHT * (nextLoc.getRow() * 2 + 1) / 2);
			   					// to set two endpoints of the line at the center of the square
			   currentLoc = nextLoc;
		   }
		   pathList.clear();// after painting, clear the pathList for the next search round
	   }// draw the result path with blue line, if a maze has one
   }   
}



