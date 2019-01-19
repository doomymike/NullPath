//

import java.awt.image.BufferedImage;

public class MovingPlatform extends Platform{
	
	int x2,y2,x3,y3,direction; //Direction: 0 = start -> end, 1 = end -> start
	int maxTravelD = 100;

	MovingPlatform(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	MovingPlatform(int x, int y, int height, int width) {
		super(x, y,height,width);
	}

	MovingPlatform(int x, int y, int height, int width,int x2,int y2, double[] vel, int direction) {
		super(x, y,height,width, vel);
		this.x2 = x;
		this.y2 = y;
		this.x3 = x2;
		this.y3 = y2;
		this.direction = direction;
		// TODO Auto-generated constructor stub
	}
	
	public int[] getStartPos() {
		return new int[] {x2, y2};
	}
	
	public int[] getEndPos() {
		return new int[] {x3, y3};
	}
	
	public int getDirection() {
		return direction;
	}
	
	public static BufferedImage getSprite(){
		return getSprite();
	}
	
}
