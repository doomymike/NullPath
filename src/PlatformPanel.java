import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.util.ArrayList;

/*
 * PlatformPanel
 * 
 * Authors: Michael and James
 * Description: Main panel for game
 * Last Updated: January 21, 2018
 * 
 */



public class PlatformPanel extends JPanel implements KeyListener{

		ArrayList<Item> itemList = new ArrayList<Item>();
		ArrayList<Character> characterList = new ArrayList<Character>();
    	ArrayList <Projectile> ProjectileList = new ArrayList<Projectile>();
		
    	PhysicsEngine newEng = null;
    	
    	double initStart;
    	int lastNum;
    	boolean onID;
    	boolean onIA;
    	String[][] contactMap; //Condensed version of the string map that's fed as input (used for game map)
    	int launchCounter = -1; //Used in correspondance with projectilelauncher projectile pairs
    	
    	/**
    	 * PlatformPanel
    	 * 
    	 * Constructor for PlatformPanel
    	 */
    	
    	public PlatformPanel() {
    		this.setSize(new Dimension(1720, 760));
    		try {
				characterList.add(new Character("Blue",200, 200, 60, 40, 0));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		//Add collection of initial items
    		itemList.add(new StationaryPlatform(80, 250, 150, 300));
    		itemList.add(new FanWind(450, 100, 600, 50, 1, 1));
    		itemList.add(new ConveyorBelt(500, 250, 40, 200, 1, 0));
    		//itemList.add(new ProjectileLauncher(800, 500,50, 50, -3, -6, false, "Ball", launchCounter, "L")); //LaunchCounter used so that projectiles can collide with projectileLaunchers that didnt launch itself
    		//launchCounter++;
    		itemList.add(new ProjectileLauncher(800, 375,50, 50, 3, 0, "Arrow", launchCounter, "H")); 
    		launchCounter++;
    		itemList.add(new ProjectileLauncher(600, 450,50, 50, 0, 3, "Arrow", launchCounter, "V")); // negative = upward velocity
    		launchCounter++;
    		itemList.add(new CharacterLauncher(1000, 400, 20, 60, 1, 1));
    		//itemList.add(new MovingPlatform(100, 150, 50, 150, 500, 100, new double[] {1, 0}, 1));
    		itemList.add(new Spike(300, 200, 50, 50));
    		//itemList.add(new Saw (400, 200, 50));
    		initStart = System.nanoTime()/(Math.pow(10, 9));
    		//Start timer for projectileLaunchers
    		onIA = false;
    		onID = false;
    		try {
				newEng = new PhysicsEngine("PS");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		//Load in the contactMap (retrieved by physics engine)
    		contactMap = newEng.retrieveCMap();
    	    newEng.printMap(contactMap);
    	    
    	    //Loading in sprites
    	    
    	    try {
				itemList.get(3).setImage(ImageIO.read(new File("resources/Cannon1.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    try {
				itemList.get(4).setImage(ImageIO.read(new File("resources/Bow1.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	    try {
				itemList.get(5).setImage(ImageIO.read(new File("resources/Bow1.png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
    	   
    		setFocusable(true);
    		requestFocusInWindow();
            setVisible (true);
    		addKeyListener(this);
    	}
    	
    	/**
    	 * paintComponent
    	 * 
    	 * Game loop operations for the main game. 
    	 * 
    	 * @param 
    	 */
    	
    	public void paintComponent(Graphics g) {
    		  
    		// Call the super class
    	    super.paintComponent(g);
    	    setDoubleBuffered(true);
    	    
    	    try {
				g.drawImage(ImageIO.read(new File("resources/SkyFortress.png")), 0, 0, 1720, 760, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    
    	    newEng.contactMapCollision((characterList.get(0))); //Make sure that the grid is overwritten by objects!!!!!

    	    //Check collision b/w characters and projectiles
    	    for (int i = 0; i < characterList.size(); i++) {
    	    	for (int a = 0; a < ProjectileList.size(); a++) {
    	    		if (ProjectileList.get(a).getRadius() != 0) {
    	    			if (newEng.checkCollision(characterList.get(i), ProjectileList.get(a), true)) {
        	    			ProjectileList.remove(a);
        	    		}
    	    		} else {
    	    			if (newEng.checkCollision(characterList.get(i), ProjectileList.get(a), false)) {
        	    			ProjectileList.remove(a);
        	    		}
    	    		}
    	    	}
    	    }
    	    
	    	int a = 0;
	    	while (a < itemList.size()) {
	    		
	    		//Draw fanwind - ensure that the fan is only drawn on the portion of the overall fanwind track
	    		if (itemList.get(a) instanceof FanWind) {
	    			g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY()+ itemList.get(a).getHeight()-20, itemList.get(a).getWidth(), 20, this);
	    			if(itemList.get(a).getSprites()!=null) {
		    			
		    			if(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())== itemList.get(a).getSprites().size()-1) {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(0));
		    			}else {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())+1));
		    			}
		    				    			
		    		}
	    			
	    		//Specialized drawings of character and projectileLauncher (launch animations)
	    		}else if(itemList.get(a) instanceof ProjectileLauncher||itemList.get(a) instanceof CharacterLauncher) {
	    			g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight(), this);
		    		
	    			
	    			if(!itemList.get(a).getImage().equals(itemList.get(a).getSprites().get(0))&&!itemList.get(a).getImage().equals(itemList.get(a).getSprites().get(itemList.get(a).getSprites().size()-1))) {
		    			itemList.get(a).setImage(itemList.get(a).getSprites().get(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())+1));
		    		}else {
		    			itemList.get(a).setImage(itemList.get(a).getSprites().get(0));
		    		}
	    		} else {
	    			//Drawing of all other sprites if available in the resource folder
	    			if (itemList.get(a).getRadius()!= 0){

	    				g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX() - itemList.get(a).getRadius(), itemList.get(a).getY() - itemList.get(a).getRadius(), 2*itemList.get(a).getRadius(), 2*itemList.get(a).getRadius(), this);
	    			} else {
	    				g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight(), this);
	    			}
	    			if(itemList.get(a).getSprites()!=null) {
		    			
		    			if(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())== itemList.get(a).getSprites().size()-1) {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(0));
		    			}else {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())+1));
		    			}
		    				    			
		    		}
	    		}
	    		
	    		//Collisions vary based on whether or not they are circular (character x item)
	    		for (int i = 0; i < characterList.size(); i++) {
	    			if (itemList.get(a).getRadius() == 0) {
	    				newEng.checkCollision(characterList.get(i), itemList.get(a), false);
	    			} else {
	    				newEng.checkCollision(characterList.get(i), itemList.get(a), true);
	    			}
	    		}
	    		
	    		//Specialized call methods for projectileLauncher and movingPlatform objects
	    		
	    		if (itemList.get(a) instanceof ProjectileLauncher) {
        	    	((ProjectileLauncher)itemList.get(a)).launchProjectile(ProjectileList);
	    		}
	    		
	    		if (itemList.get(a) instanceof MovingPlatform) {
	    			newEng.move(itemList.get(a));
	    		}
	    		
	    		//Checks for item character collision
	    		
	    		int reduceC = 0;
	    		boolean projCollide = false;
	    		for (int i = 0; i < ProjectileList.size(); i++) {
	    			if (ProjectileList.get(i-reduceC).getRadius() != 0) {
	    				projCollide = newEng.checkCollision(itemList.get(a), ProjectileList.get(i-reduceC), false, true);
	    				if (projCollide && ((projectileCollide(itemList.get(a), ProjectileList.get(i-reduceC))) && !(itemList.get(a) instanceof FanWind)) || newEng.checkCMCollision(ProjectileList.get(i-reduceC), true)){
		    				ProjectileList.remove(i-reduceC);
		    				reduceC++;
		    			}
	    			} else { //Differentiate between circle and non-circle contactMap collisions 
	    				projCollide = newEng.checkCollision(itemList.get(a), ProjectileList.get(i-reduceC), false, false);
	    				if (projCollide && ((projectileCollide(itemList.get(a), ProjectileList.get(i-reduceC))) && !(itemList.get(a) instanceof FanWind)) || newEng.checkCMCollision(ProjectileList.get(i-reduceC), false)){
		    				ProjectileList.remove(i-reduceC);
		    				reduceC++;
		    			}
	    			}
	    			
	    		}
	    		
	    		a++;
	    		
	    	}
	    	
	    	//while loop is used in case items may need to be removed
    	    
	    	//Draw Projectiles
	    	
    	    int c = 0;
    	    while (c < ProjectileList.size()) {
    	    	newEng.move(ProjectileList.get(c));
    	    	if(ProjectileList.get(c) instanceof Pellet) {
    	    		g.drawImage(ProjectileList.get(c).getImage(), ProjectileList.get(c).getX(), ProjectileList.get(c).getY(), ProjectileList.get(c).getRadius(), ProjectileList.get(c).getRadius(), this);
    	    	}else {
    	    		g.drawImage(ProjectileList.get(c).getImage(), ProjectileList.get(c).getX(), ProjectileList.get(c).getY(), ProjectileList.get(c).getWidth(), ProjectileList.get(c).getHeight(), this);
        	    	
    	    	}
    	    	if (ProjectileList.get(c).getX() < 0 || ProjectileList.get(c).getX() > 1720 || ProjectileList.get(c).getY() < 0 || ProjectileList.get(c).getY() > 760) {
    	    		ProjectileList.remove(c);
    	    		c--;
    	    	}
    	    	c++;
    	    }
    	    
	    	for (int i = 0; i < characterList.size(); i++) {
	    		
	    		//Loading large collection of sprites
	    		
	    		//Time to draw
	    		if(characterList.get(i).getDirectionFacing().equals("right")) {
	    			g.drawImage(characterList.get(i).getActiveFrame(), characterList.get(i).getPosition()[0], characterList.get(i).getPosition()[1], characterList.get(i).getWidth(), characterList.get(i).getHeight(), this);
	    		}else {
	    			g.drawImage(characterList.get(i).getActiveFrame(), characterList.get(i).getPosition()[0]+characterList.get(i).getWidth(), characterList.get(i).getPosition()[1], -characterList.get(i).getWidth(), characterList.get(i).getHeight(), this);
	    		}
	    		if(!characterList.get(i).isAlive()) {

	    			if(characterList.get(i).getCurrentFrameIndex()>0&&characterList.get(i).getCurrentFrameIndex()<6) {
	    				System.out.println(characterList.get(i).getCurrentFrameIndex());
	    				characterList.get(i).setCurrentFrameIndex(characterList.get(i).getCurrentFrameIndex()+1);
	    			}
				} else if (characterList.get(i).getVelocity()[1]<0) { //jump
					characterList.get(i).setCurrentFrameIndex(14);
	    		}else if(characterList.get(i).getVelocity()[1]>0){ //fall
	    			characterList.get(i).setCurrentFrameIndex(7);
	    			
	    		}else if(characterList.get(i).getMotion()[1]) { //right
	    			characterList.get(i).setDirectionFacing("right");
	    			if(characterList.get(i).getCurrentFrameIndex()>14&&characterList.get(i).getCurrentFrameIndex()<22) {
						characterList.get(i).setCurrentFrameIndex(characterList.get(i).getCurrentFrameIndex()+1);
					}else {
						characterList.get(i).setCurrentFrameIndex(15);
					}
	    		}else if(characterList.get(i).getMotion()[0]) { //left
	    			characterList.get(i).setDirectionFacing("left");
	    			if(characterList.get(i).getCurrentFrameIndex()>14&&characterList.get(i).getCurrentFrameIndex()<22) {
						characterList.get(i).setCurrentFrameIndex(characterList.get(i).getCurrentFrameIndex()+1);
					}else {
						characterList.get(i).setCurrentFrameIndex(15);
					}
	    		}else if(characterList.get(i).getMotion()[2]) { //up
					characterList.get(i).setCurrentFrameIndex(14);
					
	    		}else if(characterList.get(i).getMotion()[3]) { //crouch
	    				characterList.get(i).setCurrentFrameIndex(0);
	    			
	    		}else { //idle

	    			if(characterList.get(i).getCurrentFrameIndex()>7&&characterList.get(i).getCurrentFrameIndex()<13) {
						characterList.get(i).setCurrentFrameIndex(characterList.get(i).getCurrentFrameIndex()+1);
					}else {
						characterList.get(i).setCurrentFrameIndex(8);
					}
	    		}

	    		newEng.move(characterList.get(i));
	    	}
	    		
    		if ((characterList.get(0)).isAlive() == false || (characterList.get(0).getFinished())) {
    			//Reset all motion if character either wins or dies
    			if (characterList.get(0).getMotion()[1]) {
    				if ((characterList.get(0)).getHMotion()[1]) {
    					(characterList.get(0)).setHMotion(false, 1);
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-1, (characterList.get(0)).getVelocity()[1]});
    				} else if ((characterList.get(0)).getIce() && (characterList.get(0)).getIMotion() == false && onIA == false){
    					(characterList.get(0)).setIMotion(true);
    					(characterList.get(0)).setLastI((characterList.get(0)).getVelocity()[0]*(.33));
    					System.out.println((characterList.get(0)).getVelocity()[0]*(.33));    					
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]*(.33), (characterList.get(0)).getVelocity()[1]});
    				} else { 
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-2, (characterList.get(0)).getVelocity()[1]});
    				}
    				(characterList.get(0)).setMotion(false, 1);
    			}
    			
    			if ((characterList.get(0)).getMotion()[2]) {
    				(characterList.get(0)).setMotion(false, 2);
    			} 
    			
    			if ((characterList.get(0)).getMotion()[0]) {
    				if ((characterList.get(0)).getHMotion()[0]) {
    					(characterList.get(0)).setHMotion(false, 0);
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]+1, (characterList.get(0)).getVelocity()[1]});	
    				} else if ((characterList.get(0)).getIce() && (characterList.get(0)).getIMotion() == false && onID == false) {
    					(characterList.get(0)).setIMotion(true);
    					(characterList.get(0)).setLastI((characterList.get(0)).getVelocity()[0]*(.33));
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]*(.33), (characterList.get(0)).getVelocity()[1]});
    				} else {
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]+2, (characterList.get(0)).getVelocity()[1]});	
    				}
    				(characterList.get(0)).setMotion(false, 0);
    			} 
    			
    			if (characterList.get(0).getMotion()[3] == false) {
    				//Reset crouch to true - better accustoms the bounding box of the dead character
    				characterList.get(0).setMotion(true, 3);
    				characterList.get(0).setHeight(characterList.get(0).getHeight()/2 + 10);
    				characterList.get(0).setPosition(characterList.get(0).getPosition()[0], characterList.get(0).getPosition()[1]+characterList.get(0).getHeight()/2-10);
    			}
    			//g.drawRect(characterList.get(0).getPosition()[0], characterList.get(0).getPosition()[1], characterList.get(0).getWidth(), characterList.get(0).getHeight());
    			
    		}
	    	
    	    repaint();
    	}
    	
    	public boolean projectileCollide(Item launch, Projectile obj) {
    		if (launch instanceof ProjectileLauncher) {
    			if (((ProjectileLauncher) launch).getTag() == obj.getTag()) {
    				return false;
    			}
    		}
    		return true;
    	}
    	
    	public void keyPressed(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd' && (characterList.get(0).isAlive() && (characterList.get(0).getFinished() == false))) {
    			onID = true;
    			if ((characterList.get(0)).getMotion()[1] == false) {
    				if ((characterList.get(0)).getHoney()) {
    					(characterList.get(0)).setHMotion(true, 1);
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]+1, (characterList.get(0)).getVelocity()[1]});
    				} else if ((characterList.get(0)).getIce()){
    					if ((characterList.get(0)).getIMotion()) {
    						(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-(characterList.get(0)).getLastI() , (characterList.get(0)).getVelocity()[1]});
    					}
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]+2, (characterList.get(0)).getVelocity()[1]});
    					(characterList.get(0)).setIMotion(false);
    				} else {
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]+2, (characterList.get(0)).getVelocity()[1]});
    				}
    				(characterList.get(0)).setMotion(true, 1);
    			}
    		} else if (c == 'w' && (characterList.get(0)).getJump() && ((characterList.get(0)).isAlive() && (characterList.get(0).getFinished() == false))) {
    			if ((characterList.get(0)).getMotion()[2] == false) {
    				/*
    				if((characterList.get(0)).getIMotion()) {
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-(characterList.get(0)).getLastI() , (characterList.get(0)).getVelocity()[1]});
    					(characterList.get(0)).setIMotion(false);
    				}
    				*/
    				if ((characterList.get(0)).getHoney()) {
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0], (characterList.get(0)).getVelocity()[1]-2.5});
    				} else {
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0], (characterList.get(0)).getVelocity()[1]-4.5});
    				}
    				(characterList.get(0)).setMotion(true, 2);
    			}
    		} else if (c == 'a' && (characterList.get(0).isAlive() && (characterList.get(0).getFinished() == false))) {
    			onIA = true;
    			if ((characterList.get(0)).getMotion()[0] == false) {
    				if ((characterList.get(0)).getHoney()) {
    					(characterList.get(0)).setHMotion(true, 0);
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-1, (characterList.get(0)).getVelocity()[1]});
    				} else if ((characterList.get(0)).getIce()){
    					if ((characterList.get(0)).getIMotion()) {
    						(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-(characterList.get(0)).getLastI() , (characterList.get(0)).getVelocity()[1]});
    					}
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-2 , (characterList.get(0)).getVelocity()[1]});
    					(characterList.get(0)).setIMotion(false);
    				} else {
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-2, (characterList.get(0)).getVelocity()[1]});
    				}
    				(characterList.get(0)).setMotion(true, 0);
    			}
    		} else if (c == 's' && (characterList.get(0).isAlive() && (characterList.get(0).getFinished() == false))) {
    			if (characterList.get(0).getMotion()[3] == false) {
    				characterList.get(0).setHeight(characterList.get(0).getHeight()/2 + 10);
    				characterList.get(0).setPosition(characterList.get(0).getPosition()[0], characterList.get(0).getPosition()[1]+characterList.get(0).getHeight()/2-10);
    				characterList.get(0).setMotion(true, 3);
    			}
				//g.drawRect(characterList.get(i).getPosition()[0], characterList.get(i).getPosition()[1] + characterList.get(i).getHeight()/2-10, characterList.get(i).getWidth(), characterList.get(i).getHeight()/2 + 10);

    		}
    		
    	}
    	
    	public void keyReleased(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd' && ((characterList.get(0)).isAlive() && (characterList.get(0).getFinished() == false))) {
    			onID = false;
    			if (characterList.get(0).getMotion()[1]) {
    				
    				if ((characterList.get(0)).getHMotion()[1]) {
    					(characterList.get(0)).setHMotion(false, 1);
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-1, (characterList.get(0)).getVelocity()[1]});
    				} else if ((characterList.get(0)).getIce() && (characterList.get(0)).getIMotion() == false && onIA == false){
    					(characterList.get(0)).setIMotion(true);
    					(characterList.get(0)).setLastI((characterList.get(0)).getVelocity()[0]*(.33));
    					System.out.println((characterList.get(0)).getVelocity()[0]*(.33));    					
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]*(.33), (characterList.get(0)).getVelocity()[1]});
    				} else { 
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]-2, (characterList.get(0)).getVelocity()[1]});
    				}
    				(characterList.get(0)).setMotion(false, 1);
    			}
    		} else if (c == 'w' && (characterList.get(0).isAlive() && (characterList.get(0).getFinished() == false))) {
    			if ((characterList.get(0)).getMotion()[2]) {
    				//(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0], (characterList.get(0)).getVelocity()[1]+6});
    				(characterList.get(0)).setMotion(false, 2);
    			}
    		} else if (c == 'a' && (characterList.get(0).isAlive() && (characterList.get(0).getFinished() == false))) {
    			onIA = false;
    			if ((characterList.get(0)).getMotion()[0]) {
    				if ((characterList.get(0)).getHMotion()[0]) {
    					(characterList.get(0)).setHMotion(false, 0);
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]+1, (characterList.get(0)).getVelocity()[1]});	
    				} else if ((characterList.get(0)).getIce() && (characterList.get(0)).getIMotion() == false && onID == false) {
    					(characterList.get(0)).setIMotion(true);
    					(characterList.get(0)).setLastI((characterList.get(0)).getVelocity()[0]*(.33));
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]*(.33), (characterList.get(0)).getVelocity()[1]});
    				} else {
    					(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0]+2, (characterList.get(0)).getVelocity()[1]});	
    				}
    				(characterList.get(0)).setMotion(false, 0);
    			}
    		} else if (c == 's' && (characterList.get(0).isAlive() && (characterList.get(0).getFinished() == false))) {
    			if (characterList.get(0).getMotion()[3]) {
    				characterList.get(0).setPosition(characterList.get(0).getPosition()[0], characterList.get(0).getPosition()[1]-(characterList.get(0).getHeight()/2-10));
    				characterList.get(0).setHeight(2*(characterList.get(0).getHeight()-10));
    				characterList.get(0).setMotion(false, 3);
    			}
    		}
    	}
    	
    	public void keyTyped(KeyEvent e) {
    	}
    }