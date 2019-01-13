// Items stored as string (for their names) - spelled exactly same as class names

import java.util.Random;

public class ItemBox {

	private SimpleLinkedList<String> items = new SimpleLinkedList<>();
	
	public ItemBox(SimpleLinkedList<String> items) {
		this.items = items;
	}
	
	public ItemBox(int numItems, int numPlayers) {
		
		Random randomNum = new Random();
		int randInt = 0;

		// Add 1 'bomb' (String) to the box
		items.add("Bomb");
		
		// Random + bomb (always only 1 bomb)
		// number of players + 2
		// Randomize items
		for (int i = 0; i < numPlayers + 1; i++) {
			randInt = randomNum.nextInt(9); // if 10 items? idk how many there are
			if (randInt == 0) {
				items.add("ConveyorBelt");
			} else if (randInt == 1) {
				items.add("CharacterLauncher");
			} else if (randInt == 2) {
				items.add("Fan");
			} else if (randInt == 3) {
				items.add("MovingPlatform");
			} else if (randInt == 4) {
				items.add("Platform");
			} else if (randInt == 5) {
				items.add("Saw");
			} else if (randInt == 6) {
				items.add("Spike");
			} else if (randInt == 7) {

			} else if (randInt == 8) {

			} else if (randInt == 9) {

			}
		}
	}
	
	public String removeItem(String itemRemoved) {
		items.remove(itemRemoved);
		return itemRemoved;
	}
	
	public void addItem(String itemAdded) {
		items.add(itemAdded);
	}
	
	public void emptyBox() {
		items.clear();
	}
	
}
