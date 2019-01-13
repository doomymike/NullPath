
public class tempItem {
	private int x,y,height, width, radius;
	private double dx,dy;
	private boolean affectGravity = true;
	private Player placer;
	
	public boolean getGravity() {
		return affectGravity;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public Player getPlacer(){
		return placer;
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
	
	public void setPlacer(Player placer) {
		this.placer = placer;
	}
	
	tempItem(int x, int y, int height, int width, boolean affectGravity){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.affectGravity = affectGravity;
	}
	
	tempItem(int x, int y, int height, int width){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	tempItem(int x, int y, int height, int width,int radius){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.radius=radius;
	}
	
	tempItem(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}