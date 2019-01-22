/**
 * [CharacterLauncher.java]
 * Character Launcher object in NullPath game
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

//java imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CharacterLauncher extends VelocityModifier{

	static int speed = -6; //Speed of launch
	private static int height = 20; //height of launcher
	private static int width = 60; //width of launcher
	
	/**
	 * getH
	 * Returns height of launcher
	 * @return int height, the height of the launcher
	 */
	public static int getH() {
		return height;
	} //End of getH
	
	/**
	 * getW
	 * Returns width of launcher
	 * @return int width, the width of the launcher
	 */
	public static int getW() {
		return width;
	} //End of getW
	
	/**
	 * CharacterLauncher
	 * Constructor that makes a CharacterLauncher object with given parameters
	 * @param x, int representing x coordinate
	 * @param y, int representing y coordinate
	 * @param direction, int representing +/- (sign of velocity)
	 * @param direction2, int representing lateral or vertical movement (flag)
	 */
	public CharacterLauncher(int x, int y, int direction, int direction2) {
		// TODO Auto-generated constructor stub
		super(x, y, height, width, direction, direction2);	
		try {
			for(int i=1;i<4;i++) {
				this.addSprite(ImageIO.read(new File("resources/Spring"+i+".png"))); //Set frames for launcher
			}
			this.setImage(ImageIO.read(new File("resources/Spring1.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} //End of constructor
	
	/**
	 * launchChar
	 * Launches character on it
	 * @param affectChar, Character object that gets launched
	 */
	public void launchChar(Character affectChar) {
		double[] updatedVel = {affectChar.getVelocity()[0], speed};
		affectChar.setVelocity(updatedVel); //Change velocity of character
		this.setImage(this.getSprites().get(1)); //Update active frame
	} //End of launchChar
	
	/**
	 * getSprite
	 * Returns the sprite
	 * @return BufferedImage representing sprite
	 */
	public static BufferedImage getSprite() {
		return null;
	} //End of getSprite
	
} //End of class
