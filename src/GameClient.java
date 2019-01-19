// something to close it

import java.awt.*;
import javax.swing.*;


import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.util.Queue;

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
	private boolean newUserSet;
	private SimpleLinkedList<String> users = new SimpleLinkedList<>();

	// for character selection
	private String characterSelected = "";
	private boolean characterHasBeenSelected;
	
	// store all players' selections
	private String[] characterSelection = new String[4];
	
	// for running of game
	private String characterMovement = "";
	//private Queue<String> movementInputs = new Queue<>(); //replace with queue class

    /**
     * main method, runs the client
     * @param args parameters from command line
     */
    public static void main(String[] args) {
        new GameClient().go();
    } //End of main

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

			running = true;

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
				if (input.ready()) { //check for an incoming messge
					String temp = input.readLine();
					if (temp.substring(0, temp.indexOf(":::")).equals("character selection")) {
						for (int i = 0; i < 4; i++) {
							if (characterSelection[i].equals(null)) {
								characterSelection[i] = temp.substring(temp.indexOf(":::") + 3); // set command for character select panel to read
								characterHasBeenSelected = true;
								break;
							}
						}
					} else if (temp.substring(0, temp.indexOf(":::")).equals("user connected")) {
						addUser(temp.substring(temp.indexOf(":::") + 3));
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// can look for other inputs by panels here

		}
	}
    
    public String getCharacterSelected() {
    	return characterSelected;
    }

    public void setCharacterSelected(String characterSelected) {
    	this.characterSelected = characterSelected;
    	output.println("character selection" + ":::" + username + ":::" + characterSelected); // write to server
    }

	public String[] getCharacterSelection() {
		return characterSelection;
	}

	public void setCharacterSelection(String[] characterSelection) {
		this.characterSelection = characterSelection;
	}
	
	public boolean hasSelected() {
		return characterHasBeenSelected;
	}

	public String getCharacterMovement() {
		return characterMovement;
	}

	public void setCharacterMovement(String characterMovement) {
		this.characterMovement = characterMovement;
		output.println("character movement" + ":::" + username + ":::" + characterMovement); //write to server
	}

	public void setUsername(String username) {
    	this.username = username;
    	System.out.println(username + " username set");
    	output.println("user connected" + ":::" + username); // write to server
	}

	public String getUsername() {
    	return username;
	}

	public boolean isNewUserSet() {
		return newUserSet;
	}

	public void setNewUserSet(boolean newUserSet) {
    	this.newUserSet = newUserSet;
	}

	public void addUser(String username) {
    	users.add(username);
    	newUserSet = true;
	}

	public SimpleLinkedList<String> getUsers() {
    	return users;
	}

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
}	
