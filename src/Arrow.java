import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arrow extends Projectile{

	private static int width = 30;
	private static int height = 15;
	private String dir;
	
	public Arrow(int x, int y, double[] speed, String orientation, int tag) {
		super(x, y, height, width, speed, false, tag);
		try {
			this.setImage(ImageIO.read(new File("resources/Arrow.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (orientation.equals("V")) {
			setHeight(width);
			setWidth(height);
		}
	}
	
	public static int getH() {
		return height;
	}
	
	public static int getW() {
		return width;
	}
	
}
