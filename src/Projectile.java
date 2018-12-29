
public class Projectile {

	private int x, y, height, width;
	private double dx, dy;
	private boolean affectGravity = true;
	
	public Projectile(int x, int y, int height, int width, double[] speed) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.dx = speed[0];
		this.dy = speed[1];
	}
	
	public Projectile(int x, int y, int height, int width, double[] speed, boolean affectGravity) {
		this.affectGravity = affectGravity;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.dx = speed[0];
		this.dy = speed[1];
	}
	
	public int[] getPosition(){
		int[] pos = {x, y};
		return pos;
	}
	
	public double[] getVel() {
		double[] vel = {dx, dy};
		return vel;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}

}
