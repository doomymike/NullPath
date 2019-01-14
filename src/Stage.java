import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Stage {

    private BufferedImage sprite;
    private SimpleLinkedList<Item> items;
    private SimpleLinkedList<Character> characters;
    private ItemBox itemBox;
    
    private String[][] skyFortressMapCollision = new String[36][86];
	private BufferedImage skyFortressImage;

    public Stage(BufferedImage sprite) {
        this.sprite = sprite;
    }
    
    public Stage(String stageName) {
    	
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
    
    public boolean itemIntersect(Item item){
    	for (int i = 0; i < items.size(); i++) {
                       	
            if (PhysicsEngine.checkCollision(item, items.get(i), (item.getRadius()!=0), (items.get(i).getRadius()!=0))){
            	return true;
            }
                                 
        }
    	return false;
    }
    
    private void mapInit(String mapName) {
    	try {
			skyFortressImage = ImageIO.read(new File("/resources/SkyFortress.png")); //idk if this is right pathname
		} catch (IOException e) {

		}
		try {
			Scanner input = new Scanner(new File("/resources/SkyFortressCollision")); //idk if pathname is correct
			input.close();
			String line;
			int index = 0;
			if (mapName.equals("SkyFortress")) {
				while (input.hasNext()) {
					line = input.nextLine();
					for (int i = 0; i < 86; i++) {
						skyFortressMapCollision[index][i] = line.substring(i,i+1);
					}
					index++;
				}
			}
		} catch(IOException e) {

		}
	}
    
}
