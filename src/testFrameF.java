import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.ArrayList;

public class testFrameF extends JPanel implements KeyListener{

    	Character testPlayer = null;
    	PhysicsEngine newEng = null;
    	StationaryPlatform plat = null;
    	FanWind wind = null;
    	ConveyorBelt track = null;
    	ArrayList <Projectile> ProjectileList = new ArrayList<Projectile>();
    	ProjectileLauncher launcher = null;
    	double initStart;
    	int lastNum;
    	
    	public testFrameF() {
    		this.setPreferredSize(new Dimension(1000, 1000));
    		testPlayer = new Character(50, 450, 60, 40, 0);
    		plat = new StationaryPlatform(50, 510, 30, 300);
    		plat.setIce(true);
    		wind = new FanWind(400, 200, 600, 50, 1, 1);
    		track = new ConveyorBelt(500, 250, 40, 200, 1, 0);
    		launcher = new ProjectileLauncher(600, 600,50, 50, false, "RAND");
    		initStart = System.nanoTime()/(Math.pow(10, 9));
    		lastNum = 0;
    		
    		System.out.println(testPlayer.getPosition()[0] + " AND "+testPlayer.getPosition()[1] + ", "+(testPlayer.getPosition()[0]+testPlayer.getWidth())+" AND " + (testPlayer.getPosition()[1]+testPlayer.getHeight()));
    		System.out.println(plat.getX() + " AND "+plat.getY() + ", "+(plat.getX()+plat.getWidth())+" AND " + (plat.getY()+plat.getHeight()));
    		newEng = new PhysicsEngine();
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

    	    g.setColor(Color.GREEN);
    	    g.fillRect(launcher.getX(), launcher.getY(), launcher.getWidth(), launcher.getHeight());
    	    g.setColor(Color.DARK_GRAY);
    	    g.fillRect(plat.getX(), plat.getY(), plat.getWidth(), plat.getHeight());
    	    g.setColor(Color.BLUE);
    	    g.fillRect(wind.getX(), wind.getY(), wind.getWidth(), wind.getHeight());
    	    g.setColor(Color.CYAN);
    	    g.fillRect(track.getX(), track.getY(), track.getWidth(), track.getHeight());
    	    g.setColor(Color.RED);
    	    newEng.move(testPlayer);
    	    g.fillRect(testPlayer.getPosition()[0], testPlayer.getPosition()[1], testPlayer.getWidth(), testPlayer.getHeight());
    	    
    	    for (int i = 0; i < ProjectileList.size(); i++) {
    	    	newEng.move(ProjectileList.get(i));
    	    	g.setColor(Color.RED);
    	    	g.fillRect(ProjectileList.get(i).getX(), ProjectileList.get(i).getY(), ProjectileList.get(i).getWidth(), ProjectileList.get(i).getHeight());
    	    }
    	    
    	    if (newEng.checkCollision(testPlayer, plat, false) || newEng.checkCollision(testPlayer, track, false)) { //Sub with list of objects, checking instants of platforms
    	    	testPlayer.setJump(true);
    	    } else {
    	    	testPlayer.setJump(false);
    	    }

    	    newEng.checkCollision(testPlayer, wind, false);
    	    newEng.checkCollision(testPlayer, track, false);
    	    if (lastNum < convSec) {
    	    	lastNum = convSec;
    	    	launcher.launchProjectile(-3, 0, ProjectileList);
    	    }
    	    repaint();
    	}
    	
    	
    	public void keyPressed(KeyEvent e) {
    		char c = e.getKeyChar();
    		if (c == 'd') {
    			if (testPlayer.getMotion()[1] == false) {
    				if (testPlayer.getHoney()) {
    					testPlayer.setHMotion(true, 1);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+1, testPlayer.getVelocity()[1]});
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+2, testPlayer.getVelocity()[1]});
    				}
    				testPlayer.setMotion(true, 1);
    			}
    		} else if (c == 'w' && testPlayer.getJump()) {
    			if (testPlayer.getMotion()[2] == false) {
    				if (testPlayer.getHoney()) {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]-3});
    				} else {
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0], testPlayer.getVelocity()[1]-6});
    				}
    				testPlayer.setMotion(true, 2);
    			}
    		} else if (c == 'a') {
    			if (testPlayer.getMotion()[0] == false) {
    				if (testPlayer.getHoney()) {
    					System.out.println("STARTD");
    					testPlayer.setHMotion(true, 0);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-1, testPlayer.getVelocity()[1]});
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
    			if (testPlayer.getMotion()[1]) {
    				if (testPlayer.getHMotion()[1]) {
    					System.out.println("PLS");
    					testPlayer.setHMotion(false, 1);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]-1, testPlayer.getVelocity()[1]});
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
    			if (testPlayer.getMotion()[0]) {
    				if (testPlayer.getHMotion()[0]) {
    					testPlayer.setHMotion(false, 0);
    					testPlayer.setVelocity(new double[] {testPlayer.getVelocity()[0]+1, testPlayer.getVelocity()[1]});	
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