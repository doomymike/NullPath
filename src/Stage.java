/**
 * [Stage.java]
 * Stage object for NullPath game
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

//java imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Stage {

    private BufferedImage sprite; //image for stage
    private SimpleLinkedList<Item> items; //List of items held in stage
    private SimpleLinkedList<Character> characters; //List of characters held in stage
    private ItemBox itemBox; //Item box held in stage
    
    private String[][] skyFortressMapCollision = new String[38][86]; //Stores collision map for the stage
	private BufferedImage skyFortressImage; //Picture for the stage

    /**
     * Stage
     * Constructor that makes a stage object with an image as a parameter
     * @param sprite
     */
    public Stage(BufferedImage sprite) {
        this.sprite = sprite;
        this.items = new SimpleLinkedList<Item>();
    }
    
    public Stage(String stageName) {
    	mapInit(stageName);
        this.items = new SimpleLinkedList<Item>();
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
    
    public String[][] getCollisionMap() {
    	return skyFortressMapCollision;
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
			Scanner input = new Scanner(new File("/resources/SkyFortressCollision.txt")); //idk if pathname is correct
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
