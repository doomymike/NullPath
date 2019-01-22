/*  [GameServer.java]
    Basic server for handling connections and ports.
    Author: Brian Zhang, Brian Li
    ICS4UE
    Date: 01/22/19
 */

//Java imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Queue;

public class GameServer {

    private ServerSocket serverSock;//Server socket for connection
    private static Boolean running = true;  //Controls if the server is accepting clients
    private ArrayList<GameClientHandler> clients; //Holds handlers for all clients connected
    private SimpleQueue<String> commands; //Holds the inputs done by player

    /** Main
     * Main method that starts the server (runs the method that starts it)
     * @param args parameters from command line
     */
    public static void main(String[] args) {
        new GameServer().go(); //start the server
    }

    /** Go
     * This method starts the server and loops to accept multiple client connections
     */
    public void go() {
        System.out.println("Waiting for a client connection..");
        Socket client = null; //hold the client connection
        clients = new ArrayList<GameClientHandler>(); //Initialize client handler list

        try {

            serverSock = new ServerSocket(5132); //Assigns a port to the server

            commands = new SimpleQueue<>();

            while(running) { //Loop to accept multiple clients

                client = serverSock.accept();  //Wait for connection
                System.out.println("Client connected"); //Show that a client has connected in console
                clients.add(new GameClientHandler(client)); //Add new ConnectionHandler for new client into list of handlers
                Thread t = new Thread(clients.get(clients.size()-1)); //Create a thread for the new client and pass in handler
                t.start(); //Start the new thread
                
            }
            
        } catch(Exception e) {
            try {
                client.close();
            }catch (Exception e1) {
                System.out.println("Failed to close socket");
            }
            System.exit(-1); //Close program
        }
        
    }

    /**
     * GameClientHandler
     * Inner class that is run in a thread and manages a client connected to the server
     */
    class GameClientHandler implements Runnable {
        private PrintWriter output; //assign printwriter to network stream
        private BufferedReader input; //Stream for network input
        private Socket client;  //keeps track of the client socket
        private boolean running; //flags whether the client is connected or not (whether handler should run)
        
        private String command;

        /**
         * ConnectionHandler
         * Constructor that makes a handler for a client connection
         * @param s, the socket belonging to this client connection
         */
        GameClientHandler(Socket s) {
            this.client = s;  //constructor assigns client to this
            try {  //assign all connections to client
                this.output = new PrintWriter(client.getOutputStream());
                InputStreamReader stream = new InputStreamReader(client.getInputStream());
                this.input = new BufferedReader(stream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            running = true; //Flag as user being connected
        } //end of constructor

        /**
         * run
         * Executed on start of thread, loops to communicate with client
         */
        public void run() {

            while (running) {
                try {
					if (input.ready()) {
						command = input.readLine();
						if (command != null) {
                            commands.offer(command);
                            System.out.println(command);
                            if (clients.size() == 4) { // Do not send anything until all users are connected
                                while (commands.peek() != null) {
                                    writeToAll();
                                }
                            }
                        }
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
            }

            //Close the socket
            try {
                input.close();
                output.close();
                client.close();
            } catch (Exception e) {
                System.out.println("Failed to close socket");
            }
        } //End of run()
        
        /**
         * write
         * This method takes in a string message and outputs it to the client
         * @param str, the message to be sent
         */
        private void write(String str) {
            output.println(str);
            output.flush();
        } //End of write()

	/**
	 * writeToAll
	 * Outputs message in commands queue to all connected clients
	 */
        private void writeToAll() {
            for (int i = 0; i < clients.size(); i++) {
                clients.get(i).write(commands.peek()); // write command to each connected client
            }
            commands.poll();
            System.out.println("sent to client");
        } //End of writeToAll

    } //End of inner class

} //End of class
