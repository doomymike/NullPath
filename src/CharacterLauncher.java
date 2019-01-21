import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CharacterLauncher extends VelocityModifier{

	static int speed = -6;
	public CharacterLauncher(int x, int y, int height, int width, int direction, int direction2) {
		// TODO Auto-generated constructor stub
		super(x, y, height, width, direction, direction2);	
		try {
			this.setImage(ImageIO.read(new File("resources/Spring1.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void launchChar(Character affectChar) {
		double[] updatedVel = {affectChar.getVelocity()[0], affectChar.getVelocity()[1]+speed};
		affectChar.setVelocity(updatedVel);
	}
	
	public static BufferedImage getSprite() {
		return null;
	}
	
}
