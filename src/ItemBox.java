
public class ItemBox {

	private SimpleLinkedList<Item> items = new SimpleLinkedList<Item>();
	
	public ItemBox(SimpleLinkedList<Item> items) {
		this.items = items;
	}
	
	public ItemBox(int numItems) {
		//Randomize items
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
