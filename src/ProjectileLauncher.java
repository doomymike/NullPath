import java.util.ArrayList;

public class ProjectileLauncher extends Item{
	private String projectileType;
	
	public ProjectileLauncher(int x, int y, int height, int width, String projectileType) {
		super(x, y, height, width, true);
		this.projectileType = projectileType;
	}
	
	public ProjectileLauncher(int x, int y, int height, int width, boolean affectGravity, String projectileType) {
		super(x, y, height, width, affectGravity);
		this.projectileType = projectileType;
	}
	
	public void launchProjectile(double dx, double dy, ArrayList<Projectile> projectList) { //Appends to a projectList called in main to draw the ball
		projectList.add(new Pellet (getX()+getWidth()/2, getY()+getHeight()/2, 15, new double[] {dx, dy}, true));
	}
	
}
