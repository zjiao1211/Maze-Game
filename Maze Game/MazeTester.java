
public class MazeTester {

	public static void main(String[] args) {
		boolean[][] mazedata = {{true,false,false},{true,false,true},{false,false,true}};
		for(int i = 0; i < mazedata.length;i++)
		{
			for(int j = 0; j < mazedata[0].length;j++)
			{
				System.out.print(mazedata[i][j] + " ");
			}
			System.out.println("");
		}
		MazeCoord start = new MazeCoord(0,2);
		MazeCoord end = new MazeCoord(2,0);
		Maze maze = new Maze(mazedata, start, end);
		//System.out.println(maze.getEntryLoc());
		System.out.println(maze.getPath());

	}

}
