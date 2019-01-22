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
    	Resources resource;
    	GameClient client = null;
    	
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
    	
    	public PlatformPanel(Resources usedResource, GameClient newClient) {

    	    //Store references to resources and client objects
    		this.resource = usedResource;
    		this.client = newClient;

    		//Initialize characterList (same order as corresponding players in players.get(i))
            for (int i = 0; i < 4; i++) {
                characterList.add(resource.getPlayers().get(i).getCharacter());
            }

    		this.setSize(new Dimension(1720, 760));

    		//Add collection of initial items
    		itemList.add(new StationaryPlatform(80, 250));
    		itemList.add(new FanWind(450, 100, 1, 1));
    		itemList.add(new ConveyorBelt(500, 250, 1, 0));
    		itemList.add(new ProjectileLauncher(800, 500, -3, -6, false, "Ball", launchCounter, "L")); //LaunchCounter used so that projectiles can collide with projectileLaunchers that didnt launch itself
    		launchCounter++;
    		itemList.add(new ProjectileLauncher(800, 375, 3, 0, "Arrow", launchCounter, "H")); 
    		launchCounter++;
    		itemList.add(new ProjectileLauncher(600, 450, 0, 3, "Arrow", launchCounter, "V")); // negative = upward velocity
    		launchCounter++;
    		itemList.add(new CharacterLauncher(1000, 400, 1, 1));
    		itemList.add(new MovingPlatform(100, 150, 50, 150, new double[] {1, 0}, 1));
    		itemList.add(new Spike(300, 200));
    		itemList.add(new Saw (400, 200));
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
    		  	
	    		for (int i = 0; i < 4; i++) {

	    		    //1. Find corresponding player (index to get corresponding player in resource.getPayers())
                    //2. Check that corresponding player is alive and has not completed course
                    //3. Input movement command

	    			int influencePlayer = 0;
	    			String recentInput = client.getGameplayInputs()[i].poll();
	    			for (int j = 0; j < 4; j++) {
	    				if (resource.getPlayers().get(j).getCharacter().getColor().equals("blue") && i == 0) {
	    					influencePlayer = j;
	    				} else if (resource.getPlayers().get(j).getCharacter().getColor().equals("green") && i == 1) {
	    					influencePlayer = j;
	    				} else if (resource.getPlayers().get(j).getCharacter().getColor().equals("red") && i == 2) {
	    					influencePlayer = j;
	    				} else if (resource.getPlayers().get(j).getCharacter().getColor().equals("yellow") && i == 3) {
	    					influencePlayer = j;
	    				}
	    			}
	    			
		    		if (recentInput.equals("d") && (characterList.get(influencePlayer).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
		    			onID = true;
		    			if ((characterList.get(influencePlayer)).getMotion()[1] == false) {
		    				if ((characterList.get(influencePlayer)).getHoney()) {
		    					(characterList.get(influencePlayer)).setHMotion(true, 1);
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]+1, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else if ((characterList.get(influencePlayer)).getIce()){
		    					if ((characterList.get(influencePlayer)).getIMotion()) {
		    						(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-(characterList.get(influencePlayer)).getLastI() , (characterList.get(influencePlayer)).getVelocity()[1]});
		    					}
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]+2, (characterList.get(influencePlayer)).getVelocity()[1]});
		    					(characterList.get(influencePlayer)).setIMotion(false);
		    				} else {
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]+2, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				}
		    				(characterList.get(influencePlayer)).setMotion(true, 1);
		    			}
		    		} else if (recentInput.equals("w") && (characterList.get(influencePlayer)).getJump() && ((characterList.get(influencePlayer)).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
		    			if ((characterList.get(influencePlayer)).getMotion()[2] == false) {
		    				if ((characterList.get(influencePlayer)).getHoney()) {
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0], (characterList.get(influencePlayer)).getVelocity()[1]-2.5});
		    				} else {
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0], (characterList.get(influencePlayer)).getVelocity()[1]-4.5});
		    				}
		    				(characterList.get(influencePlayer)).setMotion(true, 2);
		    			}
		    		} else if (recentInput.equals("a") && (characterList.get(influencePlayer).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
		    			onIA = true;
		    			if ((characterList.get(influencePlayer)).getMotion()[0] == false) {
		    				if ((characterList.get(influencePlayer)).getHoney()) {
		    					(characterList.get(influencePlayer)).setHMotion(true, 0);
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-1, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else if ((characterList.get(influencePlayer)).getIce()){
		    					if ((characterList.get(influencePlayer)).getIMotion()) {
		    						(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-(characterList.get(influencePlayer)).getLastI() , (characterList.get(influencePlayer)).getVelocity()[1]});
		    					}
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-2 , (characterList.get(influencePlayer)).getVelocity()[1]});
		    					(characterList.get(influencePlayer)).setIMotion(false);
		    				} else {
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-2, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				}
		    				(characterList.get(influencePlayer)).setMotion(true, 0);
		    			}
		    		} else if (recentInput.equals("s") && (characterList.get(influencePlayer).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
		    			if (characterList.get(influencePlayer).getMotion()[3] == false) {
		    				characterList.get(influencePlayer).setHeight(characterList.get(influencePlayer).getHeight()/2 + 10);
		    				characterList.get(influencePlayer).setPosition(characterList.get(influencePlayer).getPosition()[0], characterList.get(influencePlayer).getPosition()[1]+characterList.get(influencePlayer).getHeight()/2-10);
		    				characterList.get(influencePlayer).setMotion(true, 3);
		    			}
						//g.drawRect(characterList.get(i).getPosition()[0], characterList.get(i).getPosition()[1] + characterList.get(i).getHeight()/2-10, characterList.get(i).getWidth(), characterList.get(i).getHeight()/2 + 10);
		
		    		} else if (recentInput.equals("rd") && ((characterList.get(influencePlayer)).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
	    			onID = false;
		    			if (characterList.get(influencePlayer).getMotion()[1]) {
		    				
		    				if ((characterList.get(influencePlayer)).getHMotion()[1]) {
		    					(characterList.get(influencePlayer)).setHMotion(false, 1);
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-1, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else if ((characterList.get(influencePlayer)).getIce() && (characterList.get(influencePlayer)).getIMotion() == false && onIA == false){
		    					(characterList.get(influencePlayer)).setIMotion(true);
		    					(characterList.get(influencePlayer)).setLastI((characterList.get(influencePlayer)).getVelocity()[0]*(.33));
		    					System.out.println((characterList.get(influencePlayer)).getVelocity()[0]*(.33));    					
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]*(.33), (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else { 
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-2, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				}
		    				(characterList.get(influencePlayer)).setMotion(false, 1);
		    			}
		    		} else if (recentInput.equals("rw") && (characterList.get(influencePlayer).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
		    			if ((characterList.get(influencePlayer)).getMotion()[2]) {
		    				//(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0], (characterList.get(influencePlayer)).getVelocity()[1]+6});
		    				(characterList.get(influencePlayer)).setMotion(false, 2);
		    			}
		    		} else if (recentInput.equals("ra") && (characterList.get(influencePlayer).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
		    			onIA = false;
		    			if ((characterList.get(influencePlayer)).getMotion()[0]) {
		    				if ((characterList.get(influencePlayer)).getHMotion()[0]) {
		    					(characterList.get(influencePlayer)).setHMotion(false, 0);
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]+1, (characterList.get(influencePlayer)).getVelocity()[1]});	
		    				} else if ((characterList.get(influencePlayer)).getIce() && (characterList.get(influencePlayer)).getIMotion() == false && onID == false) {
		    					(characterList.get(influencePlayer)).setIMotion(true);
		    					(characterList.get(influencePlayer)).setLastI((characterList.get(influencePlayer)).getVelocity()[0]*(.33));
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]*(.33), (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else {
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]+2, (characterList.get(influencePlayer)).getVelocity()[1]});	
		    				}
		    				(characterList.get(influencePlayer)).setMotion(false, 0);
		    			}
		    		} else if (recentInput.equals("rs") && (characterList.get(influencePlayer).isAlive() && (characterList.get(influencePlayer).getFinished() == false))) {
		    			if (characterList.get(influencePlayer).getMotion()[3]) {
		    				characterList.get(influencePlayer).setPosition(characterList.get(influencePlayer).getPosition()[0], characterList.get(influencePlayer).getPosition()[1]-(characterList.get(influencePlayer).getHeight()/2-10));
		    				characterList.get(influencePlayer).setHeight(2*(characterList.get(influencePlayer).getHeight()-10));
		    				characterList.get(influencePlayer).setMotion(false, 3);
		    			}
		    		}
		    		
		    		
		    		// Call the super class
		    	    super.paintComponent(g);
		    	    setDoubleBuffered(true);
		    	    
				//Draw Score
			JLabel blueScore = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(resource.getPlayers().get(0).getScore()+"Logo.png"))));
			JLabel greenScore = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(resource.getPlayers().get(1).getScore()+"Logo.png"))));
			JLabel redScore = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(resource.getPlayers().get(2).getScore()+"Logo.png"))));
			JLabel yellowScore = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource(resource.getPlayers().get(3).getScore()+"Logo.png"))));

			this.add(blueScore);
			blueScore.setLocation(0,600);
			blueScore.setSize(780,36);

			this.add(greenScore);
			greenScore.setLocation(0,680);
			greenScore.setSize(780,36);

			this.add(redScore);
			redScore.setLocation(1000,600);
			redScore.setSize(780,36);

			this.add(yellowScore);
			yellowScore.setLocation(1000,680);
			yellowScore.setSize(780,36);
				
		    	    try {
						g.drawImage(ImageIO.read(new File("resources/SkyFortress.png")), 0, 0, 1720, 760, this);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	    
		    	    newEng.contactMapCollision((characterList.get(influencePlayer))); //Make sure that the grid is overwritten by objects!!!!!
		
		    	    //Check collision b/w characters and projectiles
		    	    for (int k = 0; k < characterList.size(); k++) {
		    	    	for (int a = 0; a < ProjectileList.size(); a++) {
		    	    		if (ProjectileList.get(a).getRadius() != 0) {
		    	    			if (newEng.checkCollision(characterList.get(k), ProjectileList.get(a), true)) {
		        	    			ProjectileList.remove(a);
		        	    		}
		    	    		} else {
		    	    			if (newEng.checkCollision(characterList.get(k), ProjectileList.get(a), false)) {
		        	    			ProjectileList.remove(a);
		        	    		}
		    	    		}
		    	    	}
		    	    }
		    	    
				if(endGame()) {
		    	    		end();
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
			    			if (itemList.get(a) instanceof ProjectileLauncher) {
			    				if (((ProjectileLauncher)itemList.get(a)).getDir().equals("l")) {
			    					g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX()+ itemList.get(a).getWidth(), itemList.get(a).getY(), -itemList.get(a).getWidth(), itemList.get(a).getHeight(), this);
			    				} else {
					    			g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight(), this);
			    				}
			    			} else {
				    			g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight(), this);
			    			}
				    		
			    			
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
			    		for (int k = 0; k < characterList.size(); k++) {
			    			if (itemList.get(a).getRadius() == 0) {
			    				newEng.checkCollision(characterList.get(k), itemList.get(a), false);
			    			} else {
			    				newEng.checkCollision(characterList.get(k), itemList.get(a), true);
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
			    		for (int k = 0; k < ProjectileList.size(); k++) {
			    			if (ProjectileList.get(k-reduceC).getRadius() != 0) {
			    				projCollide = newEng.checkCollision(itemList.get(a), ProjectileList.get(k-reduceC), false, true);
			    				if (projCollide && ((projectileCollide(itemList.get(a), ProjectileList.get(k-reduceC))) && !(itemList.get(a) instanceof FanWind)) || newEng.checkCMCollision(ProjectileList.get(k-reduceC), true)){
				    				ProjectileList.remove(k-reduceC);
				    				reduceC++;
				    			}
			    			} else { //Differentiate between circle and non-circle contactMap collisions 
			    				projCollide = newEng.checkCollision(itemList.get(a), ProjectileList.get(k-reduceC), false, false);
			    				if (projCollide && ((projectileCollide(itemList.get(a), ProjectileList.get(k-reduceC))) && !(itemList.get(a) instanceof FanWind)) || newEng.checkCMCollision(ProjectileList.get(k-reduceC), false)){
				    				ProjectileList.remove(k-reduceC);
				    				reduceC++;
				    			}
			    			}
			    			
			    		}
			    		
			    		a++;
			    		
			    	}
			    	if(endGame()) {
		    	    		end();
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
		    	    
			    	for (int k = 0; k < characterList.size(); k++) {
			    		
			    		//Loading large collection of sprites
			    		
			    		//Time to draw
			    		if(characterList.get(k).getDirectionFacing().equals("right")) {
			    			g.drawImage(characterList.get(k).getActiveFrame(), characterList.get(k).getPosition()[0], characterList.get(k).getPosition()[1], characterList.get(k).getWidth(), characterList.get(k).getHeight(), this);
			    		}else {
			    			g.drawImage(characterList.get(k).getActiveFrame(), characterList.get(k).getPosition()[0]+characterList.get(k).getWidth(), characterList.get(k).getPosition()[1], -characterList.get(k).getWidth(), characterList.get(k).getHeight(), this);
			    		}
			    		if(!characterList.get(k).isAlive()) {
		
			    			if(characterList.get(k).getCurrentFrameIndex()>0&&characterList.get(k).getCurrentFrameIndex()<6) {
			    				characterList.get(k).setCurrentFrameIndex(characterList.get(k).getCurrentFrameIndex()+1);
			    			}
						} else if(characterList.get(k).getFinished()) {
			    			if(characterList.get(k).getCurrentFrameIndex()>23&&characterList.get(k).getCurrentFrameIndex()<31) {
			    				characterList.get(k).setCurrentFrameIndex(characterList.get(k).getCurrentFrameIndex()+1);
			    			}else {
			    				characterList.get(k).setCurrentFrameIndex(24);
			    			}
						}else if (characterList.get(k).getVelocity()[1]<0) { //jump
							characterList.get(k).setCurrentFrameIndex(14);
			    		}else if(characterList.get(k).getVelocity()[1]>0){ //fall
			    			characterList.get(k).setCurrentFrameIndex(7);
			    			
			    		}else if(characterList.get(k).getMotion()[1]) { //right
			    			characterList.get(k).setDirectionFacing("right");
			    			if(characterList.get(k).getCurrentFrameIndex()>14&&characterList.get(k).getCurrentFrameIndex()<22) {
								characterList.get(k).setCurrentFrameIndex(characterList.get(k).getCurrentFrameIndex()+1);
							}else {
								characterList.get(k).setCurrentFrameIndex(15);
							}
			    		}else if(characterList.get(k).getMotion()[0]) { //left
			    			characterList.get(k).setDirectionFacing("left");
			    			if(characterList.get(k).getCurrentFrameIndex()>14&&characterList.get(k).getCurrentFrameIndex()<22) {
								characterList.get(k).setCurrentFrameIndex(characterList.get(k).getCurrentFrameIndex()+1);
							}else {
								characterList.get(k).setCurrentFrameIndex(15);
							}
			    		}else if(characterList.get(k).getMotion()[2]) { //up
							characterList.get(k).setCurrentFrameIndex(14);
							
			    		}else if(characterList.get(k).getMotion()[3]) { //crouch
			    				characterList.get(k).setCurrentFrameIndex(0);
			    			
			    		}else { //idle
		
			    			if(characterList.get(k).getCurrentFrameIndex()>7&&characterList.get(k).getCurrentFrameIndex()<13) {
								characterList.get(k).setCurrentFrameIndex(characterList.get(k).getCurrentFrameIndex()+1);
							}else {
								characterList.get(k).setCurrentFrameIndex(8);
							}
			    		}
		
			    		newEng.move(characterList.get(k));
			    	}
			    		
		    		if ((characterList.get(influencePlayer)).isAlive() == false || (characterList.get(influencePlayer).getFinished())) {
		    			//Reset all motion if character either wins or dies
		    			if (characterList.get(influencePlayer).getMotion()[1]) {
		    				if ((characterList.get(influencePlayer)).getHMotion()[1]) {
		    					(characterList.get(influencePlayer)).setHMotion(false, 1);
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-1, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else if ((characterList.get(influencePlayer)).getIce() && (characterList.get(influencePlayer)).getIMotion() == false && onIA == false){
		    					(characterList.get(influencePlayer)).setIMotion(true);
		    					(characterList.get(influencePlayer)).setLastI((characterList.get(influencePlayer)).getVelocity()[0]*(.33));
		    					System.out.println((characterList.get(influencePlayer)).getVelocity()[0]*(.33));    					
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]*(.33), (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else { 
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]-2, (characterList.get(influencePlayer)).getVelocity()[1]});
		    				}
		    				(characterList.get(influencePlayer)).setMotion(false, 1);
		    			}
		    			
		    			if ((characterList.get(influencePlayer)).getMotion()[2]) {
		    				(characterList.get(influencePlayer)).setMotion(false, 2);
		    			} 
		    			
		    			if ((characterList.get(influencePlayer)).getMotion()[0]) {
		    				if ((characterList.get(influencePlayer)).getHMotion()[0]) {
		    					(characterList.get(influencePlayer)).setHMotion(false, 0);
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]+1, (characterList.get(influencePlayer)).getVelocity()[1]});	
		    				} else if ((characterList.get(influencePlayer)).getIce() && (characterList.get(influencePlayer)).getIMotion() == false && onID == false) {
		    					(characterList.get(influencePlayer)).setIMotion(true);
		    					(characterList.get(influencePlayer)).setLastI((characterList.get(influencePlayer)).getVelocity()[0]*(.33));
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]*(.33), (characterList.get(influencePlayer)).getVelocity()[1]});
		    				} else {
		    					(characterList.get(influencePlayer)).setVelocity(new double[] {(characterList.get(influencePlayer)).getVelocity()[0]+2, (characterList.get(influencePlayer)).getVelocity()[1]});	
		    				}
		    				(characterList.get(influencePlayer)).setMotion(false, 0);
		    			} 
		    			
		    			if (characterList.get(influencePlayer).getMotion()[3] == false) {
		    				//Reset crouch to true - better accustoms the bounding box of the dead character
		    				characterList.get(influencePlayer).setMotion(true, 3);
		    				characterList.get(influencePlayer).setHeight(characterList.get(influencePlayer).getHeight()/2 + 10);
		    				characterList.get(influencePlayer).setPosition(characterList.get(influencePlayer).getPosition()[0], characterList.get(influencePlayer).getPosition()[1]+characterList.get(influencePlayer).getHeight()/2-10);
		    			}
		    			//g.drawRect(characterList.get(influencePlayer).getPosition()[0], characterList.get(influencePlayer).getPosition()[1], characterList.get(influencePlayer).getWidth(), characterList.get(influencePlayer).getHeight());
		    			
		    		}

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
    	
	public boolean endGame() {
		for(int j=0;j<resource.getCharacters().size();j++) {
			if(resource.getCharacters().get(j).isAlive()&&!resource.getCharacters().get(j).getFinished()) {
				return false;
			}
		}
		return true;
	}
    	
    	public void end(){
    		if(resource.getPlayers().get(0).getCharacter().getFinished()&&resource.getPlayers().get(1).getCharacter().getFinished()&&resource.getPlayers().get(2).getCharacter().getFinished()&&resource.getPlayers().get(3).getCharacter().getFinished()){
    			//too easy no point  (writing code here gottem)
    		}else if (resource.getPlayers().get(0).getCharacter().getFinished()||resource.getPlayers().get(1).getCharacter().getFinished()||resource.getPlayers().get(2).getCharacter().getFinished()||resource.getPlayers().get(3).getCharacter().getFinished()){ //if anyone finished
    			for (int i=0;i<4;i++){
    				if(!resource.getPlayers().get(i).getCharacter().getFinished()){
    					if (resource.getPlayers().get(i).getCharacter().getKilledBy().getPlacer()!= null && (resource.getPlayers().get(i).getCharacter().getKilledBy().getPlacer()!= resource.getPlayers().get(i))){
    						givePoints(resource.getPlayers().get(i).getCharacter().getKilledBy().getPlacer(),1);//trap
    					}
    				}else{
    					givePoints(resource.getPlayers().get(i),3);//finish
    				}
    			}
    		}
    		   
    	}
    	 
    	public void givePoints(Player p, int amount){
    		p.setScore(p.getScore()+amount);
    			
    	}
	
    	public void keyPressed(KeyEvent e) {
    		char c = e.getKeyChar();

    		if (c == 'd') {
    			client.setCharacterMovement("d");
    		}
    		
    		if (c == 'a') {
    			client.setCharacterMovement("a");
    		}
    		
    		if (c == 's') {
    			client.setCharacterMovement("s");
    		}
    		
    		if (c == 'w') {
    			client.setCharacterMovement("w");
    		}
    		
    	}
    	
    	public void keyReleased(KeyEvent e) {
    		char c = e.getKeyChar();
    		
    		if (c == 'd') {
    			client.setCharacterMovement("rd");
    		}
    		
    		if (c == 'a') {
    			client.setCharacterMovement("ra");
    		}
    		
    		if (c == 's') {
    			client.setCharacterMovement("rs");
    		}
    		
    		if (c == 'w') {
    			client.setCharacterMovement("rw");
    		}
    		
    	}
    	
    	public void keyTyped(KeyEvent e) {
    	}
    }
