import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Saw.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for saw object
 *
 */
public class Saw extends ContactDamage{
	
	private static int radius=35;
	
	//constructor
	Saw(int x, int y) {
		super(x, y,0,0,radius);
		// TODO Auto-generated constructor stub
		
		try {
			
			for (int i = 1;i<25;i++){
				this.addSprite(ImageIO.read(new File("resources/Saw"+i+".png")));
			}
			this.setImage(this.getSprites().get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	//Method for retrieving radius
	public static int getR() {
		return radius;
	}
	

}
