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
        if (!(item instanceof Bomb)) {
            items.add(item); //"Place" item on stage
        } else { //If bomb, remove items within the vicinity
            for (int i = 0; i < items.size(); i++) {
                if (i < items.size()) { //Check because items may be removed in loop
                    if (items.get(i).getRadius() == 0) { //For objects with rectangular hitboxes
                        if (PhysicsEngine.checkBombCollision(items.get(i),item,false)) {
                            items.remove(i);
                            //Do some animation for item removal?
                        }
                    } else { //For items with circular hitboxes
                        if (PhysicsEngine.checkBombCollision(items.get(i),item,true)) {
                            items.remove(i);
                            //Do some animation for item removal?
                        }
                    }
                }
            }
        }
        //one issue: if objects are displayed in repaint, bombs will never be shown as placed
        //Figure out how to animate (or show) bomb going off
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void resetStage() {
        //Remove all projectile stuff, add stuff to item box??
    }

}
