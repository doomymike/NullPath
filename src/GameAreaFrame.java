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

public GameAreaFrame extends JFrame {
	
	private String buttonPressed = "";
	
	// Constructor
    public GameAreaFrame() {
        super("NullPath");

        // Connect to server
        new GameClient().go();
        
        // Set the frame to full screen
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.setResizable (false);

        // Set up the intro panel
        JPanel introPanel = new IntroPanel ();
        this.add (introPanel);

        // Add key listener
        GameKeyListener keyListener = new GameKeyListener ();
        this.addKeyListener(keyListener);

        // Focus the frame
        this.requestFocusInWindow();

        // Make the frame visible
        this.setVisible (true);

    } // End of constructor
    
  //****** Inner Classes for KeyListener ****

    private class GameKeyListener implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == 27) { //esc pressed
            	buttonPressed = "esc";
            } else if (e.getKeyCode() == 13) { //enter pressed
            	buttonPressed = "enter";
            } else if (e.getKeyCode() == 9) { //tab pressed
            	buttonPressed = "tab";
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

            if (e.getKeyCode() == 37) { //Left pressed
            	buttonPressed = "left";
            } else if (e.getKeyCode() == 38) {//Up pressed
            	buttonPressed = "up";
            } else if (e.getKeyCode() == 39) { //Right pressed
            	buttonPressed = "right";
            } else if (e.getKeyCode() == 40) { //Down pressed
            	buttonPressed = "down";
            }
            
        }

    } //End of inner class
    	
}
