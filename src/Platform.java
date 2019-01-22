import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/* [Platform.java]
 * Class containing platform info
 * Author: James, Brian L, Michael, Brian Z
 * Date: 01/21/19
 * 
 */

//import java.awt.Rectangle;

public class Platform extends Item{
	
	private boolean hasHoney = false;
	private boolean hasIce = false;
	private HashMap<Integer, Double> lastVel = new HashMap<Integer, Double>();
	
	//Rectangle loc;
	
	
	public void addVelEntry(int uniqueTag, double dx) {
		lastVel.put(uniqueTag, dx);
	}
	
	public void removeVelEntry(int uniqueTag) {
		lastVel.remove(uniqueTag);
	}
	
	public double getEntry(int uniqueTag) {
		for (Map.Entry<Integer, Double> entry: lastVel.entrySet()) {
			if (entry.getKey() == uniqueTag) {
				return entry.getValue();
			}
		}
		return -1;
	}
	
	Platform(int x, int y) {
		super(x, y);
		
		try {
			this.setImage(ImageIO.read(new File("resources/PlatformGirder.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	Platform(int x,int y, int height, int width){
		super(x,y,height,width);
		
		try {
			this.setImage(ImageIO.read(new File("resources/PlatformGirder.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	Platform(int x,int y, int height, int width, double[] vel){
		super(x,y,height,width, vel);
		try {
			this.setImage(ImageIO.read(new File("resources/PlatformGirder.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**g
	 * getHoney
	 * 
	 * Returns if honey property exist
	 * 
	 * @return
	 */
	
	public boolean getHoney() {
		return hasHoney;
	}
	
	/**
	 * getIce
	 * 
	 * Returns if ice property exists
	 * 
	 * @return
	 */
	
	public boolean getIce() {
		return hasIce;
	}
	
	/**
	 * setHoney
	 * 
	 * Sets honey hazard for surface
	 * 
	 * @param newSurface
	 */
	
	public void setHoney(boolean newSurface) {
		hasHoney = newSurface;
	}
	
	/**
	 * setIce
	 * 
	 * Sets ice property for ice
	 * 
	 * @param newSurface
	 */
	
	public void setIce(boolean newSurface) {
		hasIce = newSurface;
	}
	
}
