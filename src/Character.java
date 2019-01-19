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
	int speed = 10;
	private boolean affectGravity = true;
	private int uniqueTag;
	private double preserveDx = 0;
	private boolean onIce = false;
	private boolean canJump = false;
	private boolean [] motionState = {false, false, false, false};
	private boolean [] lastHoney = {false, false};
	private double lastIce = 0;
	//MotionState: Used in correspondance to keyListeners to ensure that velocities are only changed once (no acceleration)
	//{leftState, rightState, upState, downState}
	private boolean applyHoney = false;
	private boolean honeyMotion = false;
	private boolean iceMotion = false;
	private String currentAction = "idle";
	private String directionFacing = "right";
	private BufferedImage activeFrame;
	private int currentFrameIndex;
	private boolean finished;
	private Item killedBy;
	private int platMotion;
	
	public void setPMotion(int motion) {
		platMotion = motion;
	}
	
	public int getPMotion() {
		return platMotion;
	}
	
	public Item getKilledBy() {
		return killedBy;
	}

	public void setKilledBy(Item killedBy) {
		this.killedBy = killedBy;
	}
	
	public boolean getFinished() {
		return finished;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public void setLastI(double recentIce){
		lastIce = recentIce;
	}
	
	public double getLastI() {
		return lastIce;
	}
		
	public boolean getIMotion() {
		return iceMotion;
	}
	
	public void setIMotion(boolean state) {
		iceMotion = state;
	}
	
	public boolean[] getHMotion() {
		return lastHoney;
	}
	
	public void setHMotion(boolean motion, int idx) {
		lastHoney[idx] = motion;
	}
	
	public boolean getHoney() {
		return applyHoney;
	}
	
	public void setHoney(boolean state) {
		applyHoney = state;
	}
	
	public boolean[] getMotion() {
		return motionState;
	}
	
	public void setMotion(boolean state, int idx) {
		motionState[idx] = state;
	}

	Character() {
		
	}

	Character(String name) throws IOException{
		
		// currentAction = "crouch"
		sprites[0] = ImageIO.read(new File("resources/"+name+"CrouchReference"));
		
		// currentAction = "death"
		for (int i = 1;i<7;i++){
			sprites[0+i] = ImageIO.read(new File("resources/"+name+"DeathReference"+i));
		}
		
		// currentAction = "fall"
		sprites[7] = ImageIO.read(new File("resources/"+name+"FallReference"));
		
		// currentAction = "idle"
		for (int i = 1;i<7;i++){
			sprites[7+i] = ImageIO.read(new File("resources/"+name+"IdleReference"+i));
		}
		
		// currentAction = "jump"
		sprites[14] = ImageIO.read(new File("resources/"+name+"JumpReference"));
		
		// currentAction = "run"
		for (int i = 1;i<9;i++){
			sprites[14+i] = ImageIO.read(new File("resources/"+name+"RunReference"+i));
		}
		
		// currentAction = "slide"
		sprites[23] = ImageIO.read(new File("resources/"+name+"SlideReference"));
		
		// currentAction = "victory"
		for (int i = 1;i<9;i++){
			sprites[23+i] = ImageIO.read(new File("resources/"+name+"VictoryReference"+i));
		}
		
	}
	
	Character(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	Character(int x, int y, int height, int width, int tag){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.uniqueTag = tag;
		this.dx = 0;
		this.dy = 0;
	}

	public boolean getIce() {
		return onIce;
	}
	
	public void setIce(boolean state) {
		onIce = state;
	}
	
	public void setPDx(double newDx) {
		preserveDx = newDx;
	}
	
	public double getPDx() {
		return preserveDx;
	}
	
	public int getTag() {
		return uniqueTag;
	}
	
	public void setJump(boolean state) {
		canJump = state;
	}
	
	public boolean getGravity() {
		return affectGravity;
	}
	
	public void setGravity(boolean state) {
		affectGravity = state;
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
	
	public void crouch() {
		
	}
	
	public boolean getJump() {
		return canJump;
	}
	
	public void resetGravity() {
		affectGravity = true;
	}
	
	public void resetY() {
		affectGravity = false;
		if (dy > 0) {
			dy = 0;
		}
	}
	
	public void setCurrentAction(String action) {
		this.currentAction = currentAction;
	}
	
	public String getDirectionFacing() {
		return directionFacing;
	}
	
	public void setDirectionFacing(String directionFacing) {
		this.directionFacing = directionFacing;
	}
	
	public void die() {
		alive = false;
		currentAction = "death";
	}
	
	public BufferedImage getActiveFrame() {
		return activeFrame;
	}

	public int getCurrentFrameIndex() {
		return currentFrameIndex;
	}

	public void setCurrentFrameIndex(int currentFrameIndex) {
		this.currentFrameIndex = currentFrameIndex;
		activeFrame = sprites[currentFrameIndex];
	}
	
	public BufferedImage[] getSprites(){
		return sprites;
	}
	
}
