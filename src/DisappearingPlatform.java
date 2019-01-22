**
 * [DisappearingPlatform.java]
 * Base platform object to disappear over time
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 21, 2019
 */
	 
import java.awt.image.BufferedImage;

public class DisappearingPlatform extends Platform{
	
	//Base Constructor
	DisappearingPlatform(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	DisappearingPlatform(int x, int y, int width, int length) {
		super(x, y,width,length);
		// TODO Auto-generated constructor stub
	}
	
	//Method for returning sprite
	public static BufferedImage getSprite(){
		return getSprite();
	}
}
