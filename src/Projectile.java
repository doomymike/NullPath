/* [Projectile.java]
   Object reference for projectile damage dealing object
   Author: James Liang
   Date: January 22, 2019
*/
public class Projectile extends Item {
	
	//Base variables (store tag)
	private int tag;
	
	public int getTag() {
		return tag;
	}
	
	//Base constructor
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
