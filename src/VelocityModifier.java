
public class VelocityModifier extends Item{

	private int dirMarker;
	private int xyDir;
	
	//Dictionary (dirMarker):
	//0 = Speed (direct abs influence on player speed)s
	//1 = Positive
	//2 = Negative
	
	//Dictionary (xyDir):
	//0 = Lateral
	//1 = Vertical
	
	public int getDirection() {
		return dirMarker;
	}
	
	public int getDirectionXY() {
		return xyDir;
	}
	
	public double[] getSpeed() {
		return null;
	}
	
	public VelocityModifier(int x, int y, int height, int width, int direction, int direction2) {
		super (x, y, height, width);
		this.dirMarker = direction;
		this.xyDir = direction2;
	}

}
