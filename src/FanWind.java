import java.awt.image.BufferedImage;

public class FanWind extends VelocityModifier{

	private static double speed = 2.5;
	private double dx;
	private double dy;
	
	public FanWind(int x, int y, int height, int width, int direction, int direction2) {
		//Direction 2 indicates lateral or not
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
		
	}
	
	public double[] getSpeed() {
		return new double[] {dx, dy};
	}

	//public static BufferedImage getSprite()
	
}