/**
 * [GameAreaFrame.java]
 * Frame that holds all of NullPath game's operations
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 21, 2019
 */

//Since everything is in constructor, to play again after finishing a game, launcher will have to remake the frame

//Graphics & GUI imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.io.IOException;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Util
import java.io.IOException;
import java.util.ArrayList;

import java.awt.image.*;

public class GameAreaFrame extends JFrame implements KeyListener {

    public static void main(String[] args) {
        new GameAreaFrame();
    }

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
    
    GameClient client = null;

    private Player player = null; // Player that is running the game

    Resources resources;

    PhysicsEngine physicsEngine;

    // Constructor
    public GameAreaFrame() {
        super("NullPath");
        //mapIntegration = new MapPlacement();
        // Connect to server
        //new GameClient().go();

        physicsEngine = new PhysicsEngine("contact");

        // Initialize resources object (with characters + stage)
        resources = new Resources();
        resources.setCurrentStage(resources.getStages().get(0));

        // Set screen size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setSize(1720, 760); //Filler values
        this.setResizable(false);

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (client != null) {
                    client.close();
                }
            }
        });

        // Set up the intro panel if start
        /*
        if ((panelCounter == 0) && panelChange) {
            introPanel = new IntroPanel();
            introPanel.addKeyListener(introPanel);
            introPanel.setFocusable(true);

            this.getContentPane().add(introPanel);
            panelChange = false;
        }

        */
        
        
        try {
			gameB = new testFrameFT(resources);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        gameB.setFocusable(true);
        gameB.requestFocus();
        this.getContentPane().add(gameB);
        
        this.requestFocusInWindow();

        // Make the frame visible
        this.setVisible(true);
        
        //this.add(mapIntegration);
        //Deprecated method - to be replaced by stage

        // Add key listener
        //GameKeyListener keyListener = new GameKeyListener();
        //this.addKeyListener(keyListener);

        // Initialize resources object (with characters + stage)

        // Initialize physics object
        // try {
        //physicsEngine = new PhysicsEngine(resources.getStages().get(0).getCollisionMap());
        //} catch (IOException e) {
        //e.printStackTrace();
        //}
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
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

    //START OF LOOP METHOD
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

    }

} //End of class


