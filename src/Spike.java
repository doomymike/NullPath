import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Spike.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for spike object
 *
 */
public class Spike extends ContactDamage{

	//constructors
	
	private static int height = 30;
	private static int width = 30;
	
	Spike(int x, int y){
		super(x,y,height,width,0);
		try {
			this.setImage(ImageIO.read(new File("resources/SpikeBall.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static int getH() {
		return height;
	}
	
	public static int getW() {
		return width;
	}
	
}
