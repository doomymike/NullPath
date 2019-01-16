import java.awt.Rectangle;

public class PhysicsEngine {
	
	String [][] contactMap = null;
	
	PhysicsEngine(String[][] contactMap){
		this.contactMap = contactMap;
	}
	
	PhysicsEngine(){
	}
	
	public void move(Character player){
		int xPos = player.getPosition()[0];
		int yPos = player.getPosition()[1];
				
		if (player.getGravity()) {
			player.setVelocity(new double[] {player.getVelocity()[0], player.getVelocity()[1]+0.06}); //Not using getGravityV to sequentially decrease dy
		}
		//System.out.println(player.getVelocity()[0] + " X_vel" + player.getVelocity()[1] + " Y_vel");
		player.setPosition((int)Math.round(xPos + player.getVelocity()[0]), (int)Math.round(yPos + player.getVelocity()[1]));
		
		// Determine action performed by character
		if (player.getVelocity()[1] == 0) { // if character has no vertical component of velocity
			if (player.getVelocity()[0] == 0) { // if character is not moving at all
				player.setCurrentAction("idle");
			} else if (player.getVelocity()[0] < 0) { // if character is moving left on some surface (not fall/jump)
				player.setCurrentAction("run");
				if (player.getDirectionFacing().equals("right")) {
					player.setDirectionFacing("left");
				}
			} else { // if character is moving right on some surface (not fall/jump)
				player.setCurrentAction("run");
				if (player.getDirectionFacing().equals("left")) {
					player.setDirectionFacing("right");
				}
			}
		} else if (player.getVelocity()[1] < 0) { // Falling
			if (player.getVelocity()[0] < 0) { // if character is falling left
				player.setCurrentAction("fall");
				if (player.getDirectionFacing().equals("right")) {
					player.setDirectionFacing("left");
				}
			} else { // if character is falling right (or straight down)
				player.setCurrentAction("fall");
				if (player.getDirectionFacing().equals("left")) {
					player.setDirectionFacing("right");
				}
			}
		} else {
			if (player.getVelocity()[0] < 0) { // if character is jumping left
				player.setCurrentAction("jump");
				if (player.getDirectionFacing().equals("right")) {
					player.setDirectionFacing("left");
				}
			} else { // if character is jumping right
				player.setCurrentAction("jump");
				if (player.getDirectionFacing().equals("left")) {
					player.setDirectionFacing("right");
				}
			}
		}
		
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
	
	public static boolean checkCollision(Character player, Item object, boolean circular) {
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
			if ((itemLX <= lowerX && itemHX >= higherX) || (itemLX >= lowerX && itemLX <= higherX) || (itemLX <= lowerX && itemHX >= lowerX)) {
				if ((lowerY <= itemLY) && (higherY >= itemLY)) {
					if (object instanceof Platform) {
						player.resetY();

						if (((Platform)object).getHoney() == true) {
							System.out.println("BAD");
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
							System.out.println("ON");
							object.addChar(player.getTag());
						}
						return true;
					} else if (object instanceof CharacterLauncher) {
						player.resetY();
						//Player has already been reset (movement)
						if (object.checkChar(player.getTag()) == false) {
							object.addChar(player.getTag());
						}
						((CharacterLauncher) object).launchChar(player);
						return true;
					} else if (object instanceof ConveyorBelt) {
						if (object.checkChar(player.getTag()) == false) {
							player.resetY();
							player.setVelocity(new double[] {player.getVelocity()[0]+(((VelocityModifier)object).getSpeed())[0], player.getVelocity()[1] - (((VelocityModifier)object).getSpeed())[1]});
							object.addChar(player.getTag());
						}
						return true;
					}
					
					
				}
				if ((itemHY >= higherY && itemLY <= higherY) || (itemLY <= higherY && itemHY >= lowerY) || (itemHY <= higherY && itemLY >= lowerY)) {
					if (object instanceof VelocityModifier && !(object instanceof ConveyorBelt)) {
						if (object.checkChar(player.getTag()) == false) {
							player.resetY();
							player.setVelocity(new double[] {player.getVelocity()[0]+(((VelocityModifier)object).getSpeed())[0], player.getVelocity()[1] - (((VelocityModifier)object).getSpeed())[1]});
							object.addChar(player.getTag());
						}
					}		
					return true;
				}
			}
			
			if (object.checkChar(player.getTag())) {
				if (object instanceof ConveyorBelt) {
					player.setVelocity(new double[]{player.getVelocity()[0] - ((ConveyorBelt)object).getSpeed()[0], player.getVelocity()[1]});
				}
				if (object instanceof FanWind) {
					player.setVelocity(new double[]{player.getVelocity()[0], player.getVelocity()[1] + ((FanWind)object).getSpeed()[1]});
				}
				
				if (object instanceof Platform) {
					if (((Platform)object).getHoney()) {
						player.setHoney(false);
					}
					if (((Platform)object).getIce() == true) {
						player.setIce(false);
					}
				}
				player.resetGravity();
				System.out.println("STAPP");
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
