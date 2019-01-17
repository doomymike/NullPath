import java.util.HashMap;
import java.util.Map;


/**
 * Platform.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for platforms
 *
 */
public class Platform extends Item{
	
	private boolean hasHoney = false;
	private boolean hasIce = false;
	private HashMap<Integer, Double> lastVel = new HashMap<Integer, Double>();
	
	
	//velocity entries for velocity modifiers
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
	
	//constructors
	Platform(int x, int y) {
		super(x, y);
	
	}
	
	Platform(int x,int y, int height, int width){
		super(x,y,height,width);
		
	}
	
	//getters and setters
	public boolean getHoney() {
		return hasHoney;
	}
	
	public boolean getIce() {
		return hasIce;
	}
	
	public void setHoney(boolean newSurface) {
		hasHoney = newSurface;
	}
	
	public void setIce(boolean newSurface) {
		hasIce = newSurface;
	}
	
}
