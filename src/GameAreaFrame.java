package heckpath;
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
	
	public static void main(String[] args) {
		new GameAreaFrame();
	}
	
	private String buttonPressed = "";
	private int panelCounter = 0;
		// Intro - 0, Main menu - 1, Game - 2, Instructions - 3, Credits - 4, CharacterSelect - 5
	private boolean panelChange = true;
	private IntroPanel introPanel = null;
	private MainMenuPanel mainMenuPanel = null;
	private GamePanel gamePanel = null;
	private InstructionsPanel instructionsPanel = null;
	private CreditsPanel creditsPanel = null;
	private CharacterSelectPanel characterSelectPanel = null;
	private MapPlacement mapIntegration = null;
	
	private Player player = null; // Player that is running the game
	
	// Constructor
    public GameAreaFrame() {
        super("NullPath");
        //mapIntegration = new MapPlacement();
        // Connect to server
        //new GameClient().go();
        
        // Set screen size
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,1000); //Filler values
        this.setResizable (false);

        // Set up the intro panel if start
        if ((panelCounter == 0) && panelChange) {
        	introPanel = new IntroPanel ();
        	this.add (introPanel);
        	panelChange = false;
        }
        

        //this.add(mapIntegration);
        //Deprecated method - to be replaced by stage
        
        // Add key listener
        GameKeyListener keyListener = new GameKeyListener ();
        this.addKeyListener(keyListener);

        // Focus the frame
        this.requestFocusInWindow();

        // Make the frame visible
        this.setVisible (true);
        
        while(true) {

            if ((panelCounter == 0) && !panelChange) {
            	System.out.println(introPanel.getSelection());
            	if (introPanel.getSelection().equals("start")) {
            		System.out.println("idk");
            		this.remove(introPanel); // Remove the intro panel
            		mainMenuPanel = new MainMenuPanel(); // Add the main menu panel
            		this.add(mainMenuPanel);
            		System.out.println("change to main menu panel");
            		panelCounter = 1;
            	}
            }
            
    	    // Switch to the screen chosen from main menu
    	    if (panelCounter == 1) {
    		    if (mainMenuPanel.getSelection().equals("Start Game")) {
    			    this.remove(mainMenuPanel); // Remove main menu panel
    			    characterSelectPanel = new CharacterSelectPanel(); // Add the game panel
    			    this.add(characterSelectPanel);
    			    panelCounter = 2;
    		    } else if (mainMenuPanel.getSelection().equals("Instructions")) {
    			    this.remove(mainMenuPanel); // Remove main menu panel
    			    instructionsPanel = new InstructionsPanel(); // Add the game panel
    			    this.add(instructionsPanel);
    			    panelCounter = 3;
    		    } else if (mainMenuPanel.getSelection().equals("Credits")) {
    			    this.remove(mainMenuPanel); // Remove main menu panel
    			    creditsPanel = new CreditsPanel(); // Add the game panel
    			    this.add(creditsPanel);
    			    panelCounter = 4;
    		    }
    	    }
    	    
    	    // Go back to main menu from credits
    	    if (panelCounter == 4) {
    		    if (creditsPanel.getSelection().equals("back")) {
    			    this.remove(creditsPanel); // Remove main menu panel
    			    mainMenuPanel = new MainMenuPanel(); // Add the game panel
    			    this.add(mainMenuPanel);
    			    panelCounter = 1;
    		    }
    	    }
    	    
    	    // Go back to main menu from instructions
    	    if (panelCounter == 3) {
    		    if (instructionsPanel.getSelection().equals("back")) {
    			    this.remove(instructionsPanel); // Remove main menu panel
    			    mainMenuPanel = new MainMenuPanel(); // Add the game panel
    			    this.add(mainMenuPanel);
    			    panelCounter = 1;
    		    }
    	    }
    	    
    	    // Character select screen (back button and character selection)
    	    if (panelCounter == 5) {
    	    	
    	    	// Character assignment
    	    	if (characterSelectPanel.getSelection().equals("blue")) {
    	    		player.setCharacter(new Character("blue"));
    	    		// add to resources object?
    	    	} else if (characterSelectPanel.getSelection().equals("green")) {
    	    		player.setCharacter(new Character("green"));
    	    	} else if (characterSelectPanel.getSelection().equals("red")) {
    	    		player.setCharacter(new Character("green"));
    	    	} else if (characterSelectPanel.getSelection().equals("yellow")) {
    	    		player.setCharacter(new Character("green"));
    	    	}
    	    	
    	    	// Change to game panel or back to menu panel
    	    	if ((characterSelectPanel.getSelection().equals("blue")) || (characterSelectPanel.getSelection().equals("blue")) || (characterSelectPanel.getSelection().equals("blue")) || (characterSelectPanel.getSelection().equals("blue"))) {
    	    		this.remove(characterSelectPanel);
    	    		gamePanel = new GamePanel();
    	    		this.add(gamePanel);
    	    		panelCounter = 2;
    	    	} else if (characterSelectPanel.getSelection().equals("back")) {
    	    		this.remove(characterSelectPanel);
    	    		mainMenuPanel = new MainMenuPanel();
    	    		this.add(mainMenuPanel);
    	    		panelCounter = 1;
    	    	}
    	    	
    	    }
    	    
        }

    } // End of constructor
    
  //****** Inner Classes for KeyListener ****

    class GameKeyListener implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {
        	System.out.println(e.getKeyCode());
            if (e.getKeyCode() == 27) { //esc pressed
            	buttonPressed = "esc";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	} else if (panelCounter == 1) {
			mainMenuPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 2) {
			gamePanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 3) {
			instructionsPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 4) {
			creditsPanel.setButtonPressed(buttonPressed);
		}
            } else if (e.getKeyCode() == 10) { //enter pressed
            	buttonPressed = "enter";
            	System.out.println("enter pressed");
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	} else if (panelCounter == 1) {
			mainMenuPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 2) {
			gamePanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 3) {
			instructionsPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 4) {
			creditsPanel.setButtonPressed(buttonPressed);
		}
            } else if (e.getKeyCode() == 9) { //tab pressed
            	buttonPressed = "tab";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	} else if (panelCounter == 1) {
			mainMenuPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 2) {
			gamePanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 3) {
			instructionsPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 4) {
			creditsPanel.setButtonPressed(buttonPressed);
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
            	} else if (panelCounter == 1) {
			mainMenuPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 2) {
			gamePanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 3) {
			instructionsPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 4) {
			creditsPanel.setButtonPressed(buttonPressed);
		}
            } else if (e.getKeyCode() == 38) {//Up pressed
            	buttonPressed = "up";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	} else if (panelCounter == 1) {
			mainMenuPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 2) {
			gamePanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 3) {
			instructionsPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 4) {
			creditsPanel.setButtonPressed(buttonPressed);
		}
            } else if (e.getKeyCode() == 39) { //Right pressed
            	buttonPressed = "right";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	} else if (panelCounter == 1) {
			mainMenuPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 2) {
			gamePanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 3) {
			instructionsPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 4) {
			creditsPanel.setButtonPressed(buttonPressed);
		}
            } else if (e.getKeyCode() == 40) { //Down pressed
            	buttonPressed = "down";
            	if (panelCounter == 0) {
            		introPanel.setButtonPressed(buttonPressed);
            	} else if (panelCounter == 1) {
			mainMenuPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 2) {
			gamePanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 3) {
			instructionsPanel.setButtonPressed(buttonPressed);
		} else if (panelCounter == 4) {
			creditsPanel.setButtonPressed(buttonPressed);
		}
            }
            
        }

    } //End of inner class
    	
}
