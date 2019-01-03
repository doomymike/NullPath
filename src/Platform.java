
import java.awt.Rectangle;

public class Platform extends Item{
	
	private boolean hasHoney = false;
	private boolean hasIce = false;
	private int length,width;
	Rectangle loc;
	
	Platform(int x, int y) {
		super(x, y);
	
	}
	
	Platform(int x,int y, int width, int length){
		super(x,y);
		this.loc = new Rectangle(x,y,width,length);
	}
	
	public int getLength() {
		return length;
	}
	
	public int getWidth() {
		return width;
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

