/**
 * [Character.java]
 * Character object for NullPath
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

//java imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Character {

	private int x,y,height,width;
	private double dx, dy;
	private boolean alive = true;
	private BufferedImage sprite;
	private BufferedImage[] sprites = new BufferedImage[32];
	int speed = 10;
	private boolean affectGravity = true;
	private int uniqueTag;
	private String color;
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
	private boolean finished = false;
	private Item killedBy; //Store item that killed them in a round (for score)
	private int platMotion;
	private boolean fanWindM;
	
	/**
	 * getColor
	 * returns which character (characters are named by color)
	 * @return String color
	 */
	public String getColor() {
		return color.toLowerCase();
	}
	
	/**
	 * setFWM 
	 * sets whether or not the character is affected by fan (FWM = fan wind motion)
	 * @param newFanM
	 */
	public void setFWM(boolean newFanM) {
		fanWindM = newFanM;
	}
	
	/**
	 * getFWM
	 * returns whether or not the character is affected by fan
	 * @return boolean fanWindM 
	 */
	public boolean getFWM() {
		return fanWindM;
	}
	
	/**
	 * setPMotion
	 * sets character platform-influenced motion
	 * @param motion
	 */
	public void setPMotion(int motion) {
		platMotion = motion;
	}
	
	/**
	 * setHeight
	 * sets height of character (used for crouching)
	 * @param newHeight
	 */
	public void setHeight(int newHeight) {
		height = newHeight;
	}
	
	/**
	 * getPMotion
	 * returns platform-influenced character motion
	 * @return int platMotion
	 */
	public int getPMotion() {
		return platMotion;
	}
	
	/**
	 * getKilledBy
	 * returns item that killed player
	 * @return Item killedBy
	 */
	public Item getKilledBy() {
		return killedBy;
	}

	/**
	 * setKilledBy
	 * sets item that killed player
	 * @param killedBy
	 */
	public void setKilledBy(Item killedBy) {
		this.killedBy = killedBy;
	}
	
	/**
	 * getFinished
	 * returns whether character made it to finish
	 * @return boolean finished
	 */
	public boolean getFinished() {
		return finished;
	}
	
	/**
	 * setFinished
	 * sets player finish status
	 * @param finished
	 */
	public void setFinished(boolean finished) {
		if(!this.finished &&finished) {
			currentFrameIndex = 24; //victory dance
			activeFrame = sprites[24];
		}
		this.finished = finished;
	}
	
	/**
	 * setLastI
	 * sets last instance of ice
	 * @param recentIce
	 */
	public void setLastI(double recentIce){
		lastIce = recentIce;
	}
	
	/**
	 * getLastI
	 * returns last instance of ice
	 * @return double lastIce
	 */
	public double getLastI() {
		return lastIce;
	}
	
	/**
	 * getIMotion
	 * returns whether or not character is on ice
	 * @return boolean iceMotion
	 */
	public boolean getIMotion() {
		return iceMotion;
	}
	
	/**
	 * setIMotion
	 * sets whether or not character is on ice
	 * @param state
	 */
	public void setIMotion(boolean state) {
		iceMotion = state;
	}
	
	/**
	 * getHmotion
	 * returns honey status
	 * @return boolean[] lastHoney
	 */
	public boolean[] getHMotion() {
		return lastHoney;
	}
	
	/**
	 * setHmotion
	 * sets honey status
	 * @param motion
	 * @param idx
	 */
	public void setHMotion(boolean motion, int idx) {
		lastHoney[idx] = motion;
	}
	
	/**
	 * getHoney
	 * returns honey
	 * @return boolean applyHoney
	 */
	public boolean getHoney() {
		return applyHoney;
	}
	
	/**
	 * setHoney
	 * sets honey
	 * @param state
	 */
	public void setHoney(boolean state) {
		applyHoney = state;
	}
	
	/**
	 * getMotion
	 * returns motion
	 * @return boolean[] motionState
	 */
	public boolean[] getMotion() {
		return motionState;
	}
	
	/**
	 * setMotion
	 * sets motion
	 * @param state
	 * @param idx
	 */
	public void setMotion(boolean state, int idx) {
		motionState[idx] = state;
	}
	
	/**
	 * Character
	 * constructor, makes character from name
	 * @param name
	 * @throws IOException
	 */
	Character(String name) throws IOException{
		
		this.color = name;
		
		// currentAction = "crouch"
		sprites[0] = ImageIO.read(new File("resources/"+name+"CrouchReference"+".png"));
		
		// currentAction = "death"
		for (int i = 1;i<7;i++){
			sprites[0+i] = ImageIO.read(new File("resources/"+name+"DeathReference"+i+".png"));
		}
		
		// currentAction = "fall"
		sprites[7] = ImageIO.read(new File("resources/"+name+"FallReference"+".png"));
		
		// currentAction = "idle"
		for (int i = 1;i<7;i++){
			sprites[7+i] = ImageIO.read(new File("resources/"+name+"IdleReference"+i+".png"));
		}
		
		// currentAction = "jump"
		sprites[14] = ImageIO.read(new File("resources/"+name+"JumpReference"+".png"));
		
		// currentAction = "run"
		for (int i = 1;i<9;i++){
			sprites[14+i] = ImageIO.read(new File("resources/"+name+"RunReference"+i+".png"));
		}
		
		// currentAction = "slide"
		sprites[23] = ImageIO.read(new File("resources/"+name+"SlideReference"+".png"));
		
		// currentAction = "victory"
		for (int i = 1;i<9;i++){
			sprites[23+i] = ImageIO.read(new File("resources/"+name+"VictoryReference"+i+".png"));
		}
		
	}
	
	/**
	 * Character
	 * constructor, creates character from name, location and size
	 * @param name
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 * @param tag
	 * @throws IOException
	 */
	Character(String name,int x, int y, int height, int width, int tag) throws IOException{
		
		this.color = name;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.uniqueTag = tag;
		this.dx = 0;
		this.dy = 0;
		
		// currentAction = "crouch"
		sprites[0] = ImageIO.read(new File("resources/"+name+"CrouchReference"+".png"));
		
		// currentAction = "death"
		for (int i = 1;i<7;i++){
			sprites[0+i] = ImageIO.read(new File("resources/"+name+"DeathReference"+i+".png"));
		}
		
		// currentAction = "fall"
		sprites[7] = ImageIO.read(new File("resources/"+name+"FallReference"+".png"));
		
		// currentAction = "idle"
		for (int i = 1;i<7;i++){
			sprites[7+i] = ImageIO.read(new File("resources/"+name+"IdleReference"+i+".png"));
		}
		
		// currentAction = "jump"
		sprites[14] = ImageIO.read(new File("resources/"+name+"JumpReference"+".png"));
		
		// currentAction = "run"
		for (int i = 1;i<9;i++){
			sprites[14+i] = ImageIO.read(new File("resources/"+name+"RunReference"+i+".png"));
		}
		
		// currentAction = "slide"
		sprites[23] = ImageIO.read(new File("resources/"+name+"SlideReference"+".png"));
		
		// currentAction = "victory"
		for (int i = 1;i<9;i++){
			sprites[23+i] = ImageIO.read(new File("resources/"+name+"VictoryReference"+i+".png"));
		}
		
		currentFrameIndex = 8;
	}
	
	/**
	 * Character
	 * constructor, creates character from sprite
	 * @param sprite
	 */
	Character(BufferedImage sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Character
	 * constructor, creates character from location and size
	 * @param x
	 * @param y
	 * @param height
	 * @param width
	 * @param tag
	 */
	Character(int x, int y, int height, int width, int tag){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.uniqueTag = tag;
		this.dx = 0;
		this.dy = 0;
	}

	/**
	 * getIce
	 * returns whether or not the character is on ice
	 * @return boolean onIce
	 */
	public boolean getIce() {
		return onIce;
	}
	
	/**
	 * setIce
	 * sets whether or not the character is on ice
	 * @param state
	 */
	public void setIce(boolean state) {
		onIce = state;
	}
	
	/**
	 * setPDx
	 * sets dx preserver for future use
	 * @param newDx
	 */
	public void setPDx(double newDx) {
		preserveDx = newDx;
	}
	
	/**
	 * getPDx
	 * returns preserved dx
	 * @return double preserveDx
	 */
	public double getPDx() {
		return preserveDx;
	}
	
	/**
	 * getTag
	 * returns tag
	 * @return int uniqueTag
	 */
	public int getTag() {
		return uniqueTag;
	}
	
	/**
	 * setJump
	 * sets jump status
	 * @param state
	 */
	public void setJump(boolean state) {
		canJump = state;
	}
	
	/**
	 * getGravity
	 * returns gravity stauts
	 * @return boolean affectGravity
	 */
	public boolean getGravity() {
		return affectGravity;
	}
	
	/**
	 * setGravity
	 * sets gravity status
	 * @param state
	 */
	public void setGravity(boolean state) {
		affectGravity = state;
	}
	
	/**
	 * getSprite
	 * returns sprite
	 * @return BufferedImage sprite
	 */
	public BufferedImage getSprite(){
		return sprite;
	}
	
	/**
	 * getVelocity
	 * returns velocity
	 * @return double[] vel
	 */
	public double[] getVelocity() {
		double[] vel = {dx, dy};
		return vel;
	}
	
	/**
	 * setVelocity
	 * sets velocity
	 * @param newVel
	 */
	public void setVelocity(double [] newVel) {
		dx = newVel[0];
		dy = newVel[1];
	}
	
	/**
	 * isAlive
	 * returns whether or not character is alive
	 * @return boolean alive
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * setAlive
	 * sets whether or not character is alive
	 * @param alive
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 * setPosition
	 * sets character position
	 * @param x
	 * @param y
	 */
	public void setPosition(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * getPosition
	 * returns position
	 * @return int[] location
	 */
	public int[] getPosition() {
		int[] location = new int[2];
		location[0] = x;
		location[1] = y;
		return location;
		
	}
	
	/**
	 * getHeight
	 * returns height
	 * @return int height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * getWidth
	 * returns width
	 * @return int width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * crouch
	 */
	public void crouch() {
		
	}
	
	/**
	 * getJump
	 * returns whether or not the character can jump
	 * @return boolean vanJump
	 */
	public boolean getJump() {
		return canJump;
	}
	
	/**
	 * resetGravity
	 * resets gravity
	 */
	public void resetGravity() {
		affectGravity = true;
	}
	
	/**
	 * resetY
	 * resets y velocity
	 */
	public void resetY() {
		affectGravity = false;
		if (dy > 0) {
			dy = 0;
		}
	}
	
	/**
	 * setCurrentAction
	 * sets current character action
	 * @param action
	 */
	public void setCurrentAction(String action) {
		this.currentAction = currentAction;
	}
	
	/**
	 * getDirectionFacing
	 * returns the direction the character is facing
	 * @return String directionFacing
	 */
	public String getDirectionFacing() {
		return directionFacing;
	}
	
	/**
	 * setDirectionFacing
	 * sets direction the character is facing
	 * @param directionFacing
	 */
	public void setDirectionFacing(String directionFacing) {
		this.directionFacing = directionFacing;
	}
	
	/**
	 * die
	 * sets character to dead and begins death animation if not already
	 */
	public void die() {
		if(alive) {
			this.setCurrentFrameIndex(1);
		}
		alive = false;
		currentAction = "death";
		
		
	}
	
	/**
	 * getActiveFrame
	 * returns current sprite
	 * @return BufferedImage activeFrame
	 */
	public BufferedImage getActiveFrame() {
		return activeFrame;
	}

	/**
	 * getCurrentFrameIndex
	 * returns current frame index
	 * @return int currentFrameIndex
	 */
	public int getCurrentFrameIndex() {
		return currentFrameIndex;
	}

	/**
	 * setCurrentFrameIndex
	 * sets current frame index
	 * @param currentFrameIndex
	 */
	public void setCurrentFrameIndex(int currentFrameIndex) {
		this.currentFrameIndex = currentFrameIndex;
		activeFrame = sprites[currentFrameIndex];
	}
	
	/**
	 * getSprites
	 * returns spritelist
	 * @return BufferedImage[] sprites
	 */
	public BufferedImage[] getSprites(){
		return sprites;
	}
	
}
