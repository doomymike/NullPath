import java.awt.Rectangle;

public class PhysicsEngine {
	
	String [][] contactMap = null;
	
	PhysicsEngine(String[][] contactMap){
		this.contactMap = contactMap;
	}
	
	PhysicsEngine(){
	}
	
	public void move(Character character){
		int xPos = character.getPosition()[0];
		int yPos = character.getPosition()[1];
				
		if (character.getGravity()) {
			character.setVelocity(new double[] {character.getVelocity()[0], character.getVelocity()[1]+0.06}); //Not using getGravityV to sequentially decrease dy
		}
		//System.out.println(player.getVelocity()[0] + " X_vel" + player.getVelocity()[1] + " Y_vel");
		character.setPosition((int)Math.round(xPos + character.getVelocity()[0]), (int)Math.round(yPos + character.getVelocity()[1]));
		
		// Determine action performed by character
		if (character.getVelocity()[1] == 0) { // if character has no vertical component of velocity
			if (character.getVelocity()[0] == 0) { // if character is not moving at all
				character.setCurrentAction("idle");
				if ((character.getCurrentFrameIndex() < 8) || (character.getCurrentFrameIndex() > 12)) {
					character.setCurrentFrameIndex(8);
				} else {
					character.setCurrentFrameIndex(character.getCurrentFrameIndex()+1);
				}
			} else if (character.getVelocity()[0] < 0) { // if character is moving left on some surface (not fall/jump)
				character.setCurrentAction("run");
				if (character.getDirectionFacing().equals("right")) { // change direction to left (and frame to first frame of run)
					character.setDirectionFacing("left");
					character.setCurrentFrameIndex(15);
				} else {
					if ((character.getCurrentFrameIndex() > 14) || (character.getCurrentFrameIndex() < 22)) {
						character.setCurrentFrameIndex(character.getCurrentFrameIndex()+1);
					} else {
						character.setCurrentFrameIndex(15);
					}
				}
			} else { // if character is moving right on some surface (not fall/jump)
				character.setCurrentAction("run");
				if (character.getDirectionFacing().equals("left")) { // change direction to right (and frame to first frame of run)
					character.setDirectionFacing("right");
					character.setCurrentFrameIndex(15);
				} else {
					if ((character.getCurrentFrameIndex() > 14) || (character.getCurrentFrameIndex() < 22)) {
						character.setCurrentFrameIndex(character.getCurrentFrameIndex()+1);
					} else {
						character.setCurrentFrameIndex(15);
					}
				}
			}
		} else if (character.getVelocity()[1] < 0) { // Falling
			character.setCurrentFrameIndex(7); // set to fall frame index
			character.setCurrentAction("fall"); // set to fall action
			if (character.getVelocity()[0] < 0) { // if character is falling left
				if (character.getDirectionFacing().equals("right")) { // change direction to left
					character.setDirectionFacing("left");
				}
			} else { // if character is falling right
				if (character.getDirectionFacing().equals("left")) { // change direction to right
					character.setDirectionFacing("right");
				}
			}
		} else {
			character.setCurrentFrameIndex(14); // set to jump frame index
			character.setCurrentAction("jump"); // set to jump action
			if (character.getVelocity()[0] < 0) { // if character is jumping left
				if (character.getDirectionFacing().equals("right")) { // change direction to left
					character.setDirectionFacing("left");
				}
			} else { // if character is jumping right
				if (character.getDirectionFacing().equals("left")) { // change direction to right
					character.setDirectionFacing("right");
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
	
	public static boolean checkCollision(Character character, Item object, boolean circular) {
		int lowerX = character.getPosition()[0];
		int lowerY = character.getPosition()[1];
		int higherX = lowerX + character.getWidth();
		int higherY = lowerY + character.getHeight();
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
					
					if (object instanceof ContactDamage||object instanceof Projectile){
						character.die();
					}
					
					if (object instanceof Platform) {
						character.resetY();

						if (((Platform)object).getHoney() == true) {
							System.out.println("BAD");
							if (character.getHoney() == false) {
								//player.setVelocity(honeyMod(player, player.getVelocity()));
								character.setHoney(true);
								//object.addChar(player.getTag());
							} //Reverse effect if no intersection occurs (bottom)
						} else if (((Platform)object).getIce() == true) {
							if (character.getIce() == false) {
								character.setIce(true);
							}
						} 
						if (object.checkChar(character.getTag()) == false){
							System.out.println("ON");
							object.addChar(character.getTag());
						}
						return true;
					} else if (object instanceof CharacterLauncher) {
						character.resetY();
						//Player has already been reset (movement)
						if (object.checkChar(character.getTag()) == false) {
							object.addChar(character.getTag());
						}
						((CharacterLauncher) object).launchChar(character);
						return true;
					} else if (object instanceof ConveyorBelt) {
						if (object.checkChar(character.getTag()) == false) {
							character.resetY();
							character.setVelocity(new double[] {character.getVelocity()[0]+(((VelocityModifier)object).getSpeed())[0], character.getVelocity()[1] - (((VelocityModifier)object).getSpeed())[1]});
							object.addChar(character.getTag());
						}
						return true;
					}
					
					
				}
				if ((itemHY >= higherY && itemLY <= higherY) || (itemLY <= higherY && itemHY >= lowerY) || (itemHY <= higherY && itemLY >= lowerY)) {
					if (object instanceof VelocityModifier && !(object instanceof ConveyorBelt)) {
						if (object.checkChar(character.getTag()) == false) {
							character.resetY();
							character.setVelocity(new double[] {character.getVelocity()[0]+(((VelocityModifier)object).getSpeed())[0], character.getVelocity()[1] - (((VelocityModifier)object).getSpeed())[1]});
							object.addChar(character.getTag());
						}
					}		
					return true;
				}
			}
			
			if (object.checkChar(character.getTag())) {
				if (object instanceof ConveyorBelt) {
					character.setVelocity(new double[]{character.getVelocity()[0] - ((ConveyorBelt)object).getSpeed()[0], character.getVelocity()[1]});
				}
				if (object instanceof FanWind) {
					character.setVelocity(new double[]{character.getVelocity()[0], character.getVelocity()[1] + ((FanWind)object).getSpeed()[1]});
				}
				
				if (object instanceof Platform) {
					if (((Platform)object).getHoney()) {
						character.setHoney(false);
					}
					if (((Platform)object).getIce() == true) {
						character.setIce(false);
					}
				}
				character.resetGravity();
				System.out.println("STAPP");
				object.removeChar(character.getTag());
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
