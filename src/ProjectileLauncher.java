import java.util.ArrayList;

public class ProjectileLauncher extends Item{
	private String projectileType;
	private double launchDx;
	private double launchDy;
	private double initStart;
	private double lastNum;
	private int tag;
	
	public int getTag() {
		return tag;
	}
	
	public ProjectileLauncher(int x, int y, int height, int width, double launchDx, double launchDy, String projectileType, int tag) {
		super(x, y, height, width, true);
		this.projectileType = projectileType.toLowerCase();
		this.launchDx = launchDx;
		this.launchDy = launchDy;
		initStart = System.nanoTime()/(Math.pow(10, 9));
		lastNum = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
		this.tag = tag;
	}
	
	public ProjectileLauncher(int x, int y, int height, int width, double launchDx, double launchDy, boolean affectGravity, String projectileType, int tag) {
		super(x, y, height, width, affectGravity);
		this.projectileType = projectileType.toLowerCase();
		this.launchDx = launchDx;
		this.launchDy = launchDy;
		initStart = System.nanoTime()/(Math.pow(10, 9));
		lastNum = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
		this.tag = tag;
	}
	
	public int getConvSec() {
		return (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
	}
	
	public void launchProjectile(ArrayList<Projectile> projectList) { //Appends to a projectList called in main to draw the ball
		if (lastNum < (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart)) {
			lastNum = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
			if (projectileType.equals("ball")) {
				projectList.add(new Pellet (getX()+getWidth()/2, getY()+getHeight()/2, 15, new double[] {launchDx, launchDy}, true, tag));
			} else if (projectileType.equals("arrowh")) {
				projectList.add(new Arrow (getX()+getWidth()/2, getY()+getHeight()/2, new double[] {launchDx, launchDy}, "H", tag));
			} else if (projectileType.equals("arrowv")) {
				projectList.add(new Arrow (getX()+getWidth()/2, getY()+getHeight()/2, new double[] {launchDx, launchDy}, "V", tag));
			}
		}
	}
		
	
}
