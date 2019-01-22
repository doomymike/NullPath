
/**
 * [ItemBox.java]
 * ItemBox object for NullPath
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */
// Items stored as string (for their names) - spelled exactly same as class names
import java.util.Random;

public class ItemBox {

	private SimpleLinkedList<String> items = new SimpleLinkedList<>();
	
	public ItemBox(SimpleLinkedList<String> items) {
		this.items = items;
	}
	
	/**
	 * ItemBox
	 * constructor, creates itembox from num players
	 * @param numPlayers
	 */
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
					tempItem = "FanWind";
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

	/**
	 * foundInBox
	 * returns whether or not an item is in the box
	 * @param item
	 * @return boolean
	 */
	private boolean foundInBox(String item) {
		for (int i = 0; i < items.size(); i++) {
			if (item.equals(items.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * removeItem
	 * removes item
	 * @param itemRemoved
	 * @return String itemRemoved
	 */
	public String removeItem(String itemRemoved) {
		items.remove(itemRemoved);
		return itemRemoved;
	}
	
	/**
	 * addItem
	 * adds item
	 * @param itemAdded
	 */
	public void addItem(String itemAdded) {
		items.add(itemAdded);
	}
	
	/**
	 * emptyBox
	 * empties box
	 */
	public void emptyBox() {
		items.clear();
	}
	
	/**
	 * getItems
	 * returns items in box
	 * @return SimpleLinkedList<String> items
	 */
	public SimpleLinkedList<String> getItems(){
		return items;
	}
	
}
