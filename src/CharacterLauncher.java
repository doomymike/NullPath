import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CharacterLauncher extends VelocityModifier{

	static int speed = -6;
	private static int height = 20;
	private static int width = 60;
	
	public static int getH() {
		return height;
	}
	
	public static int getW() {
		return width;
	}
	
	public CharacterLauncher(int x, int y, int direction, int direction2) {
		// TODO Auto-generated constructor stub
		super(x, y, height, width, direction, direction2);	
		try {
			for(int i=1;i<4;i++) {
				this.addSprite(ImageIO.read(new File("resources/Spring"+i+".png")));
			}
			this.setImage(ImageIO.read(new File("resources/Spring1.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void launchChar(Character affectChar) {
		double[] updatedVel = {affectChar.getVelocity()[0], speed};
		affectChar.setVelocity(updatedVel);
		this.setImage(this.getSprites().get(1));
	}
	
	public static BufferedImage getSprite() {
		return null;
	}
	
}