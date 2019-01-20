/**
 * [GameClient.java]
 * Client that connects to the server for the NullPath Game
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 21, 2019
 */

// something to close it

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
	private boolean characterHasBeenSelected;
	
	// store all players' selections
	private String[] characterSelection = new String[4]; //Will store the name of the user in the corresponding slot (to their character selection)
	//Array: {Blue, Green, Red, Yellow}
	
	// for running of game
	private String characterMovement = "";
	private SimpleQueue<String> movementInputs = new SimpleQueue<>(); //stores inputs by user

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
						if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1)).equals("Blue")) {
							if (characterSelection[0] == null) { //Ensure no one else selected the character
								characterSelection[0] = temp.substring(temp.indexOf(":::"),temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						} else if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1)).equals("Green")) {
							if (characterSelection[1] == null) { //Ensure no one else selected the character
								characterSelection[1] = temp.substring(temp.indexOf(":::"),temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						} else if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1)).equals("Red")) {
							if (characterSelection[2] == null) { //Ensure no one else selected the character
								characterSelection[2] = temp.substring(temp.indexOf(":::"),temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						} else if (temp.substring(temp.indexOf(":::", temp.indexOf(":::")+1)).equals("Yellow")) {
							if (characterSelection[3] == null) { //Ensure no one else selected the character
								characterSelection[3] = temp.substring(temp.indexOf(":::"),temp.indexOf(":::", temp.indexOf(":::")+1));
							}
						}
					} else if (temp.substring(0, temp.indexOf(":::")).equals("user connected")) {
						users.add(temp.substring(temp.indexOf(":::") + 3));
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
	
	public boolean hasSelected() {
		return characterHasBeenSelected;
	} //End of hasSelected

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
