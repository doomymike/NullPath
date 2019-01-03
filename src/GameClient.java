//No gui needed? (since gui should all be done in game panel/frames)

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClient {

    Socket mySocket; //socket for connection
    BufferedReader input; //reader for network stream
    PrintWriter output;  //printwriter for network output
    boolean running = true; //thread status via boolean
    String username;

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

        } catch (IOException e) {  //connection error occured
            System.out.println("Connection to Server Failed");
            e.printStackTrace();
        }

        System.out.println("Connection made.");

        // ------------------------------------------------------------------------

        //Window listener needed in frame - make sure to tell client that player is exiting (so things can be closed)

        /*
        //tells server if exiting program
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                output.println("/exit");
                output.flush();
            }
        });
        */

        // Checks for incoming commands
        while (true){
            try {
                if (input.ready()) { //check for an incoming messge
                    String temp = input.readLine();
                    //SEND STRING (or some combo of sorts) TO PANEL/FRAME
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    } //End of go()

    //****** Inner Classes for KeyListener ****

    private class GameKeyListener implements KeyListener{

        @Override
        public void keyPressed(KeyEvent e) {

            if (e.getKeyCode() == 27) { //esc pressed

            } else if (e.getKeyCode() == 13) { //enter pressed

            } else if (e.getKeyCode() == 9) { //tab pressed

            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }

        @Override
        public void keyTyped(KeyEvent e) {

            if (e.getKeyCode() == 37) { //Left pressed

            } else if (e.getKeyCode() == 38) {//Up pressed

            } else if (e.getKeyCode() == 39) { //Right pressed

            } else if (e.getKeyCode() == 40) { //Down pressed

            }
            
        }

    } //End of inner class

}
