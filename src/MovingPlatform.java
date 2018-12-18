import java.awt.image.BufferedImage;

public class MovingPlatform extends Platform{
	
	int x2,y2,direction,speed;
	
	MovingPlatform(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	MovingPlatform(int x, int y, int width, int length) {
		super(x, y,width,length);
	}

	MovingPlatform(int x, int y, int width, int length,int x2,int y2, int direction, int speed) {
		super(x, y,width,length);
		this.direction=direction;
		this.speed = speed;
		// TODO Auto-generated constructor stub
	}
	
	public static BufferedImage getSprite(){
		return null;
	}
}
