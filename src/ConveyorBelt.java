
public class ConveyorBelt extends VelocityModifier{

	private static int speed = 30;
	private int dx;
	private int dy;
	
	public ConveyorBelt(int x, int y, int height, int width, int direction, int direction2) {
		//Direction 2 indicates up or down
		super(x, y, height, width, direction, direction2);

		if (getDirection() == 0) {
			System.out.println("IMPROPER!");
		}
		
		if (getDirectionXY() == 0) {
			if (getDirection() == 2) {
				dx = -1*speed;	
			} else {
				dx = speed;
			}
		} else {
			if (getDirection() == 2) {
				dx = -1*speed;
			} else {
				dx = speed;
			}
		}
		
	}
	
	public static int getSpeed() {
		return speed;
	}

	//public static BufferedImage getSprite()
	
}
