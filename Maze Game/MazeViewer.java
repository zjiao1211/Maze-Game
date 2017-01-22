// Name: Tian Tan
// USC loginid: tiantan@usc.edu
// CS 455 PA3
// Fall 2016

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.JFrame;
import java.util.Scanner;

/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start location, 
 * and ending with the exit location. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze, with start location as the top left, and exit location as the bottom right
 * (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 * 
 */

public class MazeViewer {
   
   private static final char WALL_CHAR = '1'; // WALL_CHAR means char '1'
   private static final char FREE_CHAR = '0'; // FREE_CHAR means char '0'

   public static void main(String[] args)  {

	      String fileName = "";

	      try {

	         if (args.length < 1) {
	            System.out.println("ERROR: missing file name command line argument");
	         }
	         else {
	            fileName = args[0];
	            
	            JFrame frame = readMazeFile(fileName);

	            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	            frame.setVisible(true);
	         }

	      }
	      catch (FileNotFoundException exc) {
	         System.out.println("File not found: " + fileName);
	      }
	      catch (IOException exc) {
	         exc.printStackTrace();
	      }
	   }

   /**
    readMazeFile reads in maze from the file whose name is given and 
    returns a MazeFrame created from it.
   
   @param fileName
             the name of a file to read from (file format shown in class comments, above)
   @returns a MazeFrame containing the data from the file.
        
   @throws FileNotFoundException
              if there's no such file (subclass of IOException)
   @throws IOException
              (hook given in case you want to do more error-checking.
               that would also involve changing main to catch other exceptions)
   */
   private static MazeFrame readMazeFile(String fileName) throws IOException {
	   File inputFile = new File(fileName);
	   Scanner in = new Scanner(inputFile);	   
	   try{
		   return readMazeData(in);
	   }
	   finally{
		   in.close();
	   }
   }
   
   /**
    * readMazeData Reads in maze data from the scanner that scans the maze file
    * @param in Scanner that scans the maze file
    * @return a MazeFrame created from it.
    */
   private static MazeFrame readMazeData(Scanner in){
	   Scanner readLine;
	   int rowNum = 0, colNum = 0, counter = 0;
	   String thisLine;
	   boolean[][] mazeFile = null;
	   MazeCoord startPoint = null, endPoint = null;
	   while(in.hasNextLine()){
		   thisLine = in.nextLine();
		   if(counter == 0){
			   readLine = new Scanner(thisLine);
			   rowNum = readLine.nextInt();
			   colNum = readLine.nextInt();
			   mazeFile = new boolean[rowNum][colNum];
		   }
		   else if(counter > 0 && counter < rowNum + 1){
			   for(int i = 0; i < colNum; i++){
				   if(thisLine.charAt(i) == FREE_CHAR){
					   mazeFile[counter-1][i] = true;
				   }
				   else{
					   mazeFile[counter-1][i] = false;
				   }
			   }
		   }
		   else if(counter == rowNum + 1){
			   readLine = new Scanner(thisLine);
			   startPoint = new MazeCoord(readLine.nextInt(), readLine.nextInt());
		   }
		   else{
			   readLine = new Scanner(thisLine);
			   endPoint = new MazeCoord(readLine.nextInt(), readLine.nextInt());
		   }
		   counter++;
	   }
      return new MazeFrame(mazeFile, startPoint, endPoint);
   }
   
}
