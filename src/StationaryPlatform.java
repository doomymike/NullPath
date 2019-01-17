import java.awt.image.BufferedImage;
/**
 * StationaryPlatform.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for stationary platforms
 *
 */
public class StationaryPlatform extends Platform{
	
	//constructors
	StationaryPlatform(int x, int y) {
		super(x, y);
	}
	
	StationaryPlatform(int x, int y, int height, int length) {
		super(x, y,height,length);
	}
	
	public static BufferedImage getSprite(){
		return getSprite();
	}
}
