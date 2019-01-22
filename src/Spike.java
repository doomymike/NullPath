/**
 * [Spike.java]
 * Class for spike object
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

//java imports
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Spike extends ContactDamage{
	
	private static int height = 30; //Height of spike
	private static int width = 30; //Width of spike

	/**
	 * Spike
	 * Constructor that makes a spike given coordinates
	 * @param x, int representing x coordinate
	 * @param y, int representing y coordinate
	 */
	Spike(int x, int y){
		super(x,y,height,width,0);
		try {
			this.setImage(ImageIO.read(new File("resources/SpikeBall.png"))); //set image
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * getH
	 * Returns the height of the spike
	 * @return int height, the height of the spike
	 */
	public static int getH() {
		return height;
	} //End of getH

	/**
	 * getW
	 * Returns the width of the spike
	 * @return int width, the height of the spike
	 */
	public static int getW() {
		return width;
	} //End of getW
	
} //End of class
