/**
 * [ItemMovePanel.java]
 * Panel that allows for item placement before a round starts in NullPath
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 21, 2019
 */

//java imports
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;

public class ItemMovePanel extends JPanel implements KeyListener, MouseListener{

	ArrayList<Item> itemList = new ArrayList<Item>();
	Item currentItem;
	int xPos;
	int yPos;
	int objC;
	boolean bombMark;
	String[][] cMap;
	PhysicsEngine physEng;
	Stage cStage;
	boolean done;
	private int influencePlayer; //Stores index of player in resources.getPlayers()
	private GameClient client;
	private int loc; //Stores index corresponding to character that player chose (used for client)
	private boolean[] placed = new boolean[4]; //Stores which players have placed items already - corresponds with loc
	private Resources resources = null;
	
	/**
	 * ItemMovePanel
	 * Constructor to make an item move panel
	 * @param usedResource, Resources object for game
	 * @param newEng, Physics engine object for game
	 * @param client, GameClient object for game
	 */
	public ItemMovePanel(Resources usedResource, PhysicsEngine newEng, GameClient client) {
		
		this.setSize(new Dimension(1720, 760));
		this.cMap = newEng.retrieveCMap();
		this.physEng = newEng;
		this.cStage = usedResource.getCurrentStage();
		this.client =client;
		this.resources = usedResource;

		//Determine index corresponding to character that player chose (used for client)
		if(client.getCharacterSelected().equals("Blue")){
			loc = 0;
		}else if(client.getCharacterSelected().equals("Green")) {
			loc = 1;
		}else if(client.getCharacterSelected().equals("Red")) {
			loc = 2;
		}else {
			loc = 3;
		}
	
		xPos = 0;
		yPos = 0;
		objC = 0;
		bombMark = false;
		this.done = false;
		
		setFocusable(true);
		requestFocusInWindow();
        setVisible (true);
		addKeyListener(this);
		addMouseListener(this);
	}
	
	/**
	 * paintComponent
	 * Runs to paint panel
	 * @param g, Graphics object
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
	    setDoubleBuffered(true);

	    try {
			g.drawImage(ImageIO.read(new File("resources/SkyFortress.png")), 0, 0, 1720, 760, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		for (int i = 0; i < itemList.size(); i++) {
			g.fillRect(itemList.get(i).getX(), itemList.get(i).getY(), itemList.get(i).getWidth(), itemList.get(i).getHeight());
		}

		//Check for any player input with client (and draw/do action if applicable)
		for (int i = 0; i < 4; i++) {
			if (!placed[i]) { //Read from client if not already registered a player's item placement
				Player tempPlayer = null;
				for (int j = 0; j < 4; j++) {
					if ((resources != null) && (client != null)) {
						if ((resources.getPlayers() != null) && (client.getCharacterSelection() != null)) {
							if (resources.getPlayers().get(j).getName().equals(client.getCharacterSelection()[i])) { //Find player object that placed item
								tempPlayer = resources.getPlayers().get(j);
							}
						}
					}
				}
				if (client.getAllItemPlacementCoordinates()[i][0] != null) { //If the corresponding player actually placed something

					//Read x, y coordinates of item placed by player from client
					int tempX = Integer.parseInt(client.getAllItemPlacementCoordinates()[i][0]);
					int tempY = Integer.parseInt(client.getAllItemPlacementCoordinates()[i][1]);

					//MAKE TEMP ITEM - using item name
					Item currentItem = null;
					String currentItemName = client.getAllItemPlacementCoordinates()[i][2];

					if (currentItemName.equals("Bomb")) {
						currentItem = new Bomb(tempX, tempY);
						currentItem.setPlacer(tempPlayer);
					} else if (currentItemName.equals("Platform")) {
						currentItem = new StationaryPlatform(tempX, tempY);
						currentItem.setPlacer(tempPlayer);
					} else if (currentItemName.equals("Saw")) {
						currentItem = new Saw(tempX, tempY);
						currentItem.setPlacer(tempPlayer);
					} else if (currentItemName.equals("Spike")) {
						currentItem = new Spike(tempX, tempY);
						currentItem.setPlacer(tempPlayer);
					} else if (currentItemName.equals("Conveyor Belt")) {
						currentItem = new ConveyorBelt(tempX, tempY, 0,0);
						currentItem.setPlacer(tempPlayer);
					} else if (currentItemName.equals("Fan")) {
						currentItem = new FanWind(tempX, tempY, 0, 0);
						currentItem.setPlacer(tempPlayer);
					} else if (currentItemName.equals("CharacterLauncher")) {
						currentItem = new CharacterLauncher(tempX, tempY,0,0);
						currentItem.setPlacer(tempPlayer);
					}

					//Place on map if item
					if (currentItemName.equals("Bomb")) {
						itemList.add(currentItem);
						this.cStage.addItem(currentItem);
					} else { //Do bomb stuff
						int reduceC = 0;
						for (int j = 0; j < itemList.size(); j++) {
							if (PhysicsEngine.checkCollision(currentItem, itemList.get(i - reduceC), true, false)) {
								System.out.println("BAD123");
								itemList.remove(i - reduceC);
								this.cStage.removeItem(currentItem);
								reduceC++;
							}
						}
					}

					//Mark as player having placed an item
					placed[i] = true;

				}
			}
		}
		
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		
		repaint();

	}
	
	public void keyPressed(KeyEvent e) {
		
		char c = e.getKeyChar();
		
		if (c == 'd') {
			System.out.println("B1");
			bombMark = true;
		}
				
		
	}
	
	public void keyReleased(KeyEvent e) {
		
		char c = e.getKeyChar();
		
		if (c == 'd') {
			bombMark = false;
		} else if (c == 'a') {
			done = true;
		}
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent arg0) {

		
	}
	
	public void mouseEntered(MouseEvent arg0) {

		
	}
	
	public void mouseExited1(MouseEvent arg0) {
		
	}
	
	public void mousePressed(MouseEvent arg0) {

		if (!placed[loc]) { //Only can do something on this panel if they have not yet placed their item

			Point newP = arg0.getPoint();
			xPos = (int) newP.getX();
			yPos = (int) newP.getY();

			//Set these two below - for collision check / passing into server
			Item currentItem = null;
			String currentItemName = client.getItemSelected();

			//Set currentItem for collision detection
			if (currentItemName.equals("Bomb")) {
				currentItem = new Bomb(xPos, yPos);
			} else if (currentItemName.equals("Platform")) {
				currentItem = new StationaryPlatform(xPos, yPos);
			} else if (currentItemName.equals("Saw")) {
				currentItem = new Saw(xPos, yPos);
			} else if (currentItemName.equals("Spike")) {
				currentItem = new Spike(xPos, yPos);
			} else if (currentItemName.equals("Conveyor Belt")) {
				currentItem = new ConveyorBelt(xPos, yPos, 0,0);
			} else if (currentItemName.equals("Fan")) {
				currentItem = new FanWind(xPos, yPos, 0, 0);
			} else if (currentItemName.equals("CharacterLauncher")) {
				currentItem = new CharacterLauncher(xPos, yPos,0,0);
			}

			//Send command to client to send to server
			if (!(currentItem instanceof Bomb) && (totalCol(currentItem)) && this.physEng.checkCMCollision(currentItem, false) == false) { //If not bomb placed and the item is put in a valid place
				System.out.println("I"); //Test print
				client.setItemPlacementCoordinates(xPos, yPos, currentItemName); //Input x and y coordinates of item placed to server
			} else if (currentItem instanceof Bomb) {
				client.setItemPlacementCoordinates(xPos, yPos, currentItemName); //Input x and y coordinates of item placed to server
			}

		}
		
	}
	
	public void mouseReleased(MouseEvent arg0) {
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean totalCol(Item currentItem) {
		for (int i = 0; i < itemList.size(); i++) {
			if (PhysicsEngine.checkCollision(itemList.get(i), currentItem, false, false)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * checkDone
	 * Returns whether all players are done placing items or not
	 * @return boolean, true if all players have placed an item, otherwise false
	 */
	public boolean checkDone() {
		for (int i = 0; i < placed.length; i++) {
			if (placed[i] == false) {
				return false;
			}
		}
		setFocusable(false);
		return true;
	} //End of checkDone
	
	public void setDone(boolean newS) {
		for (int i = 0; i < placed.length; i++) {
			placed[i] = newS;
		}
	}
	
}
