
//Graphics & GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Util
import java.util.ArrayList;

public class GameAreaFrame extends JFrame {
	
	private String buttonPressed = "";
	private int panelCounter = 0;
	private boolean panelChange = true;
	private IntroPanel introPanel = null;
	private MainMenuPanel mainMenuPanel = null;
	private MapPlacement mapIntegration = null;
	
	// Constructor
    public GameAreaFrame() {
        super("NullPath");
        mapIntegration = new MapPlacement();
        // Connect to server
        //new GameClient().go();
        
        // Set screen size
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000); //Filler values
        this.setResizable (false);

        // Set up the intro panel if start
        if ((panelCounter == 0) && panelChange) {
        	introPanel = new IntroPanel ();
        	//this.add (introPanel);
        	panelChange = false;
        }
        
        if ((panelCounter == 0) && !panelChange) {
        	if (introPanel.getSelection().equals("start")) {
        		//this.remove(introPanel); // Remove the intro panel
        		mainMenuPanel = new MainMenuPanel(); // Add the main menu panel
        		//this.add(mainMenuPanel);
        		panelCounter = 1;
        	}
        }

        this.add(mapIntegration);
        
        // Add key listener
        GameKeyListener keyListener = new GameKeyListener ();
        this.addKeyListener(keyListener);

        // Focus the frame
        this.requestFocusInWindow();

        // Make the frame visible
        this.setVisible (true);

    } // End of constructor
    
  //****** Inner Classes for KeyListener ****

    class GameKeyListener implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == 27) { //esc pressed
            	buttonPressed = "esc";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	}
            } else if (e.getKeyCode() == 13) { //enter pressed
            	buttonPressed = "enter";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	}
            } else if (e.getKeyCode() == 9) { //tab pressed
            	buttonPressed = "tab";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	}
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

            if (e.getKeyCode() == 37) { //Left pressed
            	buttonPressed = "left";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	}
            } else if (e.getKeyCode() == 38) {//Up pressed
            	buttonPressed = "up";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	}
            } else if (e.getKeyCode() == 39) { //Right pressed
            	buttonPressed = "right";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	}
            } else if (e.getKeyCode() == 40) { //Down pressed
            	buttonPressed = "down";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	}
            }
            
        }

    } //End of inner class
    	
}
