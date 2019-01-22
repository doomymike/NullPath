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

public class itemMove extends JPanel implements KeyListener, MouseListener{

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
	
	public itemMove(Resources usedResource, PhysicsEngine newEng,GameClient client) { //Add constructor for client later
		// TODO Auto-generated constructor stub
		this.setSize(new Dimension(1720, 760));
		this.cMap = newEng.retrieveCMap();
		this.physEng = newEng;
		this.cStage = usedResource.getCurrentStage();
		this.client =client;
		this.resources = usedResource;

		for (int i = 0; i < 4; i++) {
			if (resources.getPlayers().get(i).getName().equals(client.getUsername())) {
				influencePlayer = i; //Use for setPlacer when item is placed
			}
		}

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

		/*
		for (int i = 0; i < 4; i++) {
			String itemName = newClient.getItemsHeld()[i];	
			for (int j = 0; j < 4; j++) {
				if (usedResource.getPlayers().get(j).getName().equals("Blue") && i ==0) {
					this.influencePlayer = j;
				} else if (usedResource.getPlayers().get(j).getName().equals("Green") && i ==1) {
					this.influencePlayer = j;
				} else if (usedResource.getPlayers().get(j).getName().equals("Red") && i ==2) {
					this.influencePlayer = j;
				} else if (usedResource.getPlayers().get(j).getName().equals("Yellow") && i ==3) {
					this.influencePlayer = j;
				}
			}
		}
		*/		
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
				if (client.getAllItemPlacementCoordinates()[i][0] != null) { //If the corresponding player actually placed something

					//Read x, y coordinates of item placed by player from client
					int tempX = Integer.parseInt(client.getAllItemPlacementCoordinates()[i][0]);
					int tempY = Integer.parseInt(client.getAllItemPlacementCoordinates()[i][1]);

					//MAKE TEMP ITEM - using "bomb" or "not bomb"
					Item currentItem = null;
					String currentItemName = client.getAllItemPlacementCoordinates()[i][2];

					if (currentItemName.equals("Bomb")) {
						currentItem = new Bomb(tempX, tempY);
					} else if (currentItemName.equals("Platform")) {

					}

					//Place on map if item
					if (currentItemName.equals("Bomb")) {
						itemList.add(currentItem);
						this.cStage.addItem(currentItem);
					} else { //Do bomb stuff
						int reduceC = 0;
						for (int j = 0; j < itemList.size(); j++) {
							//WARNING - will not work if non-bomb item is circular
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
			String currentItemName = "";

			//Set currentItem with proper dimensions
			if (client.getItemSelected().equals("Bomb")) {

			} else if (client.getItemSelected().equals("Platform")) {

			}

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
	
	public boolean checkDone() {
		
		for (int i = 0; i < placed.length; i++) {
			if (placed[i] == false) {
				return false;
			}
		}
		setFocusable(false);
		return true;
	}
	
	public void setDone(boolean newS) {
		for (int i = 0; i < placed.length; i++) {
			placed[i] = newS;
		}
	}
	
}
