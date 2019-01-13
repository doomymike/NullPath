import java.awt.image.BufferedImage;

public class FanWind extends VelocityModifier{

	private static int speed = 20;
	private int dx;
	private int dy;
	
	public FanWind(int x, int y, int height, int width, int direction, int direction2) {
		//Direction 2 indicates up or down
		super(x, y, height, width, direction, direction2);

		if (getDirection() == 0) {
			System.out.println("IMPROPER!");
		}
		
		if (getDirectionXY() == 0) {
			if (getDirection() == 2) {
				dx = -speed;
				dy = 0;
			} else {
				dx = speed;
				dy = 0;
			}
		} else {
			if (getDirection() == 2) {
				dy = -1*speed;
				dx = 0;
			} else {
				dy = speed;
				dx = 0;
			}
		}
		
	}
	
	public int[] getSpeed() {
		return new int[] {dx, dy};
	}

	//public static BufferedImage getSprite()
	
}