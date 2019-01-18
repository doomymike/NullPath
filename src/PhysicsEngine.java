import java.awt.Rectangle;
import java.util.ArrayList;
import java.io.*;

public class PhysicsEngine{
	
	File file = new File("resources/SkyFortressCollision.txt");
	String [][] contactMap = null;
	ArrayList<Integer> tagMap = new ArrayList<Integer>();	
	
	PhysicsEngine(String read) throws FileNotFoundException, IOException{
		contactMap = new String[38][86];
		BufferedReader br = new BufferedReader(new FileReader(file));
		this.contactMap = contactMap;
		String line;
		int lineCount = -1;
		while ((line = br.readLine()) != null) {
			lineCount += 1;
			for (int i = 0; i < line.length(); i++) {
				System.out.println(line.substring(i,  i+1));
				if (line.substring(i, i+1).equals("1")){
					contactMap[lineCount][i] = "1";
				} else if (line.substring(i,  i+1).equals("2")){
					contactMap[lineCount][i] = "2";
				} else if ((line.substring(i, i+1)).equals("0")) {
					contactMap[lineCount][i] = "0";
				}
			}
		}
	}
	
	PhysicsEngine(){
	}
	
	public String[][] retrieveCMap(){
		return contactMap;
	}
	
	public void printMap(String[][] contactMap) {
		System.out.println(contactMap.length + " "+contactMap[0].length);
		System.out.println("EXEC");
		for (int i = 0; i < contactMap.length; i++) {
			String line = "";
			for (int a = 0; a < contactMap[0].length; a++) {
				line += contactMap[i][a];
			}
			System.out.println(line);
		}
	}
	
	public boolean inMapTag(int uniqueTag) {
		for (int i = 0; i < tagMap.size(); i++) {
			if (tagMap.get(i) == uniqueTag) {
				return true;
			}
		}
		return false;
	}
	
	public void contactMapCollision(Character testPlayer) {
		int contactX = Math.min(Math.max((int)(testPlayer.getPosition()[0])/20, 0), 85);
		int contactY = Math.min(Math.max((int)((testPlayer.getPosition()[1]+testPlayer.getHeight()-20)/20), 0), 37);
		if (contactMap[contactY][contactX].equals("1") && (inMapTag(testPlayer.getTag()) == false || testPlayer.getGravity())) {
			testPlayer.resetY();
			tagMap.add(testPlayer.getTag());
			testPlayer.setJump(true);
		} else if (contactMap[contactY][contactX].equals("2") && (inMapTag(testPlayer.getTag()) == false || testPlayer.getGravity())) {
			testPlayer.resetY();
			tagMap.add(testPlayer.getTag());
			testPlayer.setFinished(true);
		} else if (contactMap[contactY][contactX].equals("0")){
			testPlayer.setJump(false);
			if (inMapTag(testPlayer.getTag())) {
				tagMap.remove(Integer.valueOf(testPlayer.getTag()));
				testPlayer.resetGravity();
			}
		}
	}
	
	public void move(Character player){
		int xPos = player.getPosition()[0];
		int yPos = player.getPosition()[1];
				
		if (player.getGravity()) {
			player.setVelocity(new double[] {player.getVelocity()[0], player.getVelocity()[1]+0.06}); //Not using getGravityV to sequentially decrease dy
		}
		//System.out.println(player.getVelocity()[0] + " X_vel" + player.getVelocity()[1] + " Y_vel");
		player.setPosition((int)Math.round(xPos + player.getVelocity()[0]), (int)Math.round(yPos + player.getVelocity()[1]));
	}
	
	public void move(Item object) {
		int xPos = object.getX();
		int yPos = object.getY();
		if (object.getGravity()) {
			object.setDy(object.getVel()[1] - 10); //Gravity
		}
		object.setX((int)Math.round(xPos + object.getVel()[0]));
		object.setY((int)Math.round(yPos + object.getVel()[1]));
		
	}
	
	public boolean checkCollision(Character player, Item object, boolean circular) {
		boolean hasCollided = false;
		int lowerX = player.getPosition()[0];
		int lowerY = player.getPosition()[1];
		int higherX = lowerX + player.getWidth();
		int higherY = lowerY + player.getHeight();
		if (circular) {
			int radius = object.getRadius();
			int lowCircX = object.getX()-radius;
			int highCircX = object.getX()+radius;
			int lowCircY = object.getY()-radius;
			int highCircY = object.getY()+radius;
			if ((highCircY > higherY && lowCircY < higherY) || (highCircY < higherY && lowCircY > lowerY) || (lowCircY < lowerY && highCircY > lowerY)) {
				if ((highCircX > higherX && lowCircX < higherX) || (highCircX < higherX && lowCircX > lowerX) || (lowCircX < lowerX && highCircY > lowerX)) {
					return true;
				}
			}
		} else {
			int itemLX = object.getX();
			int itemHX = object.getX() + object.getWidth();
			int itemLY = object.getY();
			int itemHY = object.getY() + object.getHeight();
			if ((itemLY <= lowerY && itemHY >= higherY) || (lowerY <= itemLY && higherY >= itemLY) || (lowerY <= itemHY && higherY >= itemHY)){
				if (object instanceof Platform || object instanceof CharacterLauncher || object instanceof ConveyorBelt) {
					if (lowerX <= itemLX && higherX >= itemLX && player.getVelocity()[0] >= 0) {
						player.setPosition(itemLX-player.getWidth(), player.getPosition()[1]);
						player.resetGravity();
						return true;
					} else if (lowerX <= itemHX && higherX >= itemHX && player.getVelocity()[0] <= 0) {
						player.setPosition(itemHX, player.getPosition()[1]);
						player.resetGravity();
						return true;
					}
				}
			}
			if ((itemLX <= lowerX && itemHX >= higherX) || (itemLX >= lowerX && itemLX <= higherX) || (itemLX <= lowerX && itemHX >= lowerX)) {
				if (object instanceof Platform || object instanceof CharacterLauncher || object instanceof ConveyorBelt) {
					if (lowerY < itemHY && higherY > itemHY) {
						//player.setVelocity(new double[] {player.getVelocity()[0], player.getVelocity()[1]+5});
						player.setPosition(player.getPosition()[0], itemHY);
						return true;
					}
				}
				if ((itemLY <= higherY) && (higherY <= itemHY)){
					if (object instanceof Platform) {
						player.resetY();
						player.setJump(true);
						if (((Platform)object).getHoney() == true) {
							if (player.getHoney() == false) {
								//player.setVelocity(honeyMod(player, player.getVelocity()));
								player.setHoney(true);
								//object.addChar(player.getTag());
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
					if (inMapTag(player.getTag())) {
						tagMap.remove(Integer.valueOf(player.getTag()));
					}
					if (object instanceof VelocityModifier && !(object instanceof ConveyorBelt)) {
						player.resetY();
						if (object.checkChar(player.getTag()) == false) {
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
				}
				player.resetGravity();
				System.out.println("END");
				object.removeChar(player.getTag());
			}
		}
		
		return false;
	}

	/*
	public static double[] honeyMod(Character player, double [] vel) {
		double dx = vel[0];
		double dy = vel[1];
		
		double new_dx = dx - Integer.signum((int)dx);
		double new_dy = dy - Integer.signum((int)dy)*3;
		
		if (Integer.signum((int)new_dx) != Integer.signum((int)dx)) {
			new_dx = 0;
		}
		
		if (Integer.signum((int)new_dy) != Integer.signum((int)dy)) {
			new_dy = 0;
		}
		
		if (new_dx == 0 && new_dy == 0) {
			player.setHoney(false);
		}
		
		System.out.println(dx+" "+dy+" NEW: "+new_dx+" "+new_dy);
		return new double[] {new_dx, new_dy};
	}
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
		
		if ((otherLX < centerLX && otherHX > centerHX) || (otherHX < centerHX && otherLX > centerLX) || (otherLX > centerLX && otherHX < centerHX)) {
			if ((otherHY > centerHY && otherLY < centerHY) || (otherLY < centerHY && otherHY > centerLY) || (otherHY < centerHY && otherLY > centerLY)) {
				return true;
			}
		}
		return false;
	}

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
