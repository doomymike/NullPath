/**
 * [CharacterSelectPanel.java]
 * Panel that displays the character select screen
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 21, 2019
 */

import java.awt.*;
//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.*;

public class CharacterSelectPanel extends JPanel{

    private int currentHover = 0;
    private String buttonPressed = "";
    private String selection = "";
    private String[] possibleSelections = {"Blue", "Green", "Red", "Yellow"};
    private boolean[] charactersSelected = {false, false, false, false};
    private boolean back = false;
    Resources resource = null;
    
    //Triple colon follows

    //Mouse Position
    private int xPos = 0;
    private int yPos = 0;

    private GameClient client;

    public void paintComponent(Graphics g) {

        //Check to see if anyone has selected blue (if no one previously selected it)
        if (!charactersSelected[0]) {
            if (!client.getCharacterSelection()[0].equals("")) {
                charactersSelected[0] = true; //Mark as blue being selected if not already done so but marked as so in client
                try {
                    for (int i = 0; i < 4; i++) {
                        if (resource.getPlayers().get(i).getName().equals(client.getCharacterSelection()[0])) {
                            resource.getPlayers().get(i).setCharacter(new Character("Blue"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Check to see if anyone has selected green (if no one previously selected it)
        if (!charactersSelected[1]) {
            if (!client.getCharacterSelection()[1].equals("")) {
                charactersSelected[1] = true; //Mark as green being selected if not already done so but marked as so in client
                try {
                    for (int i = 0; i < 4; i++) {
                        if (resource.getPlayers().get(i).getName().equals(client.getCharacterSelection()[1])) {
                            resource.getPlayers().get(i).setCharacter(new Character("Green"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Check to see if anyone has selected red (if no one previously selected it)
        if (!charactersSelected[2]) {
            if (!client.getCharacterSelection()[2].equals("")) {
                charactersSelected[2] = true; //Mark as red being selected if not already done so but marked as so in client
                try {
                    for (int i = 0; i < 4; i++) {
                        if (resource.getPlayers().get(i).getName().equals(client.getCharacterSelection()[2])) {
                            resource.getPlayers().get(i).setCharacter(new Character("Red"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Check to see if anyone has selected yellow (if no one previously selected it)
        if (!charactersSelected[3]) {
            if (!client.getCharacterSelection()[3].equals("")) {
                charactersSelected[3] = true; //Mark as yellow being selected if not already done so but marked as so in client
                try {
                    for (int i = 0; i < 4; i++) {
                        if (resource.getPlayers().get(i).getName().equals(client.getCharacterSelection()[3])) {
                            resource.getPlayers().get(i).setCharacter(new Character("Yellow"));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //Override mouseListener methods
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                xPos = e.getX();
                yPos = e.getY();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt){
                setLocation(evt.getXOnScreen()-xPos, evt.getYOnScreen()-yPos);
            }
        });

        // Call the super class
        super.paintComponent(g);
        setDoubleBuffered(true);
        this.setLayout(null);

        //Setup all buttons for options
        JButton charOne = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("BlueFrame.png"))));
        JButton charTwo = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("GreenFrame.png"))));
        JButton charThree = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("RedFrame.png"))));
        JButton charFour = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("YellowFrame.png"))));
        JLabel select = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Select.png"))));

        JLabel flow = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("WaterFlow.gif"))));

        this.add(select);
        select.setLocation(155,50);
        select.setSize(600,48);

        this.add(charOne);
        charOne.setLocation(150,130);
        charOne.setSize(256,256);
        charOne.setContentAreaFilled(false);
        charOne.setBorder(null);

        charOne.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                //charOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("BlueFrame.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                charOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("BlueSelect.png"))));
                if ((!charactersSelected[0]) && selection.equals("")) { //If blue is not already selected and no character has been selected
                    System.out.println("Player has selected blue");
                    selection = "Blue";
                    client.setCharacterSelected("Blue");
                }
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Draw options button (rectangles right now)
        this.add(charTwo);
        charTwo.setLocation(550,130);
        charTwo.setSize(256,256);
        charTwo.setContentAreaFilled(false);
        charTwo.setBorder(null);

        charTwo.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){

            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                //charTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("GreenFrame.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                charTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("GreenSelect.png"))));
                if ((!charactersSelected[1]) && selection.equals("")) { //If green is not already selected and no character has been selected
                    System.out.println("Player has selected green");
                    selection = "Green";
                    client.setCharacterSelected("Green");
                }
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Draw quit button (rectangles right now)
        this.add(charThree);
        charThree.setLocation(150,430);
        charThree.setSize(256,256);
        charThree.setContentAreaFilled(false);
        charThree.setBorder(null);

        charThree.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                //charThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("RedFrame.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                charThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("RedSelect.png"))));
                if ((!charactersSelected[2]) && selection.equals("")) { //If red is not already selected and no character has been selected
                    System.out.println("Player has selected red");
                    selection = "Red";
                    client.setCharacterSelected("Red");
                }
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Draw quit button (rectangles right now)
        this.add(charFour);
        charFour.setLocation(550,430);
        charFour.setSize(256,256);
        charFour.setContentAreaFilled(false);
        charFour.setBorder(null);

        charFour.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                //charFour.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("YellowFrame.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                charFour.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("YellowSelect.png"))));
                if ((!charactersSelected[3]) && selection.equals("")) { //If yellow is not already selected and no character has been selected
                    System.out.println("Player has selected yellow");
                    selection = "Yellow";
                    client.setCharacterSelected("Yellow");
                }
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });


        this.add(flow);
        flow.setLocation(0,-100);
        flow.setSize(2000,1020);

        // Repaint
        repaint();

    }

    /**
     * setResources
     * Passes in reference to a resources object
     * @param resource, the Resources object being passed into the panel
     */
    public void setResources(Resources resource){
        this.resource = resource;
    } //End of setResources

    /**
     * setClient
     * Passes in reference to a client object
     * @param client, the GameClient object being passed into the panel
     */
    public void setClient(GameClient client) {
        this.client = client;
    } //End of setClient
 
    public void setButtonPressed(String buttonPressed) {
        this.buttonPressed = buttonPressed;
        this.buttonPressed = buttonPressed;
        if (buttonPressed.equals("right")) {
            if (currentHover == 3) {
                currentHover = 0;
            } else {
                currentHover += 1;
            }
        } else if (buttonPressed.equals("left")) {
            if (currentHover == 0) {
                currentHover = 3;
            } else {
                currentHover -= 1;
            }
        } else if (buttonPressed.equals("enter")) {
            selection = possibleSelections[currentHover]; // User uses enter key to select option from menu
        }
    }

    public String getSelection() {
        return selection;
    }

}
