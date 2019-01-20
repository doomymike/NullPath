import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Arrow extends Projectile{

	static int width = 30;
	static int height = 15;
	private String dir;
	
	public Arrow(int x, int y, double[] speed) {
		super(x, y, height, width, speed, false);
		try {
			this.setImage(ImageIO.read(new File("C:\\Users\\Michael\\eclipse-workspace\\ughPath\\src/Arrow.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void orientVertical() {
		setHeight(width);
		setWidth(height);
	}
	
}
