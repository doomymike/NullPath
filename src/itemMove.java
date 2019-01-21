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
	private int influencePlayer;
	private boolean[] placed = new boolean[1];
	
	public itemMove(Resources usedResource, PhysicsEngine newEng) { //Add constructor for client later
		// TODO Auto-generated constructor stub
		this.setSize(new Dimension(1720, 760));
		this.cMap = newEng.retrieveCMap();
		this.influencePlayer = 0;
		this.physEng = newEng;
		this.cStage = usedResource.getCurrentStage();
		
		for (int i = 0; i < placed.length; i++) {
			this.placed[i] = false;
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
		
		Point newP = arg0.getPoint();
		xPos = (int) newP.getX();
		yPos = (int) newP.getY();
		
		Item currentItem = new FanWind(xPos, yPos, 200, 50, 1, 1);
		
		if (bombMark) {
			currentItem = new Bomb(xPos, yPos);
		}
		int reduceC = 0;
		if (!(currentItem instanceof Bomb) && (totalCol(currentItem)) && this.physEng.checkCMCollision(currentItem, false) == false){
			System.out.println("I");
			itemList.add(currentItem);
			this.cStage.addItem(currentItem);
			placed[0] = true;
		} else if (currentItem instanceof Bomb) {
			System.out.println("BAD");
			for (int i = 0; i < itemList.size(); i++) {
				if (PhysicsEngine.checkCollision(currentItem, itemList.get(i-reduceC), true, false)) {
					System.out.println("BAD123");
					itemList.remove(i-reduceC);
					this.cStage.removeItem(currentItem);
					reduceC++;
				}
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
