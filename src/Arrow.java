import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arrow extends Projectile{

	static int width = 30;
	static int height = 15;
	private String dir;
	
	public Arrow(int x, int y, double[] speed, int tag, String dir) {
		super(x, y, height, width, speed, false, tag);
		this.dir = dir;
		try {
			this.setImage(ImageIO.read(new File("resources/Arrow.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (dir.equals("V")) {
			setHeight(width);
			setWidth(height);
		}
	}
	
}
