
//import java.awt.Rectangle;

public class Platform extends Item{
	
	private boolean hasHoney = false;
	private boolean hasIce = false;
	
	//Rectangle loc;
	
	Platform(int x, int y) {
		super(x, y);
	
	}
	
	Platform(int x,int y, int height, int width){
		super(x,y,height,width);
		
	}
	
	public int getLength() {
		return getLength();
	}
	
	public int getWidth() {
		return getWidth();
	}
	
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

