/**
 * [VelocityModifier.java]
 * Object that modifies the velocity of a character in NullPath game
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

public class VelocityModifier extends Item{

	private int dirMarker; //Stores direction
	private int xyDir; //Marks as lateral/vertical
	
	//Dictionary (dirMarker):
	//0 = Speed (direct abs influence on player speed)s
	//1 = Positive
	//2 = Negative
	
	//Dictionary (xyDir):
	//0 = Lateral
	//1 = Vertical

	/**
	 * VelocityModifier
	 * @param x, int representing the x coordinate
	 * @param y, int representing the y coordinate
	 * @param height, int representing the height of item
	 * @param width, int representing the width of item
	 * @param direction, int representing direction (+/-) of velocity modification
	 * @param direction2, int representing lateral/vertical marker
	 */
	public VelocityModifier(int x, int y, int height, int width, int direction, int direction2) {
		super (x, y, height, width);
		this.dirMarker = direction;
		this.xyDir = direction2;
	} //End of constructor

	/**
	 * getDirection
	 * Returns the direction
	 * @return int dirMarker, an int representing the direction of velocity modification
	 */
	public int getDirection() {
		return dirMarker;
	} //End of getDirection

	/**
	 * getDirectionXY
	 * Returns whether x or y direction
	 * @return int xyDir, an int which marks whether velocity modification is lateral or vertical
	 */
	public int getDirectionXY() {
		return xyDir;
	} //End of getDirectionXY

	/**
	 * getSpeed
	 * Returns the speed of velocity modification
	 * @return double[], representing speed of velocity modification
	 */
	public double[] getSpeed() {
		return null;
	} //End of getSpeed

} //End of class
