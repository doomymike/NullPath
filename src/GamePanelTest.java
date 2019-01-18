import java.io.IOException;

public class GamePanelTest {
	
	public static void main(String[] args) {
		
		Resources resources = new Resources();
		
		Character blue = null;
		Character red = null;
		Character green = null;
		Character yellow = null;
		
		Player p1 = new Player("p1");
		Player p2 = new Player("p2");
		Player p3 = new Player("p3");
		Player p4 = new Player("p4");
		
		try {
			blue = new Character("blue");
			red = new Character("red");
			green = new Character("green");
			yellow = new Character("yellow");
		} catch (IOException e) {
			
		}
		
		p1.setCharacter(blue);
		p2.setCharacter(red);
		p3.setCharacter(green);
		p4.setCharacter(yellow);
		
		// make a game panel (with frame) here
		
	}
	
}
