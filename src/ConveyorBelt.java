import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ConveyorBelt extends VelocityModifier{

	private static double speed = 1;
	private double dx;
	private double dy;
	
	public ConveyorBelt(int x, int y, int height, int width, int direction, int direction2) {
		//Direction 2 indicates lateral or diagonal
		//Direction indicates positive or negative
		super(x, y, height, width, direction, direction2);

		if (direction == 0) {
			System.out.println("IMPROPER!");
		}
		
		if (direction2 == 0) {
			if (direction == 2) {
				dx = -speed;
				dy = 0;
			} else {
				dx = speed;
				dy = 0;
			}
		} else {
			if (direction == 2) {
				dy = -1*speed;
				dx = 0;
			} else {
				dy = speed;
				dx = 0;
			}
		}
		
		try {
			
			for (int i = 1;i<16;i++){
				this.addSprite(ImageIO.read(new File("C:\\Users\\Michael\\eclipse-workspace\\ughPath\\src/Conveyor"+i+".png")));
			}
			this.setImage(this.getSprites().get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public double[] getSpeed() {
		return new double[]{dx, dy};
	}

	//public static BufferedImage getSprite()
	
}
