/*[StartGame.java]
  Main class for running game operations
  Authors: Brian Li, Brian Zhang, James Liang, Michael Oren
  Date: January 21, 2019
*/

public class StartGame{

	//Main run
	public static void main(String [] args) {
		GameAreaFrameTE game = new GameAreaFrameTE();
		//Game loop execution
		while(true) {
			game.gameLoop();
			game.roundLoop();
		}
	}

}
