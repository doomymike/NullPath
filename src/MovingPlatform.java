/* [MovingPlatform.java]
 * Class containing relevant info for moving platform
 * Author: James, Michael
 * Date: 01/21/19
 */

import java.awt.image.BufferedImage;

public class MovingPlatform extends Platform{
	
	int x2,y2,x3,y3,direction; //Direction: 0 = start -> end, 1 = end -> start
	private static int maxTravelD = 100;
	
	private static int height = 40;
	private static int width = 130;
	
	/**
	 * MovingPlatform
	 * 
	 * Constructor for moving platform
	 * 
	 * @param x
	 * @param y
	 */
	
	MovingPlatform(int x, int y) {
		super(x, y,height,width);
	}

	/**
	 * MovingPlatform
	 * 
	 * Alt constructor
	 * 
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @param vel
	 * @param direction
	 */
	
	MovingPlatform(int x, int y,int x2,int y2, double[] vel, int direction) {
		super(x, y,height,width, vel);
		this.x2 = x;
		this.y2 = y;
		this.x3 = x2;
		this.y3 = y2;
		this.direction = direction;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * getStartPos
	 * 
	 * Returns starting position of moving platform
	 * 
	 * @return
	 */
	
	public int[] getStartPos() {
		return new int[] {x2, y2};
	}
	
	/**
	 * getEndPos
	 * 
	 * Returns ending position of moving platform
	 * 
	 * @return
	 */
	
	public int[] getEndPos() {
		return new int[] {x3, y3};
	}
	
	/**
	 * getDirection 
	 * 
	 * Get direction
	 * 
	 * @return
	 */
	
	public int getDirection() {
		return direction;
	}
	
	/**
	 * getSprite
	 * 
	 * Returns associated sprite
	 * 
	 * @return
	 */
	
	public static BufferedImage getSprite(){
		return getSprite();
	}
	
	/**
	 * getH
	 * 
	 * Returns height
	 * 
	 * @return
	 */
	
	public static int getH() {
		return height;
	}
	
	/**
	 * getW
	 * 
	 * Returns width
	 * 
	 * @return
	 */
	
	public static int getW() {
		return width;
	}
	
}
