import java.awt.Rectangle;
import java.util.ArrayList;
import java.io.*;

public class PhysicsEngine{
	
	File file = new File("resources/SkyFortressCollision.txt");
	String [][] contactMap = null;
	static ArrayList<Integer> tagMap = new ArrayList<Integer>();	
	
	/**
	 * PhysicsEngine
	 * 
	 * Constructor for physics engine. String input used for overloadding the constructor.
	 * 
	 * @param read
	 */
	
	PhysicsEngine(String read){
		contactMap = new String[38][86];
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.contactMap = contactMap;
		String line;
		int lineCount = -1;
		try {
			while ((line = br.readLine()) != null) {
				lineCount += 1;
				for (int i = 0; i < line.length(); i++) {
					if (line.substring(i, i+1).equals("1")){
						contactMap[lineCount][i] = "1";
					} else if (line.substring(i,  i+1).equals("2")){
						contactMap[lineCount][i] = "2";
					} else if ((line.substring(i, i+1)).equals("0")) {
						contactMap[lineCount][i] = "0";
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * PhysicsEngine
	 * 
	 * Blank Constructor
	 * 
	 */
	
	PhysicsEngine(){
	}
	
	/**
	 * retrieveCMap
	 * 
	 * Retrieves the contactMap from phyicsEngine
	 * 
	 * @return
	 */
	
	public String[][] retrieveCMap(){
		return contactMap;
	}
	
	/**
	 * printMap
	 * 
	 * Helper method used for troubleshooting
	 * 
	 * @param contactMap
	 */
	
	public void printMap(String[][] contactMap) {
		for (int i = 0; i < contactMap.length; i++) {
			String line = "";
			for (int a = 0; a < contactMap[0].length; a++) {
				line += contactMap[i][a];
			}
			System.out.println(line);
		}
	}

	/*
	 * inMapTag
	 * 
	 * Used for collision between contactMap and character tags. Ensures that unique characters will collide once, useful for things such as resetting gravity and position
	 * 
	 * @param uniqueTag
	 * @return
	 */
	
	private static boolean inMapTag(int uniqueTag) {
		for (int i = 0; i < tagMap.size(); i++) {
			if (tagMap.get(i) == uniqueTag) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * detectTop 
	 * 
	 * Method used for detecting whether or not the given contact tiles are near the top edge of the contactMap.
	 * Useful for smooth collision of player with the cotnactMap.
	 * 
	 * @param contactLX - Should be reduced from character position into contactMap terms (/20)
	 * @param contactRX
	 * @param contactY
	 * @param win
	 * @return
	 */
	
	
	private boolean detectTop(int contactLX, int contactRX, int contactY, boolean win) {
		int oneCount = 0;
		if (contactY <= 1) {
			return true;
		}
		
		/*
		 * 
		 * Logic:
		 * 
		 * First phase: Check if the player is standing on an actual platform. Checks to see if lower tiles are zeros. Will return false if tiles 1 and 2 below are zero (expanded to 2, as 1 can result in glitchy behaviour)
		 * Second phase: Check if the top x tiles are non-zero tiles. Will result in false if the number of tiles (of which are non-zero) exceeds a certain threshold
		 * 
		 */
		
		int zeroC = 0;
		for (int j = 0; j < 2; j++) {
			if (contactMap[Math.min(contactY+j, 37)][contactLX].equals("0") && contactMap[Math.min(contactY+j, 37)][contactRX].equals("0")) {
				zeroC++;
			}
		}
		
		if (zeroC == 2) {
			return false;
		}
		
		if (win) {
			for (int i = 1; i < 4; i++) {
				if (contactMap[contactY-i][contactLX].equals("2") || contactMap[contactY-i][contactRX].equals("2")) {
					oneCount += 1;
				}
			}
			if (oneCount <= 2) {
				return true;
			}
		} else {
			for (int i = 1; i < 4; i++) {
				if (!contactMap[contactY-i][contactLX].equals("0") || !contactMap[contactY-i][contactRX].equals("0")) {
					oneCount += 1;
				}
			}
			if (oneCount <= 2) {
				return true;
			}
		}
		
		return false;
	}
	
	/*private
	 * checkHEdge
	 * 
	 * Checks if object has collided into the left or right side of a contactMap 
	 * 
	 * @param contactLX
	 * @param contactRX
	 * @param contactY
	 * @return
	 */
	
	private String checkHEdge(int contactLX, int contactRX, int contactY) { //Returns if left or right 
		if ((!contactMap[contactY][contactLX].equals("1") || !contactMap[contactY][contactLX].equals("2")) && contactMap[contactY][contactRX].equals("0")) {
			if (contactMap[contactY][contactLX+1].equals("0") || contactMap[contactY][contactLX+2].equals("0")){
				return "R"; //Colliding into (right)
			}
		}
		
		if ((!contactMap[contactY][contactRX].equals("1") || !contactMap[contactY][contactRX].equals("2")) && contactMap[contactY][contactLX].equals("0")) {
			if (contactMap[contactY][contactRX-1].equals("0") || contactMap[contactY][contactRX-2].equals("0")){
				return "L";
			}
		}
		
		return "N";
		
		
	}
	
	/*
	 * endPoint
	 * 
	 * Followup for checkHEdge, will retrieve the horizontal endpoints of the contactMap collision. E.g. if colliding from the right, return left most tile connected to that part of the contactMap.
	 * Used to reset player position upon horizontal collision.
	 * 
	 * @param contactLX
	 * @param contactRX
	 * @param contactY
	 * @param LR
	 * @param win
	 * @return
	 */
	
	private int endPoint(int contactLX, int contactRX, int contactY, String LR, boolean win) {
		int counter = 0;
		if (LR.equals("R")) {
			while (true) {
				if (win) {
					if (contactMap[contactY][contactLX+counter].equals("2")) {
						counter++;
					} else {
						return 20*(contactLX+counter); //Expansion of compressed points for usable coordinate.
					}
				} else {
					if (contactMap[contactY][contactLX+counter].equals("1")) {
						counter++;
					} else {
						return 20*(contactLX+counter);
					}
				}
			}
		} else if (LR.equals("L")) {
			while (true) {
				if (win) {
					if (contactMap[contactY][contactRX+counter].equals("2")) {
						counter--;
					} else {
						return 20*(contactLX+counter+1);
					}
				} else {
					if (contactMap[contactY][contactRX+counter].equals("1")) {
						counter--;
					} else {
						return 20*(contactLX+counter+1);
					}
				}
			}
		} 
		return 0;
	}
	
	/*
	 * gridEquals
	 * 
	 * General method to 
	 * 
	 * @param contactY
	 * @param contactHY
	 * @param contactLX
	 * @param contactRX
	 * @param val
	 * @return
	 */
	
	private boolean gridEquals(int contactY, int contactHY, int contactLX, int contactRX, String val) {
		
		if (contactMap[contactY][contactLX].equals(val) || contactMap[contactY][contactRX].equals(val)) {
			return true;
		} 
		if (contactMap[contactHY][contactLX].equals(val) || contactMap[contactHY][contactRX].equals(val)) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * contactMapCollision
	 * 
	 * Uses collection of helper methods above to check if player collides with actual contactMap.
	 * 
	 * @param testPlayer
	 */
	
	public void contactMapCollision(Character testPlayer) {
		int contactLX = Math.min(Math.max((int)(testPlayer.getPosition()[0])/20, 0), 85);
		int realLX = (int)(testPlayer.getPosition()[0]);
		int contactRX = Math.min(Math.max((int)(testPlayer.getPosition()[0]+testPlayer.getWidth())/20, 0), 85);
		int realRX = (int)(testPlayer.getPosition()[0]+testPlayer.getWidth());
		int contactY = Math.min(Math.max((int)((testPlayer.getPosition()[1]+testPlayer.getHeight())/20), 0), 37);
		int contactHY = Math.min(Math.max((int)((testPlayer.getPosition()[1])/20), 0), 37);
		boolean inCol = false; //Checks for collision with 1 or 2
		
		if (gridEquals(contactY, contactHY, contactLX, contactRX, "1") && (detectTop(contactLX, contactRX, contactY, false) == false)) {
			if (checkHEdge(contactLX, contactRX, contactY).equals("R") && testPlayer.getVelocity()[0] < 0) {
				testPlayer.setPosition(endPoint(contactLX, contactRX, contactY, "R", false), testPlayer.getPosition()[1]);
			} else if (checkHEdge(contactLX, contactRX, contactY).equals("L") && testPlayer.getVelocity()[0] > 0) {
				testPlayer.setPosition(endPoint(contactLX, contactRX, contactY, "L", false), testPlayer.getPosition()[1]);
			}
			return;
		}
		
		if (gridEquals(contactY, contactHY, contactLX, contactRX, "2") && (detectTop(contactLX, contactRX, contactY, true) == false)){
			if (checkHEdge(contactLX, contactRX, contactY).equals("R") && testPlayer.getVelocity()[0] < 0) {
				testPlayer.setPosition(endPoint(contactLX, contactRX, contactY, "R", true), testPlayer.getPosition()[1]);
			} else if (checkHEdge(contactLX, contactRX, contactY).equals("L") && testPlayer.getVelocity()[0] > 0) {
				testPlayer.setPosition(endPoint(contactLX, contactRX, contactY, "L", true), testPlayer.getPosition()[1]);
			}
			return;
		}
		
		if ((contactMap[contactY][contactLX].equals("2") || contactMap[contactY][contactRX].equals("2")) && detectTop(contactLX, contactRX, contactY, true)) {
			inCol = true;
			if (inMapTag(testPlayer.getTag()) == false || testPlayer.getGravity()){
				testPlayer.resetY();
				tagMap.add(testPlayer.getTag());
				testPlayer.setFinished(true);
				return;
			}
		} else if ((contactMap[contactY][contactLX].equals("1") || contactMap[contactY][contactRX].equals("1")) && detectTop(contactLX, contactRX, contactY, false)) {
			inCol = true;
			if (inMapTag(testPlayer.getTag()) == false || testPlayer.getGravity()){
				testPlayer.resetY();
				tagMap.add(testPlayer.getTag());
				testPlayer.setJump(true);
				return;
			}
		} else if (contactMap[contactY][contactLX].equals("0") || contactMap[contactY][contactRX].equals("0") && inCol == false){
			testPlayer.setJump(false);
			if (inMapTag(testPlayer.getTag())) {
				tagMap.remove(Integer.valueOf(testPlayer.getTag()));
				testPlayer.resetGravity();
			}
			return;
		}
		
	}
	
	/**
	 * checkCMCollision
	 * 
	 * General method to check for collision between the contactMap and an object
	 * 
	 * @param arbItem
	 * @param circle
	 * @return
	 */
	
	public boolean checkCMCollision(Item arbItem, boolean circle) {
		int contactLX = 0;
		int contactRX = 0;
		int contactY = 0;
		int contactHY = 0;
		
		if (circle) {
			contactLX = Math.min(Math.max((int)(arbItem.getX())/20, 0), 85); //Clipping used to prevent arrayOutOfBounds Exception (contactMap is referenced directly
			contactRX = Math.min(Math.max((int)(arbItem.getX()+arbItem.getRadius())/20, 0), 85);
			contactY = Math.min(Math.max((int)((arbItem.getY()+arbItem.getRadius())/20), 0), 37);
			contactHY = Math.min(Math.max((int)((arbItem.getY())/20), 0), 37);
		} else {
			contactLX = Math.min(Math.max((int)(arbItem.getX())/20, 0), 85);
			contactRX = Math.min(Math.max((int)(arbItem.getX()+arbItem.getWidth())/20, 0), 85);
			contactY = Math.min(Math.max((int)((arbItem.getY()+arbItem.getHeight())/20), 0), 37);
			contactHY = Math.min(Math.max((int)((arbItem.getY())/20), 0), 37);
			String totalC = "";

		}
		
		for (int i = 0; i < contactY-contactHY+1; i++) {
			for (int j = 0; j < contactRX - contactLX+1; j++) {
				if (!contactMap[Math.min(contactHY+i, 37)][Math.min(contactLX+j, 85)].equals("0")) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	/**
	 * move
	 * 
	 * Updates position of character based on velocity
	 * 
	 * @param player
	 */
	
	public void move(Character player){
		int xPos = player.getPosition()[0];
		int yPos = player.getPosition()[1];
				
		if (player.getGravity()) {
			player.setVelocity(new double[] {player.getVelocity()[0], Math.min(player.getVelocity()[1]+0.06, 7)}); //Clip velocity, else decrease by a small rate consistently
		}
		//System.out.println(player.getVelocity()[0] + " X_vel" + player.getVelocity()[1] + " Y_vel");
		player.setPosition((int)Math.round(xPos + player.getVelocity()[0]), (int)Math.round(yPos + player.getVelocity()[1]));
		
	}
	
	/** 
	 * move
	 * 
	 * Overloaded method that updates the position of an object based on its velocity
	 * 
	 * @param object
	 */
	
	public void move(Item object) {
		int xPos = object.getX();
		int yPos = object.getY();
		if (object.getGravity()) {
			object.setDy(object.getVel()[1]+0.06); //Gravity
		}
		object.setX((int)Math.round(xPos + object.getVel()[0]));
		object.setY((int)Math.round(yPos + object.getVel()[1]));
		
		if (object instanceof MovingPlatform) {
			xPos = object.getX();
			if ((((MovingPlatform)object).getDirection()) == 0) {
				if (((MovingPlatform)object).getEndPos()[0] <= xPos || ((MovingPlatform)object).getStartPos()[0] >= xPos) {
					object.setDx(-object.getVel()[0]);
				}
			} else if (((MovingPlatform)object).getDirection() == 1) {
				if (((MovingPlatform)object).getEndPos()[0] >= xPos || ((MovingPlatform)object).getStartPos()[0] <= xPos) {
					object.setDx(-object.getVel()[0]);
				}
			}
		}
	}
	
	/**
	 * checkCollision
	 * 
	 * Main check collision method used to determine the collision of a player and item.
	 * Also used to determine the influence that the item has on the player.
	 * 
	 * @param player
	 * @param object
	 * @param circular - if object is circular or not
	 * @return
	 */
	
	public static boolean checkCollision(Character player, Item object, boolean circular) {
		boolean hasCollided = false;
		boolean hTest = false;
		
		int lowerX = player.getPosition()[0];
		int lowerY = player.getPosition()[1];
		int higherX = lowerX + player.getWidth();
		int higherY = lowerY + player.getHeight();
		if (circular) {
			int radius = object.getRadius();
			int lcX = object.getX()-radius;
			int hcX = object.getX()+radius;
			int lcY = object.getY()-radius;
			int hcY = object.getY()+radius;
			
			if((hcY > lowerY && lcY < lowerY) || (hcY > higherY && lcY < higherY)) {
				if ((hcX > lowerX && lcX < lowerX) || (hcX > higherX && lcX < higherX)) {
					if (object instanceof Saw || object instanceof Projectile) {
						player.die(); //Kills player if hits harmful object
						return true;
					}
				}
			}
			
			
		} else {
			int itemLX = object.getX();
			int itemHX = object.getX() + object.getWidth();
			int itemLY = object.getY();
			int itemHY = object.getY() + object.getHeight(); //-5 to allow for normal collision if the objects are only intertwined within a set margin
			//if (lowerY < itemHY && lowerY > itemLY && higherY > itemHY && ((lowerX <= itemLX && higherX <= itemHX && higherX >= itemLX) || (itemLX <= lowerX && itemHX >= higherX && lowerX < itemHX))) {
			if (((itemLY <= lowerY && itemHY >= higherY) || (lowerY <= itemLY && higherY >= itemLY && higherY-itemLY >= 5) || (lowerY <= itemHY && higherY >= itemHY))){
				if (object instanceof Platform || object instanceof CharacterLauncher || object instanceof ConveyorBelt) {
					if (lowerX <= itemLX && higherX >= itemLX && player.getVelocity()[0] >= 0 && Math.abs(lowerY - itemHY) > 4) { // 4 is used as a restriction for collision
						player.setPosition(itemLX-player.getWidth(), player.getPosition()[1]);
						return true;
					} else if (lowerX <= itemHX && higherX >= itemHX && player.getVelocity()[0] <= 0 && Math.abs(lowerY - itemHY) > 4){
						player.setPosition(itemHX, player.getPosition()[1]);
						return true;
					}
				}
			}
			if ((itemLX <= lowerX && itemHX >= higherX) || (itemLX >= lowerX && itemLX <= higherX) || (itemLX <= lowerX && itemHX >= lowerX)) {
				if (object instanceof Platform || object instanceof CharacterLauncher || object instanceof ConveyorBelt) {
					if (lowerY - itemHY <= 0 && lowerY - itemHY >= -5) {

						if (player.getFWM() == false) {
							System.out.println("BAD");
							player.setVelocity(new double[] {player.getVelocity()[0], player.getVelocity()[1]+5});
						}
						player.setPosition(player.getPosition()[0], itemHY);
						
						return true;
					}
				}
				if ((itemLY <= higherY) && (higherY <= itemHY)){
					if (object instanceof Platform) {
						player.resetY();
						player.setJump(true);
						if (object instanceof MovingPlatform) {
							if (object.checkChar(player.getTag()) == false) {
								player.setPMotion((int)object.getVel()[0]);
								player.setVelocity(new double[] {object.getVel()[0] + player.getVelocity()[0], player.getVelocity()[1]});
							} else if (player.getPMotion() != object.getVel()[0]) { // For when the velocity of moving platform switches
								player.setPMotion((int)object.getVel()[0]);
								player.setVelocity(new double[] {2*object.getVel()[0] + player.getVelocity()[0], player.getVelocity()[1]});
							}
						}
						if (((Platform)object).getHoney() == true) {
							if (player.getHoney() == false) {
								player.setHoney(true);
							} //Reverse effect if no intersection occurs (bottom)
						} else if (((Platform)object).getIce() == true) {
							if (player.getIce() == false) {
								player.setIce(true);
							}
						} 
						if (object.checkChar(player.getTag()) == false){
							object.addChar(player.getTag());
						}
						hasCollided = true;
					} else if (object instanceof CharacterLauncher) {
						player.resetY();
						//Player has already been reset (movement)
						if (object.checkChar(player.getTag()) == false) {
							if (inMapTag(player.getTag())) {
								tagMap.remove(Integer.valueOf(player.getTag()));
							}
							object.addChar(player.getTag());
						}
						((CharacterLauncher) object).launchChar(player);
						hasCollided = true;
					} else if (object instanceof ConveyorBelt) {
						player.setJump(true);
						player.resetY();
						if (object.checkChar(player.getTag()) == false) {
							if (inMapTag(player.getTag())) {
								tagMap.remove(Integer.valueOf(player.getTag()));
							}
							player.setVelocity(new double[] {player.getVelocity()[0]+(((VelocityModifier)object).getSpeed())[0], player.getVelocity()[1] - (((VelocityModifier)object).getSpeed())[1]});
							object.addChar(player.getTag());
						}
						hasCollided = true;
					}
					
				}
				
				if ((itemHY >= higherY && itemLY <= higherY) || (itemLY <= higherY && itemHY >= lowerY) || (itemHY <= higherY && itemLY >= lowerY)) {
					
					if (object instanceof Projectile) {
						player.die();
						return true;
					}
					
					if (object instanceof Spike) {
						player.die();
						return true;
					}
					
					if (object instanceof FanWind) {
						if (inMapTag(player.getTag())) {
							player.setFWM(true);
							tagMap.remove(Integer.valueOf(player.getTag()));
						}
						player.resetY();
						if (object.checkChar(player.getTag()) == false || player.getVelocity()[1] == 0) {
							player.setVelocity(new double[] {player.getVelocity()[0]+(((VelocityModifier)object).getSpeed())[0], player.getVelocity()[1] - (((VelocityModifier)object).getSpeed())[1]});
							object.addChar(player.getTag());
						}
						hasCollided = true;
					}		
				}
			}
			
			if (hasCollided) {
				return true;
			}
			
			if (object.checkChar(player.getTag())) {
				if (object instanceof ConveyorBelt) {
					player.setJump(false);
					player.setVelocity(new double[]{player.getVelocity()[0] - ((ConveyorBelt)object).getSpeed()[0], player.getVelocity()[1]});
				}
				if (object instanceof FanWind) {
					player.setFWM(false);
					player.setVelocity(new double[]{player.getVelocity()[0], player.getVelocity()[1] + ((FanWind)object).getSpeed()[1]});
				}
				
				if (object instanceof Platform) {
					player.setJump(false);
					if (((Platform)object).getHoney()) {
						player.setHoney(false);
					}
					if (((Platform)object).getIce() == true) {
						player.setIce(false);
						if(player.getIMotion()) {
	    					player.setVelocity(new double[] {player.getVelocity()[0]-player.getLastI() , player.getVelocity()[1]});
	    					player.setIMotion(false);
	    				}
					}
					if (object instanceof MovingPlatform) {
						player.setVelocity(new double[] {player.getVelocity()[0] - object.getVel()[0], player.getVelocity()[1]});
					}
				}

				player.resetGravity();
				object.removeChar(player.getTag());
			}
		}
		
		return false;
	}

	/**
	 * checkCollision
	 * 
	 * Item x Item collision checking system
	 * 
	 * @param object1
	 * @param object2
	 * @param circular1
	 * @param circular2
	 * @return
	 */
	
	public static boolean checkCollision(Item object1, Item object2, boolean circular1, boolean circular2) {
		int centerLX, centerHX, centerLY, centerHY, otherLX, otherHX, otherLY, otherHY;
		if (circular1 && circular2) { //Per case variable sets
			centerLX = object1.getX()-object1.getRadius();
			centerHX = object1.getX()+object1.getRadius();
			centerLY = object1.getY()-object1.getRadius();
			centerHY = object1.getY()+object1.getRadius();
			otherLX = object2.getX()-object2.getRadius();
			otherHX = object2.getX()+object2.getRadius();
			otherLY = object2.getY()-object2.getRadius();
			otherHY = object2.getY()+object2.getRadius();
		} else if (circular1) {
			centerLX = object1.getX()-object1.getRadius();
			centerHX = object1.getX()+object1.getRadius();
			centerLY = object1.getY()-object1.getRadius();
			centerHY = object1.getY()+object1.getRadius();
			otherLX = object2.getX();
			otherHX = object2.getX()+object2.getWidth();
			otherLY = object2.getY();
			otherHY = object2.getY()+object2.getHeight();
		} else if (circular2) {
			centerLX = object1.getX();
			centerHX = object1.getX()+object1.getWidth();
			centerLY = object1.getY();
			centerHY = object1.getY()+object1.getHeight();
			otherLX = object2.getX()-object2.getRadius();
			otherHX = object2.getX()+object2.getRadius();
			otherLY = object2.getY()-object2.getRadius();
			otherHY = object2.getY()+object2.getRadius();
		} else {
			centerLX = object1.getX();
			centerHX = object1.getX()+object1.getWidth();
			centerLY = object1.getY();
			centerHY = object1.getY()+object1.getHeight();
			otherLX = object2.getX();
			otherHX = object2.getX()+object2.getWidth();
			otherLY = object2.getY();
			otherHY = object2.getY()+object2.getHeight();
		}
		/*
		if ((centerLX < otherHX && otherLX < centerLX) || (centerHX < otherHX && otherLX < centerHX)) {
			if ((otherLY < centerHY && otherHY > centerLX) || (otherLY < centerHY && otherHY > centerHY)) {
				return true;
			}
		}
		return false;
		*/
		if ((otherLX < centerLX && otherHX > centerHX) || (otherLX < centerHX && otherLX > centerLX) || (otherLX > centerLX && otherHX < centerHX) || (centerLX < otherHX && otherHX < centerHX)) {
			if ((otherHY > centerHY && otherLY < centerHY) || (otherLY < centerHY && otherHY > centerLY) || (otherHY < centerHY && otherLY > centerLY) || (otherHY < centerHY && otherHY > centerLY)) {
				return true;
			}
		}
		return false;

	}

	/**
	 * checkBombCollision
	 * 
	 * Method used in ItemMovement class - detects whether or not bomb object intersects with an item, boolean determines if item is removed or not.
	 * 
	 * @param object1
	 * @param object2
	 * @param circular1
	 * @return
	 */
	
	public static boolean checkBombCollision(Item object1, Item object2, boolean circular1) { //Item 2 is the bomb object
		int centerLX, centerHX, centerLY, centerHY, otherLX, otherHX, otherLY, otherHY;
		if (circular1) {
			centerLX = object1.getX()-object1.getRadius();
			centerHX = object1.getX()+object1.getRadius();
			centerLY = object1.getY()-object1.getRadius();
			centerHY = object1.getY()+object1.getRadius();
			otherLX = object2.getX()-((Bomb)object2).getEffectRadius();
			otherHX = object2.getX()+((Bomb)object2).getEffectRadius();
			otherLY = object2.getY()-((Bomb)object2).getEffectRadius();
			otherHY = object2.getY()+((Bomb)object2).getEffectRadius();
		} else {
			centerLX = object1.getX();
			centerHX = object1.getX()+object1.getWidth();
			centerLY = object1.getY();
			centerHY = object1.getY()+object1.getHeight();
			otherLX = object2.getX()-((Bomb)object2).getEffectRadius();
			otherHX = object2.getX()+((Bomb)object2).getEffectRadius();
			otherLY = object2.getY()-((Bomb)object2).getEffectRadius();
			otherHY = object2.getY()+((Bomb)object2).getEffectRadius();
		}

		if ((otherLX < centerLX && otherHX > centerHX) || (otherHX < centerHX && otherLX > centerLX) || (otherLX > centerLX && otherHX < centerHX)) {
			if ((otherHY > centerHY && otherLY < centerHY) || (otherLY < centerHY && otherHY > centerLY) || (otherHY < centerHY && otherLY > centerLY)) {
				return true;
			}
		}
		return false;
	}
	
}