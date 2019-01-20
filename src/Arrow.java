
public class Arrow extends Projectile{

	static int width = 30;
	static int height = 15;
	private String dir;
	
	public Arrow(int x, int y, double[] speed, String orientation, int tag) {
		super(x, y, height, width, speed, false, tag);
		if (orientation.equals("V")) {
			setHeight(width);
			setWidth(height);
		}
	}
	
}
