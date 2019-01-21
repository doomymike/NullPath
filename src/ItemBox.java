// Items stored as string (for their names) - spelled exactly same as class names

import java.util.Random;

public class ItemBox {

	private SimpleLinkedList<String> items = new SimpleLinkedList<>();
	
	public ItemBox(SimpleLinkedList<String> items) {
		this.items = items;
	}
	
	public ItemBox(int numPlayers) {
		
		Random randomNum = new Random();
		int randInt = 0;
		String tempItem = "";

		// Add 1 'bomb' (String) to the box
		items.add("Bomb");
		
		// 6 items total - 5 others to be added besides bomb
		for (int i = 0; i < numPlayers + 1; i++) {
			do {
				randInt = randomNum.nextInt(5);
				if (randInt == 0) {
					tempItem = "ConveyorBelt";
				} else if (randInt == 1) {
					tempItem = "CharacterLauncher";
				} else if (randInt == 2) {
					tempItem = "Fan";
				} else if (randInt == 3) {
					tempItem = "Platform";
				} else if (randInt == 4) {
					tempItem = "Saw";
				} else if (randInt == 5) {
					tempItem = "Spike";
				}
			} while (foundInBox(tempItem));
			items.add(tempItem);
		}
	}

	private boolean foundInBox(String item) {
		for (int i = 0; i < items.size(); i++) {
			if (item.equals(items.get(i))) {
				return true;
			}
		}
		return false;
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
	
	public SimpleLinkedList<String> getItems(){
		return items;
	}
	
}
