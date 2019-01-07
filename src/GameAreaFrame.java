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

        GameKeyListener keyListener = new GameKeyListener ();
        this.addKeyListener(keyListener);

        // Focus the frame
        this.requestFocusInWindow();

        // Make the frame visible
        this.setVisible (true);

    } // End of constructor
    
    public void setButtonPressed(String buttonPressed) {
    	this.buttonPressed = buttonPressed;
    }
	
}
