/**
 * [GameClient.java]
 * Client that connects to the server for the NullPath Game
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 21, 2019
 */

// something to close it

// remember to reset stuff that happens multiple times (like gameplay stuff, item placement stuff, item selection) after a round ends

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient implements Runnable{

    Socket mySocket; //socket for connection
    BufferedReader input; //reader for network stream
    PrintWriter output;  //printwriter for network output
    boolean running; //thread status via boolean
    private String username;
    JButton sendButton;
	JTextField typeField;
	JFrame window;
	JPanel southPanel;

	// for username setting (and setting of usernames of all people connected)
	private SimpleLinkedList<String> users = new SimpleLinkedList<>();

	// for character selection
	private String characterSelected = "";
	
	// store all players' selections
	private String[] characterSelection = new String[4]; //Will store the name of the user in the corresponding slot (to their character selection)
	//Array: {Blue, Green, Red, Yellow}

	// for selection of items in item box
	private String itemSelected = "";
	private String[] itemsHeld = new String[4];

	// for mouse hovering when placing items
	private String[] itemPlacementCoordinates = new String[2];
	private String[][] allItemPlacementCoordinates = new String[4][2];

	// for running of game
	private String characterMovement = "";
	private SimpleQueue<String> movementInputs = new SimpleQueue<>(); //stores inputs by user
	private SimpleQueue<String>[] gameplayInputs = new SimpleQueue[4]; //stores inputs by each player

    /**
     * go
     * This method connects to server and checks for messages
     */
    public void go(){

        // Make connection ------------------------------------------------------
        System.out.println("Attempting to make a connection..");

        try {
            mySocket = new Socket("127.0.0.1",5000); //attempt socket connection (local address). This will wait until a connection is made

            InputStreamReader stream1= new InputStreamReader(mySocket.getInputStream()); //Stream for network input
            input = new BufferedReader(stream1);

            output = new PrintWriter(mySocket.getOutputStream()); //assign printwriter to network stream

			running = true; //Flag as running

        } catch (IOException e) {  //connection error occured
            System.out.println("Connection to Server Failed");
            e.printStackTrace();
        }

        System.out.println("Connection made.");

        // ------------------------------------------------------------------------

		/*
        window = new JFrame("Chat Client");
	    southPanel = new JPanel();
	    southPanel.setLayout(new GridLayout(2,0));
    
	    sendButton = new JButton("SEND");
	    
	    sendButton.addActionListener(new buttonListener());
	    
	    JLabel errorLabel = new JLabel("");
	    
	    typeField = new JTextField(10);
	    
	    southPanel.add(typeField);
	    southPanel.add(sendButton);
	    southPanel.add(errorLabel);
        //Window listener needed in frame - make sure to tell client that player is exiting (so things can be closed)

        //tells server if exiting program
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                output.println("/exit");
                output.flush();
            }
        });
        */

    } //End of go()

	public void run() {

		// Checks for incoming commands
		while (running){

			// look for command sent from server
			try {
				if (input.ready()) { //check for an incoming message
					String temp = input.readLine();
					if (temp.substring(0, temp.indexOf(":::")).equals("character selection")) {

						if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1) + 3).equals("Blue")) {
							if (characterSelection[0] == null) { //Ensure no one else selected the character
								characterSelection[0] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						} else if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1)).equals("Green")) {
							if (characterSelection[1] == null) { //Ensure no one else selected the character
								characterSelection[1] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						} else if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1) + 3).equals("Red")) {
							if (characterSelection[2] == null) { //Ensure no one else selected the character
								characterSelection[2] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						} else if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1) + 3).equals("Yellow")) {
							if (characterSelection[3] == null) { //Ensure no one else selected the character
								characterSelection[3] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						}

					} else if (temp.substring(0, temp.indexOf(":::")).equals("user connected")) {

						users.add(temp.substring(temp.indexOf(":::") + 3));

					} else if (temp.substring(0, temp.indexOf(":::")).equals("item selected")) {

						if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[0])) { //If username is the username of the player playing as blue
							itemsHeld[0] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[1])) { //If username is the username of the player playing as green
							itemsHeld[1] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[2])) { //If username is the username of the player playing as red
							itemsHeld[2] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[3])) { //If username is the username of the player playing as yellow
							itemsHeld[3] = temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::", temp.indexOf(":::")+1));
						}

					} else if (temp.substring(0, temp.indexOf(":::")).equals("character movement")) {

						if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[0])) { //If username is the username of the player playing as blue
							gameplayInputs[0].offer(temp.substring(temp.lastIndexOf(":::") + 3));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[1])) { //If username is the username of the player playing as green
							gameplayInputs[1].offer(temp.substring(temp.lastIndexOf(":::") + 3));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[2])) { //If username is the username of the player playing as red
							gameplayInputs[2].offer(temp.substring(temp.lastIndexOf(":::") + 3));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[3])) { //If username is the username of the player playing as yellow
							gameplayInputs[3].offer(temp.substring(temp.lastIndexOf(":::") + 3));
						}

					} else if (temp.substring(0, temp.indexOf(":::")).equals("item placed")) {

						if (temp.substring(temp.indexOf(":::") + 3, temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[0])) { //If username is the username of the player playing as blue
							allItemPlacementCoordinates[0][0] = temp.substring(temp.lastIndexOf(":::") + 3, temp.lastIndexOf(","));
							allItemPlacementCoordinates[0][1] = temp.substring(temp.lastIndexOf(","));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[1])) { //If username is the username of the player playing as green
							allItemPlacementCoordinates[1][0] = temp.substring(temp.lastIndexOf(":::") + 3, temp.lastIndexOf(","));
							allItemPlacementCoordinates[1][1] = temp.substring(temp.lastIndexOf(","));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[2])) { //If username is the username of the player playing as red
							allItemPlacementCoordinates[2][0] = temp.substring(temp.lastIndexOf(":::") + 3, temp.lastIndexOf(","));
							allItemPlacementCoordinates[2][1] = temp.substring(temp.lastIndexOf(","));
						} else if (temp.substring(temp.indexOf(":::") + 3,temp.indexOf(":::",temp.indexOf(":::") + 1)).equals(characterSelection[3])) { //If username is the username of the player playing as yellow
							allItemPlacementCoordinates[3][0] = temp.substring(temp.lastIndexOf(":::") + 3, temp.lastIndexOf(","));
							allItemPlacementCoordinates[3][1] = temp.substring(temp.lastIndexOf(","));
						}

					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// can look for other inputs by panels here

		}
	} //End of run
    
    public String getCharacterSelected() {
    	return characterSelected;
    } //End of getCharacterSelected

    public void setCharacterSelected(String characterSelected) {
    	this.characterSelected = characterSelected;
    	output.println("character selection" + ":::" + username + ":::" + characterSelected); // write to server
    } //End of setCharacterSelected

	public String[] getCharacterSelection() {
		return characterSelection;
	} //End of getCharacterSelection

	public void setCharacterSelection(String[] characterSelection) {
		this.characterSelection = characterSelection;
	} //End of setCharacterSelection

	public String getCharacterMovement() {
		return characterMovement;
	} //End of getCharacterMovement

	public void setCharacterMovement(String characterMovement) {
		this.characterMovement = characterMovement;
		output.println("character movement" + ":::" + username + ":::" + characterMovement); //write to server
		output.flush();
	} //End of setCharacterMovement

	public void setUsername(String username) {
    	this.username = username;
    	System.out.println(username + " username set");
    	output.println("user connected" + ":::" + username); // write to server
		output.flush();
	} //End of setUsername

	public String getUsername() {
    	return username;
	} //End of getUsername

	public SimpleLinkedList<String> getUsers() {
    	return users;
	} //End of getUsers

	public String getItemSelected() {
    	return itemSelected;
	} //End of getItemSelected

	public String[] getItemsHeld() {
    	return itemsHeld;
	}

	public SimpleQueue<String>[] getGameplayInputs() {
    	return gameplayInputs;
	}

	/**
	 * setItemSelected
	 * Sets the item selected by the player
	 * @param itemSelected, the name of the item selected by the player
	 */
	public void setItemSelected(String itemSelected) {
    	this.itemSelected = itemSelected;
    	System.out.println(itemSelected + " item selected");
    	output.println("item selected" + ":::" + username + ":::" + itemSelected); //Write to server
    	output.flush();
	} //End of setItemSelected

	public void setItemPlacementCoordinates(int x, int y) {
		itemPlacementCoordinates[0] = Integer.toString(x);
		itemPlacementCoordinates[1] = Integer.toString(y);
		System.out.println("item placed");
		output.println("item placed" + ":::" + username + ":::" + itemPlacementCoordinates[0] + "," + itemPlacementCoordinates[1]);
		output.flush();
	}

	public String[][] getAllItemPlacementCoordinates() {
		return allItemPlacementCoordinates;
	}

	public void close() {
    	output.println("/exit");
    	output.flush();
    	running = false;
	}

	/*
	class buttonListener implements ActionListener{
		  
		  //performs action when button is clicked
		  @Override
		  public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
					username = typeField.getText();
					output.println(username);
					output.flush();
					window.dispose();
			}
	}
	*/
}	
