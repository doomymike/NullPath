
public class Arrow extends Projectile{

	static int width = 30;
	static int height = 15;
	private String dir;
	
	public Arrow(int x, int y, double[] speed, String orientation, int tag) {
		super(x, y, height, width, speed, false, tag);
		try {
			this.setImage(ImageIO.read(new File("C:\\Users\\Michael\\eclipse-workspace\\ughPath\\src/Arrow.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (orientation.equals("V")) {
			setHeight(width);
			setWidth(height);
		}
	}
	
}
