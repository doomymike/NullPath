
public class Arrow extends Projectile{

	static int width = 30;
	static int height = 15;
	private String dir;
	
	public Arrow(int x, int y, double[] speed) {
		super(x, y, height, width, speed, false);
	}

	public void orientVertical() {
		setHeight(width);
		setWidth(height);
	}
	
}
