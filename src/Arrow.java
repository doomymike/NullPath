/**
 * [Arrow.java]
 * Arrow object for NullPath game
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */
 
//java imports
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arrow extends Projectile{

	private static int width = 30; //Width of arrow
	private static int height = 15; //Height of arrow
	private String dir; //Direction of arrow's path
	
	/**
	 * Arrow
	 * Constructor that makes an arrow object with the given parameters
	 * @param x, int representing x-coordinate
	 * @param y, int representing y-coordinate
	 * @param speed, double[] representing speed of arrow
	 * @param orientation, String representing orientation (for display)
	 * @param tag, int pairing arrow with projectile launcher
	 */
	public Arrow(int x, int y, double[] speed, String orientation, int tag) {
		super(x, y, height, width, speed, false, tag); //Projectile constructor
		try {
			this.setImage(ImageIO.read(new File("resources/Arrow.png"))); //Set image of arrow
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (orientation.equals("V")) { //Flip height and width if arrow is vertical
			setHeight(width);
			setWidth(height);
		}
	} //End of constructor
	
	/**
	 * getH
	 * Method that returns height
	 * @return int height, representing height of arrow
	 */
	public static int getH() {
		return height;
	} //End of getH
	
	/**
	 * getW
	 * Method that returns width
	 * @return int width, representing width of arrow
	 */
	public static int getW() {
		return width;
	} //End of getW
	
} //End of class
