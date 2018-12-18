
import java.awt.Rectangle;

public class Platform extends Item{
	
	int length,width;
	Rectangle loc;
	
	Platform(int x, int y) {
		super(x, y);
	
	}
	
	Platform(int x,int y, int width, int length){
		super(x,y);
		this.loc = new Rectangle(x,y,width,length);
	}
	
	
}

