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

public class testFrameF extends JPanel implements KeyListener{

		ArrayList<Item> itemList = new ArrayList<Item>();
		ArrayList<Character> characterList = new ArrayList<Character>();
    	ArrayList <Projectile> ProjectileList = new ArrayList<Projectile>();
		
    	PhysicsEngine newEng = null;
    	/*
    	StationaryPlatform plat = null;
    	FanWind wind = null;
    	ConveyorBelt track = null;
    	ProjectileLauncher launcher = null;
    	CharacterLauncher cLaunch = null;
    	*/
    	
    	double initStart;
    	int lastNum;
    	boolean onID;
    	boolean onIA;
    	String[][] contactMap;
    	int launchCounter = -1;
    	
    	public testFrameF() throws FileNotFoundException, IOException {
    		this.setSize(new Dimension(1720, 760));
    		characterList.add(new Character("Blue",80, 240, 60, 40, 0));
    		itemList.add(new StationaryPlatform(80, 250, 150, 300));
    		itemList.add(new FanWind(450, 100, 600, 50, 1, 1));
    		itemList.add(new ConveyorBelt(500, 250, 40, 200, 1, 0));
    		itemList.add(new ProjectileLauncher(800, 500,50, 50, -3, -6, false, "Ball", launchCounter)); //LaunchCounter used so that projectiles can collide with projectileLaunchers that didnt launch itself
    		launchCounter++;
    		itemList.add(new ProjectileLauncher(800, 375,50, 50, 3, 0, "ArrowH", launchCounter)); 
    		launchCounter++;
    		itemList.add(new ProjectileLauncher(600, 450,50, 50, 0, -3, "ArrowV", launchCounter)); // negative = upward velocity
    		launchCounter++;
    		itemList.add(new CharacterLauncher(1000, 400, 20, 60, 1, 1));
    		itemList.add(new MovingPlatform(100, 150, 50, 150, 500, 100, new double[] {1, 0}, 1));
    		itemList.add(new Spike(300, 200, 50, 50));
    		itemList.add(new Saw (400, 200, 50));
    		initStart = System.nanoTime()/(Math.pow(10, 9));
    		onIA = false;
    		onID = false;
    		newEng = new PhysicsEngine("Bad");
    		contactMap = newEng.retrieveCMap();
    	    newEng.printMap(contactMap);
    	    
    	    itemList.get(3).setImage(ImageIO.read(new File("resources/Cannon1.png")));
    	    itemList.get(4).setImage(ImageIO.read(new File("resources/Bow1.png")));
    	    itemList.get(5).setImage(ImageIO.read(new File("resources/Bow1.png")));
    	    
    	    
    		setFocusable(true);
    		requestFocusInWindow();
            setVisible (true);
    		addKeyListener(this);
    	}
    	
    	public void paintComponent(Graphics g) {
    		  
    		// Call the super class
    	    super.paintComponent(g);
    	    setDoubleBuffered(true);
    	    
//	    	for (int i = 0; i < contactMap.length; i++) {
//	    		for (int a = 0; a < contactMap[0].length; a++) {
//	    			if (contactMap[i][a].equals("1")) {
//	    				g.setColor(Color.magenta);
//	    				g.drawRect((a+1)*20, (i+1)*20, 20, 20);
//	    			} else if (contactMap[i][a].equals("2")) {
//	    				g.setColor(Color.PINK);
//	    				g.fillRect((a+1)*20, (i+1)*20, 20, 20);
//	    			}
//	    		}
//	    	}
    	    try {
				g.drawImage(ImageIO.read(new File("resources/SkyFortress.png")), 20, 20, 1720, 760, this);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
//	    	for (int i = 0; i < contactMap[0].length; i++) {
//	    		g.setColor(Color.CYAN);
//	    		g.drawRect(1700, 720, 20, 20);
//	    	}
    	    
    	    newEng.contactMapCollision((characterList.get(0))); //Make sure that the grid is overwritten by objects!!!!!
    	    
    	    //Changed characters into ArrayList
    	    //Character Collision:
    	    
    	    //Use the following for multi character integration
    	    for (int i = 0; i < characterList.size(); i++) {
    	    	for (int a = 0; a < ProjectileList.size(); a++) {
    	    		if (newEng.checkCollision(characterList.get(i), ProjectileList.get(a), true)) {
    	    			ProjectileList.remove(a);
    	    		}
    	    	}
    	    }
    	    

    	    
	    	int a = 0;
	    	while (a < itemList.size()) {
	    		
	    		if (itemList.get(a) instanceof FanWind) {
	    			g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY()+ itemList.get(a).getHeight()-20, itemList.get(a).getWidth(), 20, this);
	    			if(itemList.get(a).getSprites()!=null) {
		    			
		    			if(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())== itemList.get(a).getSprites().size()-1) {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(0));
		    			}else {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())+1));
		    			}
		    				    			
		    		}
	    		}else if(itemList.get(a) instanceof ProjectileLauncher) {
	    			g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight(), this);
		    		
	    			System.out.println(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage()));
		    		System.out.println(itemList.get(a).getImage());
	    			if(!itemList.get(a).getImage().equals(itemList.get(a).getSprites().get(0))&&!itemList.get(a).getImage().equals(itemList.get(a).getSprites().get(itemList.get(a).getSprites().size()-1))) {
		    			itemList.get(a).setImage(itemList.get(a).getSprites().get(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())+1));
		    		}else {
		    			itemList.get(a).setImage(itemList.get(a).getSprites().get(0));
		    		}
	    		}else {
	    			g.drawImage(itemList.get(a).getImage(), itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight(), this);
	    			if(itemList.get(a).getSprites()!=null) {
		    			
		    			if(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())== itemList.get(a).getSprites().size()-1) {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(0));
		    			}else {
		    				itemList.get(a).setImage(itemList.get(a).getSprites().get(itemList.get(a).getSprites().indexOf(itemList.get(a).getImage())+1));
		    			}
		    				    			
		    		}
	    		}
	    		
	    		
	    		
//	    		if (itemList.get(a) instanceof CharacterLauncher) {
//	    			g.setColor(Color.BLACK);
//	    		} else if (itemList.get(a) instanceof ProjectileLauncher) {
//	    			g.setColor(Color.GREEN);
//	    		} else if (itemList.get(a) instanceof Platform) {
//	    			g.setColor(Color.DARK_GRAY);
//	    		} else if (itemList.get(a) instanceof FanWind) {
//	    			g.setColor(Color.BLUE);
//	    		} else if (itemList.get(a) instanceof ConveyorBelt) {
//	    			g.setColor(Color.CYAN);
//	    		} else {
//	    			g.setColor(Color.ORANGE);
//	    		}
//	    		if (itemList.get(a).getRadius() != 0) {
//	    			g.fillOval(itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getRadius(), itemList.get(a).getRadius());
//	    		}
//	    		g.fillRect(itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight());
	    		for (int i = 0; i < characterList.size(); i++) {
	    			if (itemList.get(a).getRadius() == 0) {
	    				newEng.checkCollision(characterList.get(i), itemList.get(a), false);
	    			} else {
	    				newEng.checkCollision(characterList.get(i), itemList.get(a), true);
	    			}
	    		}
	    		
	    		if (itemList.get(a) instanceof ProjectileLauncher) {
        	    	((ProjectileLauncher)itemList.get(a)).launchProjectile(ProjectileList);
	    		}
	    		
	    		if (itemList.get(a) instanceof MovingPlatform) {
	    			newEng.move(itemList.get(a));
	    		}
	    		
	    		//MAIN EDIT PORTION
	    		
	    		int reduceC = 0;
	    		boolean projCollide = false;
	    		for (int i = 0; i < ProjectileList.size(); i++) {
	    			if (ProjectileList.get(i-reduceC).getRadius() != 0) {
	    				projCollide = newEng.checkCollision(itemList.get(a), ProjectileList.get(i-reduceC), false, true);
	    			} else {
	    				projCollide = newEng.checkCollision(itemList.get(a), ProjectileList.get(i-reduceC), false, false);
	    			}
	    			if (projCollide && ((projectileCollide(itemList.get(a), ProjectileList.get(i-reduceC))) && !(itemList.get(a) instanceof FanWind)) || newEng.checkCMCollision(ProjectileList.get(i-reduceC), true)){
	    				ProjectileList.remove(i-reduceC);
	    				reduceC++;
	    			}
	    		}
	    		
	    		a++;
	    		
	    	}
	    	
	    	//while loop is used in case items may need to be removed
    	    
    	    int c = 0;
    	    while (c < ProjectileList.size()) {
    	    	newEng.move(ProjectileList.get(c));
    	    	if(ProjectileList.get(c) instanceof Pellet) {
    	    		g.drawImage(ProjectileList.get(c).getImage(), ProjectileList.get(c).getX(), ProjectileList.get(c).getY(), ProjectileList.get(c).getRadius(), ProjectileList.get(c).getRadius(), this);
    	    	}else {
    	    		g.drawImage(ProjectileList.get(c).getImage(), ProjectileList.get(c).getX(), ProjectileList.get(c).getY(), ProjectileList.get(c).getWidth(), ProjectileList.get(c).getHeight(), this);
        	    	
    	    	}
//    	    	g.setColor(Color.RED);
//    	    	if (ProjectileList.get(c) instanceof Pellet) {
//    	    		g.fillOval(ProjectileList.get(c).getX(), ProjectileList.get(c).getY(), ProjectileList.get(c).getRadius(), ProjectileList.get(c).getRadius());
//    	    	} else if (ProjectileList.get(c) instanceof Arrow) {
//    	    		g.fillRect(ProjectileList.get(c).getX(), ProjectileList.get(c).getY(), ProjectileList.get(c).getWidth(), ProjectileList.get(c).getHeight());
//    	    	}
    	    	
    	    	if (ProjectileList.get(c).getX() < 0 || ProjectileList.get(c).getX() > 1720 || ProjectileList.get(c).getY() < 0 || ProjectileList.get(c).getY() > 760) {
    	    		ProjectileList.remove(c);
    	    		c--;
    	    	}
    	    	c++;
    	    	//g.fillRect(ProjectileList.get(i).getX(), ProjectileList.get(i).getY(), ProjectileList.get(i).getWidth(), ProjectileList.get(i).getHeight());
    	    }
    	    
	    	for (int i = 0; i < characterList.size(); i++) {
	    		
	    		//Time to draw
	    		if(characterList.get(i).getDirectionFacing().equals("right")) {
	    			g.drawImage(characterList.get(i).getActiveFrame(), characterList.get(i).getPosition()[0], characterList.get(i).getPosition()[1], characterList.get(i).getWidth(), characterList.get(i).getHeight(), this);
	    		}else {
	    			g.drawImage(characterList.get(i).getActiveFrame(), characterList.get(i).getPosition()[0]+characterList.get(i).getWidth(), characterList.get(i).getPosition()[1], -characterList.get(i).getWidth(), characterList.get(i).getHeight(), this);
	    		}
	    		
	    		if (characterList.get(i).getVelocity()[1]<0) { //jump
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
	    		//g.setColor(Color.BLACK);
	    		//g.fillRect(characterList.get(i).getPosition()[0], characterList.get(i).getPosition()[1], characterList.get(i).getWidth(),characterList.get(i).getHeight());
	    		newEng.move(characterList.get(i));
	    	}
	    		
    		if ((characterList.get(0)).isAlive() == false) {
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
    				//(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0], (characterList.get(0)).getVelocity()[1]+6});
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
    		if (c == 'd' && (characterList.get(0)).isAlive()) {
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
    		} else if (c == 'w' && (characterList.get(0)).getJump() && (characterList.get(0)).isAlive()) {
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
    		} else if (c == 'a' && (characterList.get(0)).isAlive()) {
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
    		}
    		
    	}
    	
    	public void keyReleased(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd' && characterList.get(0).isAlive()) {
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
    		} else if (c == 'w' && characterList.get(0).isAlive()) {
    			if ((characterList.get(0)).getMotion()[2]) {
    				//(characterList.get(0)).setVelocity(new double[] {(characterList.get(0)).getVelocity()[0], (characterList.get(0)).getVelocity()[1]+6});
    				(characterList.get(0)).setMotion(false, 2);
    			}
    		} else if (c == 'a' && characterList.get(0).isAlive()) {
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
    		} 
    	}
    	
    	public void keyTyped(KeyEvent e) {
    	}
    }
