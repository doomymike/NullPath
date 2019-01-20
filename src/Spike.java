import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Spike.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for spike object
 *
 */
public class Spike extends ContactDamage{

	//constructors
		
	Spike(int x, int y, int height, int width){
		super(x,y,height,width,0);
		try {
			this.setImage(ImageIO.read(new File("C:\\Users\\Michael\\eclipse-workspace\\ughPath\\src/SpikeBall.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
