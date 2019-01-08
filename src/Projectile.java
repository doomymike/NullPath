
public class Projectile extends Item {
	
	public Projectile(int x, int y, int height, int width, int radius, double[] speed, boolean affectGravity) {
		super(x, y, height, width, radius, speed, affectGravity);
	}	
	
	public Projectile(int x, int y, int height, int width, double[] speed, boolean affectGravity) {
		super(x, y, height, width, 0, speed, affectGravity);
	}
	
	public Projectile(int x, int y, int radius, double[] speed, boolean affectGravity) {
		super(x, y, 0, 0, radius, speed, affectGravity);
	}

}
