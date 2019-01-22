/**
 * [GameAreaFrameTE.java]
 * Main frame for NullPath game that holds game flow
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

//java imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.*;

public class GameAreaFrameTE extends JFrame implements KeyListener {

    private String buttonPressed = "";
    private int panelCounter = 0;
    // Intro - 0, Main menu - 1, Instructions/Options - 2, Character Select - 3, Game - 4, ItemSelectBox - 5, Score/Credits - 6, login - 7
    private boolean panelChange = true;
    private IntroPanel introPanel = null;
    private MainMenuPanel mainMenuPanel = null;
    private GamePanel gamePanel = null;
    private InstructionsPanel instructionsPanel = null;
    private CreditsPanel creditsPanel = null;
    private CharacterSelectPanel characterSelectPanel = null;
    private ClientLogin login = null;
    private MapPlacement mapIntegration = null;
    private testFrameFT gameB = null;
    private ItemMovePanel itemM = null;

    private ItemBoxPanel itemBoxPanel = null;
    private PlatformPanel platformPanel = null;
    private boolean gameEnd= false;
    
    GameClient client = new GameClient();

    private Player player = null; // Player that is running the game

    Resources resources;

    PhysicsEngine physicsEngine;

    /**
     * keyTyped
     * Performs action if corresponding key is entered
     * @param e, KeyEvent for key typed
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyCode() == 27) {
            System.out.println("Escape detected");
            //Return to MenuPanel, from Select
            if (panelCounter == 2) {
                this.getContentPane().remove(characterSelectPanel); // Remove the select panel
                mainMenuPanel = new MainMenuPanel(); // Add the mainmenu panel
                this.getContentPane().add(mainMenuPanel);
                System.out.println("back to main menu panel");
                panelCounter = 1;
                this.revalidate();
                repaint();
            }
            //Return to IntroPanel, from MenuPanel
            if (panelCounter == 1) {
                this.getContentPane().remove(mainMenuPanel); // Remove the select panel
                introPanel = new IntroPanel(); // Add the mainmenu panel
                this.getContentPane().add(introPanel);
                System.out.println("back to intro panel");
                panelCounter = 0;
                this.revalidate();
                repaint();
            }
        }
    } //End of keyTyped

    /**
     * keyReleased
     * Performs action if corresponding key is released
     * @param e, KeyEvent for key released
     */
    @Override
    public void keyReleased(KeyEvent e) {
    } //End of keyReleased

    /**
     * keyPressed
     * Performs action if corresponding key is pressed
     * @param e, KeyEvent for key pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
    } //End of keyPressed

    // Constructor
    public GameAreaFrameTE() {
        super("NullPath");
        physicsEngine = new PhysicsEngine("contact");

        // Initialize resources object (with stage)
        resources = new Resources();
        resources.setCurrentStage(resources.getStages().get(0));

        // Set screen size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1720, 760);
        this.setResizable(false);

        // Set up the intro panel if start
        if ((panelCounter == 0) && panelChange) {
            introPanel = new IntroPanel();
            introPanel.addKeyListener(introPanel);
            introPanel.setFocusable(true);
            this.getContentPane().add(introPanel);
            panelChange = false;
        }

        this.requestFocusInWindow();

        // Make the frame visible
        this.setVisible(true);

        // Add key listener
        GameKeyListener keyListener = new GameKeyListener();
        this.addKeyListener(keyListener);

        // Focus the frame
        this.setFocusable(true);

    } //End of constructor
    
    //****** Inner Classes for KeyListener ****

    class GameKeyListener implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println(e.getKeyCode());
            if (e.getKeyCode() == 27) { //esc pressed
                buttonPressed = "esc";
                if (panelCounter == 1) {
                    System.out.println("Back from Main Menu");
                } else if (panelCounter == 2){
                    System.out.println("Back from Instructions");
                }else if (panelCounter == 3) {
                    System.out.println("Back from Character Select");
                } else if (panelCounter == 6) {
                    System.out.println("Back from Credits");
                }
            } else if (e.getKeyCode() == 10) { //enter pressed
                buttonPressed = "enter";
                System.out.println("enter pressed");
                if (panelCounter == 1) {
                    System.out.println("Enter from Main Menu");
                } else if (panelCounter == 2) {
                    System.out.println("Enter from Instructions");
                } else if (panelCounter == 3) {
                    System.out.println("Enter from Character Select");
                }
            } else if (e.getKeyCode() == 9) { //tab pressed
                buttonPressed = "tab";
                if (panelCounter == 1) {
                    System.out.println("Tab from Main Menu");
                } else if (panelCounter == 2) {
                    System.out.println("Tab from Instructions");
                } else if (panelCounter == 3) {
                    System.out.println("Character Select");
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
                if (panelCounter == 4) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 2) {
                    System.out.println("Left from Instructions");
                } else if (panelCounter == 3) {
                    System.out.println("Left from Character Select");
                }
            } else if (e.getKeyCode() == 38) {//Up pressed
                buttonPressed = "up";
                if (panelCounter == 4) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 2) {
                    System.out.println("Up from Instructions");
                } else if (panelCounter == 3) {
                    System.out.println("Up from Character Select");
                }
            } else if (e.getKeyCode() == 39) { //Right pressed
                buttonPressed = "right";
                if (panelCounter == 4) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 2) {
                    System.out.println("Right from Instructions");
                } else if (panelCounter == 3) {
                    System.out.println("Right from Character Select");
                }
            } else if (e.getKeyCode() == 40) { //Down pressed
                buttonPressed = "down";

                if (panelCounter == 4) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 2) {
                    System.out.println("Down from Instructions");
                } else if (panelCounter == 3) {
                    System.out.println("Down from Character Select");
                }
            }

        }

    } //End of inner class

    /**
     * gameLoop
     * Loop for non-gameplay aspects, includes character select
     */
    public void gameLoop(){
        
        //Switch to Main Menu from Intro
        if ((panelCounter == 0) && !panelChange) {
            if (introPanel.getNext()) {
                this.getContentPane().remove(introPanel); // Remove the intro panel
                mainMenuPanel = new MainMenuPanel(); // Add the main menu panel
                this.getContentPane().add(mainMenuPanel);
                System.out.println("change to main menu panel");
                panelCounter = 1;   //1 IS MAIN MENU
                this.revalidate();
                repaint();
            }
        }else if (panelCounter == 1) {
            if (mainMenuPanel.getSelection() == 0) {
                client = new GameClient();
                new ClientLogin(client, resources); // Open client login above main menu
                panelCounter = 7;
            } else if (mainMenuPanel.getSelection() == 1) {
                this.getContentPane().remove(mainMenuPanel); // Remove main menu panel
                instructionsPanel = new InstructionsPanel(); // Add the game panel
                this.getContentPane().add(instructionsPanel);
                panelCounter = 2;   //2 IS INSTRUCTIONS
                this.revalidate();
                repaint();
            } else if (mainMenuPanel.getSelection() == 2) {
                System.out.println("Program Ended");
                this.dispose();
                System.exit(0);
            }
        }else if (panelCounter == 7) { // On login screen
            if (resources.getPlayers().size() == 4) { // Check that all 4 player objects have been made in resources
                for (int i = 0; i < 4; i++) {
                    if (resources.getPlayers().get(i).getName().equals(client.getUsername())) {
                        player = resources.getPlayers().get(i); // Set the player corresponding to this specific instance of the game
                    }
                }
                this.getContentPane().remove(mainMenuPanel); // Remove main menu panel
                characterSelectPanel = new CharacterSelectPanel(); // Add the game panel
                this.getContentPane().add(characterSelectPanel);
                characterSelectPanel.setResources(resources); // Pass in reference to resources object (so players can be assigned characters)
                characterSelectPanel.setClient(client);
                panelCounter = 3;   //3 IS GAME PANEL
                this.revalidate();
                repaint();
            }
        }else if (panelCounter == 6) {
            if (creditsPanel.getSelection().equals("back")) {
                this.getContentPane().remove(creditsPanel); // Remove main menu panel
                mainMenuPanel = new MainMenuPanel(); // Add the game panel
                this.getContentPane().add(mainMenuPanel);
                panelCounter = 1;   //1 IS MAIN MENU
                this.revalidate();
                repaint();
            }
        }else if (panelCounter == 3) {
            try {
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
            } catch (IOException e) {
                // Shouldn't ever run (unless images are improperly labelled) - empty catch
            }

            // Change to game panel or back to menu panel
            if ((characterSelectPanel.getSelection().equals("blue")) || (characterSelectPanel.getSelection().equals("blue")) || (characterSelectPanel.getSelection().equals("blue")) || (characterSelectPanel.getSelection().equals("blue"))) {
                this.getContentPane().remove(characterSelectPanel);
                gamePanel = new GamePanel();
                this.getContentPane().add(gamePanel);
                gamePanel.setResources(resources); // Pass in reference to resources object
                panelCounter = 4; //4 IS GAME PANEL
                this.revalidate();
                repaint();
            } else if (characterSelectPanel.getSelection().equals("back")) {
                this.getContentPane().remove(characterSelectPanel);
                mainMenuPanel = new MainMenuPanel();
                this.getContentPane().add(mainMenuPanel);
                panelCounter = 1; //1 IS MAIN MENU
                this.revalidate();
                repaint();
            }

        }

    } //End of gameLoop
    
    
    /**
     * roundLoop
     * Circulating loop (for gameply - item box, placement, platforming)
     */
    public void roundLoop(){
        //Every round consists of a: itemBoxPanel, platformPanel, scorePanel
        
        //Check if no one has won yet
        while(!gameEnd) {
            //Start by removing the itemBoxPanel, after checking if platformPanel doesn't exist yet
            if (panelCounter == 5 && itemM == null) {
                this.getContentPane().remove(itemBoxPanel);
                itemBoxPanel = null;
                itemM = new ItemMovePanel(resources, physicsEngine, client);
                this.getContentPane().add(itemM);
                panelCounter = 7; //7 IS PLACE PANEL
                this.revalidate();
                repaint();
            }

            //Allow players to place objects via item
            if (panelCounter == 7 && platformPanel == null) {
                this.getContentPane().remove(itemM);
                itemM = null;
                platformPanel = new PlatformPanel(resources, client);
                this.getContentPane().add(platformPanel);
                panelCounter = 4; //4 IS GAME PANEL
                this.revalidate();
                repaint();
            }
            //Check if round is over, then switch back to itemBoxPanel
            if (panelCounter == 4) {
                if (platformPanel.endGame()) {
                    //Check if anybody won
                    for (int i = 0; i < 4; i++) {
                        if (resources.getPlayers().get(i).getScore() >= 30) {
                            gameEnd = true;
                        }
                    }
                    //Call Client Clear
                    client.clearGameValues();

                    this.getContentPane().remove(platformPanel);
                    platformPanel = null;
                    itemBoxPanel = new ItemBoxPanel();
                    this.getContentPane().add(itemBoxPanel);
                    panelCounter = 5; //5 IS ITEM BOX PANEL
                    this.revalidate();
                    repaint();

                }
            }
        }
    }

} //End of class
