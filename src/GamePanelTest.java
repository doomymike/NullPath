import java.io.IOException;

public class GamePanelTest {
	
	public static void main(String[] args) {
		
		Resources resources = new Resources();
		
		Character blue = null;
		Character red = null;
		Character green = null;
		Character yellow = null;

		resources.addPlayer("p1");
		resources.addPlayer("p2");
		resources.addPlayer("p3");
		resources.addPlayer("p4");
		
		try {
			blue = new Character("blue");
			red = new Character("red");
			green = new Character("green");
			yellow = new Character("yellow");
		} catch (IOException e) {
			
		}
		
		resources.getPlayers().get(0).setCharacter(blue);
		resources.getPlayers().get(1).setCharacter(red);
		resources.getPlayers().get(2).setCharacter(green);
		resources.getPlayers().get(3).setCharacter(yellow);
		
		// make a game panel (with frame) here
		
	}
	
}
