
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Character {

	private int x,y,height,width;
	private double dx, dy;
	private boolean alive;
	private BufferedImage sprite;
	private BufferedImage[] sprites = new BufferedImage[32];
	boolean affectGravity = true;
	int speed = 10;
	
	
	public Character(String name) throws IOException{
		
		sprites[0] = ImageIO.read(new File("resources/"+name+"CrouchReference"));
		for (int i = 1;i<7;i++){
			sprites[0+i] = ImageIO.read(new File("resources/"+name+"DeathReference"+i));
		}
		
		sprites[7] = ImageIO.read(new File("resources/"+name+"FallReference"));
		for (int i = 1;i<7;i++){
			sprites[7+i] = ImageIO.read(new File("resources/"+name+"IdleReference"+i));
		}
		sprites[14] = ImageIO.read(new File("resources/"+name+"JumpReference"));
		for (int i = 1;i<9;i++){
			sprites[14+i] = ImageIO.read(new File("resources/"+name+"RunReference"+i));
		}
		sprites[23] = ImageIO.read(new File("resources/"+name+"SlideReference"));
		for (int i = 1;i<9;i++){
			sprites[23+i] = ImageIO.read(new File("resources/"+name+"VictoryReference"+i));
		}
	}
	
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
