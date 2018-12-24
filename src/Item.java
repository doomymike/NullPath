

public class Item {
	private int x,y,height, width, radius;
	private int dx,dy;
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public int[] getVel() {
		int[] vel = {dx, dy};
		return vel;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public void setRadius(int rad) {
		radius = rad;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	Item(int x, int y){
		this.x = x;
		this.y = y;
	}
}
