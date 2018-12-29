

public class Item {
	private int x,y,height, width, radius;
	private double dx,dy;
	private boolean affectGravity;
	
	public boolean getGravity() {
		return affectGravity;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public double[] getVel() {
		double[] vel = {dx, dy};
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
	
	public void setDy(double newDy) {
		dy = newDy;
	}
	
	Item(int x, int y, int height, int width, boolean affectGravity){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.affectGravity = affectGravity;
	}
	
	Item(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}
