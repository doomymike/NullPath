import java.awt.image.BufferedImage;
/**
 * StationaryPlatform.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for stationary platforms
 *
 */
public class StationaryPlatform extends Platform{
	
	private static int height = 40;
	private static int width = 130;
	
	//constructors
	
	StationaryPlatform(int x, int y) {
		super(x, y,height,width);
	}
	
	public static BufferedImage getSprite(){
		return getSprite();
	}
	
	public static int getH() {
		return height;
	}
	
	public static int getW() {
		return width;
	}
	
}
