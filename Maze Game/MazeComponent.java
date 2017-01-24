// Name: ZHE JIAO
// USC loginid: zjiao
// CS 455 PA3
// Fall 2016

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;

import javax.swing.JComponent;

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
   private static final int INSET = 2;  
                    // how much smaller on each side to make entry/exit inner box
   
   
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
     
     We draw three parts early or late. First one is the maze we read from the external file.
     Second, entry location is shown in yellow and exit location is shown in green.
     Finally, draws the path through the maze after called the search method.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
	   Graphics2D g2 = (Graphics2D) g;
	   
	   //Draw the maze
	   for(int i = 0; i < maze.numRows(); i++) {
		   for(int j = 0; j < maze.numCols(); j++) {
			   Rectangle unit = new Rectangle(START_X + j * BOX_WIDTH, START_Y + i * BOX_HEIGHT , BOX_WIDTH, BOX_HEIGHT);
			   if(maze.hasWallAt(new MazeCoord(i, j))) {
				   g2.setColor(Color.BLACK);
				   g2.fill(unit);
			   } else {
				   g2.setColor(Color.WHITE);
				   g2.fill(unit);
			   }  
		   }
	   }
	   
	   //Draw the entry and exit locations
	   Rectangle startUnit = new Rectangle(START_X + maze.getEntryLoc().getCol() * BOX_WIDTH + INSET, 
			   START_Y + maze.getEntryLoc().getRow() * BOX_HEIGHT + INSET, 
			   BOX_WIDTH - 2 * INSET, BOX_HEIGHT - 2 * INSET);
	   g2.setColor(Color.YELLOW);
	   g2.fill(startUnit);
	   
	   Rectangle endUnit = new Rectangle(START_X + maze.getExitLoc().getCol() * BOX_WIDTH + INSET, 
			   START_Y + maze.getExitLoc().getRow() * BOX_HEIGHT + INSET, 
			   BOX_WIDTH - 2 * INSET , BOX_HEIGHT - 2 * INSET);
	   g2.setColor(Color.GREEN);
	   g2.fill(endUnit);
	   
	   //Draw the path after calling the search method
	   LinkedList<MazeCoord> path = maze.getPath();
	   for(int i = 0; i < path.size() - 1; i++) {
		   MazeCoord firstCoord = path.get(i); 
		   MazeCoord secondCoord = path.get(i + 1); 
		   Point2D.Double from = new Point2D.Double(START_X + firstCoord.getCol() * BOX_WIDTH + BOX_WIDTH / 2, 
				   START_Y + firstCoord.getRow() * BOX_HEIGHT + BOX_HEIGHT / 2); 
		   Point2D.Double to = new Point2D.Double(START_X + secondCoord.getCol() * BOX_WIDTH + BOX_WIDTH / 2, 
				   START_Y + secondCoord.getRow() * BOX_HEIGHT + BOX_HEIGHT / 2); 
		   Line2D.Double segment = new Line2D.Double(from, to);
		   g2.setColor(Color.BLUE);
		   g2.draw(segment);
	   }
   } 
}



