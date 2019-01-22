public class StartGame{

	public static void main(String [] args) {
		GameAreaFrameTE game = new GameAreaFrameTE();
		while(true) {
			game.gameLoop();
			game.roundLoop();
		}
	}

}
