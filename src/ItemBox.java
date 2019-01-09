import java.utiil.Random;

public class ItemBox {

	private SimpleLinkedList<Item> items = new SimpleLinkedList<Item>();
	
	public ItemBox(SimpleLinkedList<Item> items) {
		this.items = items;
	}
	
	public ItemBox(int numItems, int numPlayers) {
		
		Random randomNum = new Random();
		int randInt = 0;
		
		// Random + bomb
		// number of players + 2
		// Randomize items
		for (int i = 0; i < numPlayers; i++) {
			randInt = randomNum.nextInt();
		}
	}
	
	public Item removeItem(Item itemRemoved) {
		items.remove(itemRemoved);
		return itemRemoved;
	}
	
	public void addItem(Item itemAdded) {
		items.add(itemAdded);
	}
	
	public void emptyBox() {
		items.clear();
	}
	
}
