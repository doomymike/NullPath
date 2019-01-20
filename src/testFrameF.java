import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPanel;
import java.util.ArrayList;

public class testFrameF extends JPanel implements KeyListener{

		ArrayList<Item> itemList = new ArrayList<Item>();
	
    	ArrayList <Projectile> ProjectileList = new ArrayList<Projectile>();
		
    	
    	Character testPlayer = null;
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
    	
    	public testFrameF() throws FileNotFoundException, IOException {
    		this.setSize(new Dimension(1720, 760));
    		testPlayer = new Character(80, 240, 60, 40, 0);
    		itemList.add(new StationaryPlatform(80, 250, 150, 300));
    		itemList.add(new FanWind(450, 100, 600, 50, 1, 1));
    		itemList.add(new ConveyorBelt(500, 250, 40, 200, 1, 0));
    		itemList.add(new ProjectileLauncher(600, 600,50, 50, false, "RAND"));
    		itemList.add(new CharacterLauncher(1000, 400, 20, 60, 1, 1));
    		itemList.add(new MovingPlatform(100, 150, 50, 150, 500, 100, new double[] {1, 0}, 1));
    		initStart = System.nanoTime()/(Math.pow(10, 9));
    		lastNum = 0;
    		onIA = false;
    		onID = false;
    		newEng = new PhysicsEngine("Bad");
    		contactMap = newEng.retrieveCMap();
    	    newEng.printMap(contactMap);
    	    
    		setFocusable(true);
    		requestFocusInWindow();
            setVisible (true);
    		addKeyListener(this);
    	}
    	
    	public void paintComponent(Graphics g) {
    		  
    		int convSec = (int)(System.nanoTime()/(Math.pow(10, 9)) - initStart);
    		// Call the super class
    	    super.paintComponent(g);
    	    setDoubleBuffered(true);
    	    
	    	for (int i = 0; i < contactMap.length; i++) {
	    		for (int a = 0; a < contactMap[0].length; a++) {
	    			if (contactMap[i][a].equals("1")) {
	    				g.setColor(Color.magenta);
	    				g.drawRect((a+1)*20, (i+1)*20, 20, 20);
	    			} else if (contactMap[i][a].equals("2")) {
	    				g.setColor(Color.PINK);
	    				g.fillRect((a+1)*20, (i+1)*20, 20, 20);
	    			}
	    		}
	    	}
	    	
	    	for (int i = 0; i < contactMap[0].length; i++) {
	    		g.setColor(Color.CYAN);
	    		g.drawRect(1700, 720, 20, 20);
	    	}
    	    
	    	int a = 0;
    	    newEng.contactMapCollision(testPlayer); //Make sure that the grid is overwritten by objects!!!!!
	    	
	    	while (a < itemList.size()) {
	    		if (itemList.get(a) instanceof CharacterLauncher) {
	    			g.setColor(Color.BLACK);
	    		} else if (itemList.get(a) instanceof ProjectileLauncher) {
	    			g.setColor(Color.GREEN);
	    		} else if (itemList.get(a) instanceof Platform) {
	    			g.setColor(Color.DARK_GRAY);
	    		} else if (itemList.get(a) instanceof FanWind) {
	    			g.setColor(Color.BLUE);
	    		} else if (itemList.get(a) instanceof ConveyorBelt) {
	    			g.setColor(Color.CYAN);
	    		} 
	    		g.fillRect(itemList.get(a).getX(), itemList.get(a).getY(), itemList.get(a).getWidth(), itemList.get(a).getHeight());
	    		newEng.checkCollision(testPlayer, itemList.get(a), false);
	    		
	    		if (itemList.get(a) instanceof ProjectileLauncher) {
	    			if (lastNum < convSec) {
	        	    	lastNum = convSec;
	        	    	((ProjectileLauncher)itemList.get(a)).launchProjectile(-3, -6, ProjectileList);
	        	    }
	    		}
	    		
	    		if (itemList.get(a) instanceof MovingPlatform) {
	    			newEng.move(itemList.get(a));
	    		}
	    		a++;
	    	}
	    	
	    	//while loop is used in case items may need to be removed

    	    g.fillRect(testPlayer.getPosition()[0], testPlayer.getPosition()[1], testPlayer.getWidth(), testPlayer.getHeight());
    	    
    	    int c = 0;
    	    while (c < ProjectileList.size()) {
    	    	newEng.move(ProjectileList.get(c));
    	    	g.setColor(Color.RED);
    	    	g.fillOval(ProjectileList.get(c).getX(), ProjectileList.get(c).getY(), ProjectileList.get(c).getRadius(), ProjectileList.get(c).getRadius());
    	    	if (ProjectileList.get(c).getX() < 0 || ProjectileList.get(c).getX() > 1720 || ProjectileList.get(c).getY() < 0 || ProjectileList.get(c).getY() > 760) {
    	    		ProjectileList.remove(c);
    	    		c--;
    	    	}
    	    	c++;
    	    	//g.fillRect(ProjectileList.get(i).getX(), ProjectileList.get(i).getY(), ProjectileList.get(i).getWidth(), ProjectileList.get(i).getHeight());
    	    }
    	    
    	    newEng.move(testPlayer);
    	    repaint();
    	}
    	
    	
    	public void keyPressed(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd') {
    			onID = true;
    			if (testPlayer.getMotion()[1] == false) {
    				if (testPlayer.getHoney()) {
    					testPlayer.setHMotion(true, 1);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+1, testPlayer.getVelocity()[1]});
    				} else if (testPlayer.getIce()){
    					if (testPlayer.getIMotion()) {
    						testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-testPlayer.getLastI() , testPlayer.getVelocity()[1]});
    					}
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+2, testPlayer.getVelocity()[1]});
    					testPlayer.setIMotion(false);
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+2, testPlayer.getVelocity()[1]});
    				}
    				testPlayer.setMotion(true, 1);
    			}
    		} else if (c == 'w' && testPlayer.getJump()) {
    			System.out.println("PRESS");
    			if (testPlayer.getMotion()[2] == false) {
    				/*
    				if(testPlayer.getIMotion()) {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-testPlayer.getLastI() , testPlayer.getVelocity()[1]});
    					testPlayer.setIMotion(false);
    				}
    				*/
    				if (testPlayer.getHoney()) {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]-2.5});
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]-4.5});
    				}
    				testPlayer.setMotion(true, 2);
    			}
    		} else if (c == 'a') {
    			onIA = true;
    			if (testPlayer.getMotion()[0] == false) {
    				if (testPlayer.getHoney()) {
    					testPlayer.setHMotion(true, 0);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-1, testPlayer.getVelocity()[1]});
    				} else if (testPlayer.getIce()){
    					if (testPlayer.getIMotion()) {
    						testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-testPlayer.getLastI() , testPlayer.getVelocity()[1]});
    					}
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-2 , testPlayer.getVelocity()[1]});
    					testPlayer.setIMotion(false);
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-2, testPlayer.getVelocity()[1]});
    				}
    				testPlayer.setMotion(true, 0);
    			}
    		} 
    	}
    	
    	public void keyReleased(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd') {
    			onID = false;
    			if (testPlayer.getMotion()[1]) {
    				if (testPlayer.getHMotion()[1]) {
    					testPlayer.setHMotion(false, 1);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-1, testPlayer.getVelocity()[1]});
    				} else if (testPlayer.getIce() && testPlayer.getIMotion() == false && onIA == false){
    					testPlayer.setIMotion(true);
    					testPlayer.setLastI(testPlayer.getVelocity()[0]*(.33));
    					System.out.println(testPlayer.getVelocity()[0]*(.33));    					
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]*(.33), testPlayer.getVelocity()[1]});
    				} else { 
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-2, testPlayer.getVelocity()[1]});
    				}
    				testPlayer.setMotion(false, 1);
    			}
    		} else if (c == 'w') {
    			if (testPlayer.getMotion()[2]) {
    				//testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]+6});
    				testPlayer.setMotion(false, 2);
    			}
    		} else if (c == 'a') {
    			onIA = false;
    			if (testPlayer.getMotion()[0]) {
    				if (testPlayer.getHMotion()[0]) {
    					testPlayer.setHMotion(false, 0);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+1, testPlayer.getVelocity()[1]});	
    				} else if (testPlayer.getIce() && testPlayer.getIMotion() == false && onID == false) {
    					testPlayer.setIMotion(true);
    					testPlayer.setLastI(testPlayer.getVelocity()[0]*(.33));
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]*(.33), testPlayer.getVelocity()[1]});
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+2, testPlayer.getVelocity()[1]});	
    				}
    				testPlayer.setMotion(false, 0);
    			}
    		} 
    	}
    	
    	public void keyTyped(KeyEvent e) {
    	}
    }