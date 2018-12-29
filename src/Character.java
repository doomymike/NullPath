
import java.awt.image.BufferedImage;

public class Character {

	private int x,y,height,width;
	private double dx, dy;
	private boolean alive;
	private BufferedImage sprite;
	boolean affectGravity = true;
	int speed = 10;
	
	public Character(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	public boolean getGravity() {
		return affectGravity;
	}
	
	public BufferedImage getSprite(){
		return sprite;
	}
	
	public double[] getVelocity() {
		double[] vel = {dx, dy};
		return vel;
	}
	
	public void setVelocity(double [] newVel) {
		dx = newVel[0];
		dy = newVel[1];
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public void setPosition(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public int[] getPosition() {
		int[] location = new int[2];
		location[0] = x;
		location[1] = y;
		return location;
		
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void moveLeft() {
		this.setPosition(x-speed, y);
	}
	
	public void moveRight() {
		this.setPosition(x+speed, y);
	}
	
	public void crouch() {
		
	}
	
	public void jump() {
		
	}
	
}
