/**
 * Spike.java
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * Class for spike object
 *
 */
public class Spike extends ContactDamage{

	//constructors
	Spike(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	Spike(int x, int y, int height, int width){
		super(x,y,height,width,0);
	}
	
}
