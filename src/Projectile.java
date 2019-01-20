
public class Projectile extends Item {
	
	private int tag;
	
	public int getTag() {
		return tag;
	}
	
	public Projectile(int x, int y, int height, int width, int radius, double[] speed, boolean affectGravity, int tag) {
		super(x, y, height, width, radius, speed, affectGravity);
		this.tag = tag;
	}	
	
	public Projectile(int x, int y, int height, int width, double[] speed, boolean affectGravity, int tag) {
		super(x, y, height, width, 0, speed, affectGravity);
		this.tag = tag;
	}
	
	public Projectile(int x, int y, int radius, double[] speed, boolean affectGravity, int tag) {
		super(x, y, 0, 0, radius, speed, affectGravity);
		this.tag = tag;
	}

}
