/* [Platform.java]
 * Class containing platform info
 * Author: James, Brian L, Michael, Brian Z
 * Date: 01/21/19
 */

//java imports
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class Platform extends Item {
	
	private boolean hasHoney = false; //Honey modifier
	private boolean hasIce = false; //Ice modifier
	private HashMap<Integer, Double> lastVel = new HashMap<Integer, Double>(); //Holds hashmap of associated player's last velocities
	
	/**
	* addVelEntry
	*
	* Adds lastVelocity for a player
	*
	*@param uniqueTag
	*@param dx
	*/
	
	public void addVelEntry(int uniqueTag, double dx) {
		lastVel.put(uniqueTag, dx);
	}
	
	/**
	* addVelEntry
	*
	* Removes lastVelocity for a player
	*
	*@param uniqueTag
	*/
	
	public void removeVelEntry(int uniqueTag) {
		lastVel.remove(uniqueTag);
	}
	
	/**
	* getEntry
	*
	* Retrieves last velocity for a player
	*
	*@param uniqueTag
	*/
	
	public double getEntry(int uniqueTag) {
		for (Map.Entry<Integer, Double> entry: lastVel.entrySet()) {
			if (entry.getKey() == uniqueTag) {
				return entry.getValue();
			}
		}
		return -1;
	}
	
	/**
	* Platform
	*
	* Platform constructor
	*
	*/
	
	Platform(int x, int y) {
		super(x, y);
		
		try {
			this.setImage(ImageIO.read(new File("resources/PlatformGirder.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	/**
	* Platform
	*
	* Platform constructor
	*
	*/
	
	Platform(int x,int y, int height, int width){
		super(x,y,height,width);
		
		try {
			this.setImage(ImageIO.read(new File("resources/PlatformGirder.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * getHoney
	 * Returns if honey property exists
	 * @return boolean hasHoney, whether there is honey on the platform
	 */
	public boolean getHoney() {
		return hasHoney;
	} //End of getHoney
	
	/**
	 * getIce
	 * Returns if ice property exists
	 * @return boolean hasIce, whether there is ice on the platform
	 */
	public boolean getIce() {
		return hasIce;
	} //End of getIce
	
	/**
	 * setHoney
	 * Sets honey hazard for surface
	 * @param newSurface, whether there is to be honey or not
	 */
	public void setHoney(boolean newSurface) {
		hasHoney = newSurface;
	} //End of setHoney
	
	/**
	 * setIce
	 * Sets ice property for ice
	 * @param newSurface, whether there is to be ice or not
	 */
	public void setIce(boolean newSurface) {
		hasIce = newSurface;
	} //End of setIce
	
} //End of class
