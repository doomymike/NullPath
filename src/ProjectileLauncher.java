/*  [ProjectileLauncher.java]
    Object able to launch damaging projectiles through game.
    Author: James Liang, Michael Oren
    ICS4UE
    Date: 01/22/19
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ProjectileLauncher extends Item{
	private String projectileType;
	private double launchDx;
	private double launchDy;
	private double initStart;
	private double lastNum;
	private int tag;
	private String direction;
	
	private static int height = 50;
	private static int width = 50;

	public static int getH() {
		return height;
	}
	
	public static int getW() {
		return width;
	}
	
	public int getTag() {
		return tag;
	}
	
	public ProjectileLauncher(int x, int y, double launchDx, double launchDy, String projectileType, int tag, String direction) {
		super(x, y, height, width, true);
		this.projectileType = projectileType.toLowerCase();
		this.launchDx = launchDx;
		this.launchDy = launchDy;
		initStart = System.nanoTime()/(Math.pow(10, 9));
		lastNum = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
		this.tag = tag;
		this.direction = direction;
		
		try {
			if(this.projectileType.equals("pellet")) { //cannon
				for(int i=1;i<9;i++) {
					this.addSprite(ImageIO.read(new File("resources/Cannon"+i+".png")));
				}
			}else { //bow
				for(int i=1;i<10;i++) {
					this.addSprite(ImageIO.read(new File("resources/Bow"+i+".png")));
				}
			}
			this.setImage(this.getSprites().get(0));
		}catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDir() {
		return direction;
	}
	
	public ProjectileLauncher(int x, int y, double launchDx, double launchDy, boolean affectGravity, String projectileType, int tag, String direction) {
		super(x, y, height, width, affectGravity);
		this.projectileType = projectileType.toLowerCase();
		this.launchDx = launchDx;
		this.launchDy = launchDy;
		initStart = System.nanoTime()/(Math.pow(10, 9));
		lastNum = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
		this.tag = tag;
		this.direction = direction;
		
		try {
			if(this.projectileType.equals("ball")) { //cannon
				for(int i=1;i<9;i++) {
					this.addSprite(ImageIO.read(new File("resources/Cannon"+i+".png")));
				}
			}else { //bow
				for(int i=1;i<10;i++) {
					this.addSprite(ImageIO.read(new File("resources/Bow"+i+".png")));
				}
			}
			this.setImage(this.getSprites().get(0));
		}catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getConvSec() {
		return (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
	}
	
	public void launchProjectile(ArrayList<Projectile> projectList) { //Appends to a projectList called in main to draw the ball
		if (lastNum < (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart)) {
			lastNum = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
			this.setImage(this.getSprites().get(1));
			if (projectileType.equals("ball")) {
				projectList.add(new Pellet (getX()+getWidth()/2, getY()+getHeight()/2, new double[] {launchDx, launchDy}, true, tag));
			} else if (projectileType.equals("arrow")) {
				projectList.add(new Arrow (getX()+getWidth()/2, getY()+getHeight()/2, new double[] {launchDx, launchDy}, direction, tag));
			}
		}
	}
		
	
}
