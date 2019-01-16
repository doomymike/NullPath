// init player objects somewhere !!!

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
import java.io.IOException;
import java.util.ArrayList;

import java.awt.image.*;

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

    Resources resources;

    PhysicsEngine physicsEngine;

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
            this.getContentPane().add (introPanel);
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

        // Initialize resources object (with characters + stage)
        resources = new Resources();

        // Initialize physics object
        physicsEngine = new PhysicsEngine(resources.getStages().get(0).getCollisionMap());

        while(true) {

            if ((panelCounter == 0) && !panelChange) {
                if (introPanel.getNext()) {
                    this.getContentPane().remove(introPanel); // Remove the intro panel
                    mainMenuPanel = new MainMenuPanel(); // Add the main menu panel
                    this.getContentPane().add(mainMenuPanel);
                    System.out.println("change to main menu panel");
                    panelCounter = 1;
                    this.revalidate();
                    repaint();
                }
            }

            // Switch to the screen chosen from main menu
            if (panelCounter == 1) {
                //Changed get Selection
                if (mainMenuPanel.getSelection()==0) {
                    this.getContentPane().remove(mainMenuPanel); // Remove main menu panel
                    characterSelectPanel = new CharacterSelectPanel(); // Add the game panel
                    this.getContentPane().add(characterSelectPanel);
                    characterSelectPanel.setResources(resources); // Pass in reference to resources object (so players can be assigned characters)
                    panelCounter = 5;
                    this.revalidate();
                    repaint();
                } else if (mainMenuPanel.getSelection()==1) {
                    this.getContentPane().remove(mainMenuPanel); // Remove main menu panel
                    instructionsPanel = new InstructionsPanel(); // Add the game panel
                    this.getContentPane().add(instructionsPanel);
                    panelCounter = 3;
                    this.revalidate();
                    repaint();
                } else if (mainMenuPanel.getSelection()==2) {
                    this.getContentPane().remove(mainMenuPanel); // Remove main menu panel
                    creditsPanel = new CreditsPanel(); // Add the game panel
                    this.getContentPane().add(creditsPanel);
                    panelCounter = 4;
                    this.revalidate();
                    repaint();
                }
            }

            // Go back to main menu from credits
            if (panelCounter == 4) {
                if (creditsPanel.getSelection().equals("back")) {
                    this.getContentPane().remove(creditsPanel); // Remove main menu panel
                    mainMenuPanel = new MainMenuPanel(); // Add the game panel
                    this.getContentPane().add(mainMenuPanel);
                    panelCounter = 1;
                    this.revalidate();
                    repaint();
                }
            }

            // Go back to main menu from instructions
            if (panelCounter == 3) {
                if (instructionsPanel.getSelection().equals("back")) {
                    this.getContentPane().remove(instructionsPanel); // Remove main menu panel
                    mainMenuPanel = new MainMenuPanel(); // Add the game panel
                    this.getContentPane().add(mainMenuPanel);
                    panelCounter = 1;
                    this.revalidate();
                    repaint();
                }
            }

            // Character select screen (back button and character selection)
            if (panelCounter == 5) {

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
                    panelCounter = 2;
                    this.revalidate();
                    repaint();
                } else if (characterSelectPanel.getSelection().equals("back")) {
                    this.getContentPane().remove(characterSelectPanel);
                    mainMenuPanel = new MainMenuPanel();
                    this.getContentPane().add(mainMenuPanel);
                    panelCounter = 1;
                    this.revalidate();
                    repaint();
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
                if (panelCounter == 2) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 3) {
                    instructionsPanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 4) {
                    creditsPanel.setButtonPressed(buttonPressed);
                }
            } else if (e.getKeyCode() == 10) { //enter pressed
                buttonPressed = "enter";
                System.out.println("enter pressed");
                if (panelCounter == 2) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 3) {
                    instructionsPanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 4) {
                    creditsPanel.setButtonPressed(buttonPressed);
                }
            } else if (e.getKeyCode() == 9) { //tab pressed
                buttonPressed = "tab";
                if (panelCounter == 2) {
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
                if (panelCounter == 2) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 3) {
                    instructionsPanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 4) {
                    creditsPanel.setButtonPressed(buttonPressed);
                }
            } else if (e.getKeyCode() == 38) {//Up pressed
                buttonPressed = "up";
                if (panelCounter == 2) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 3) {
                    instructionsPanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 4) {
                    creditsPanel.setButtonPressed(buttonPressed);
                }
            } else if (e.getKeyCode() == 39) { //Right pressed
                buttonPressed = "right";
                if (panelCounter == 2) {
                    gamePanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 3) {
                    instructionsPanel.setButtonPressed(buttonPressed);
                } else if (panelCounter == 4) {
                    creditsPanel.setButtonPressed(buttonPressed);
                }
            } else if (e.getKeyCode() == 40) { //Down pressed
                buttonPressed = "down";
                
                if (panelCounter == 2) {
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
