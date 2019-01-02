import java.awt.image.BufferedImage;

public class Stage {

    private BufferedImage sprite;
    private SimpleLinkedList<Item> items;
    private SimpleLinkedList<Character> characters;
    private ItemBox itemBox;

    public Stage(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public SimpleLinkedList<Item> getItems() {
        return items;
    }

    public SimpleLinkedList<Character> getCharacters() {
        return characters;
    }

    public ItemBox getItemBox() {
        return itemBox;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void resetStage() {
        //Remove all projectile stuff, add stuff to item box??
    }

}
