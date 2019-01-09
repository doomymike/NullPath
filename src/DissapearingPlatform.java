
public class DissapearingPlatform extends Platform{
	
	boolean exist;
	
	DissapearingPlatform(int x, int y, int width, int length) {
		super(x, y, width, length);
		// TODO Auto-generated constructor stub
	}

	
	void flip(){
		exist = !exist;
	}
	
}
