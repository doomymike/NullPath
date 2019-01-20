import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pellet extends Projectile{
	
	public Pellet(int x, int y, int radius, double[] speed, boolean affectGravity, int tag) {
		super(x, y, radius, speed, affectGravity, tag);
		try {
			this.setImage(ImageIO.read(new File("resources/Cannonball.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
