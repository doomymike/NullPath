/**
 * StationaryPlatform.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for stationary platforms
 * January 22, 2019
 */

//java imports
import java.awt.image.BufferedImage;

public class StationaryPlatform extends Platform{
	
	static private int height = 40; //Height of all stationary platforms
	static private int width = 130; //Width of all stationary platforms

	/**
	 * StationaryPlatform
	 * Constructor that makes a StationaryPlatform with coordinates given
	 * @param x, int representing x coordinate of platform
	 * @param y, int representing y coordinate of platform
	 */
	StationaryPlatform(int x, int y) {
		super(x, y,height,width);
	} //End of constructor

	/**
	 * getSprite
	 * Returns the platform's image
	 * @return BufferedImage representing the sprite
	 */
	public static BufferedImage getSprite(){
		return getSprite();
	} //End of getSprite

	/**
	 * getH
	 * Returns the height of the platform
	 * @return int height, the height of the platform
	 */
	public static int getH() {
		return height;
	} //End of getH

	/**
	 * getW
	 * Returns the width of the platform
	 * @return int width, the width of the platform
	 */
	public static int getW() {
		return width;
	} //End of getW
	
} //End of class
