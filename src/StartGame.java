public class StartGame{

	public static void main(String [] args) {
		GameAreaFrame game = new GameAreaFrame();
		while(true) {
			game.gameLoop();
		}
	}

}
