import java.awt.Rectangle;

public class PhysicsEngine {
	
	public void move(Character player){
		int xPos = player.getPosition()[0];
		int yPos = player.getPosition()[1];
		player.setPosition((int)Math.round(xPos + player.getVelocity()[0]), (int)Math.round(yPos + player.getVelocity()[1]));
		if (player.getGravity()) {
			player.setVelocity(new double[] {player.getVelocity()[0], player.getVelocity()[1] - 0.5});
		}
	}
	
	public void move(Item object) {
		int xPos = object.getX();
		int yPos = object.getY();
		object.setX((int)Math.round(xPos + object.getVel()[0]));
		object.setY((int)Math.round(yPos + object.getVel()[1]));
		
		if (object.getGravity()) {
			object.setDy(object.getVel()[1] - 0.5); //Gravity
		}
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
			if ((itemLX < lowerX && itemHX > higherX) || (itemHX < higherX && itemLX > lowerX) || (itemLX > lowerX && itemHX < higherX)) {
				if ((itemHY > higherY && itemLY < higherY) || (itemLY < higherY && itemHY > lowerY) || (itemHY < higherY && itemLY > lowerY)) {
					return true;
				}
			}
		}
		
		return false;	
	}
	
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
