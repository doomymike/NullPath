//Java imports
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

public class GameServer {

    private ServerSocket serverSock;//Server socket for connection
    private static Boolean running = true;  //Controls if the server is accepting clients
    private ArrayList<GameClientHandler> clients; //Holds handlers for all clients connected
    private Queue<String> commands; //Holds the inputs done by player

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
    private void go() {
        System.out.println("Waiting for a client connection..");
        Socket client = null; //hold the client connection
        clients = new ArrayList<GameClientHandler>(); //Initialize client handler list

        try {
            serverSock = new ServerSocket(5000); //Assigns a port to the server
            while(running) { //Loop to accept multiple clients
                client = serverSock.accept();  //Wait for connection
                System.out.println("Client connected"); //Show that a client has connected in console
                clients.add(new GameClientHandler(client)); //Add new ConnectionHandler for new client into list of handlers
                Thread t = new Thread(clients.get(clients.size()-1)); //Create a thread for the new client and pass in handler
                t.start(); //Start the new thread
                
                // new thread for message output to all clients (while queue is not null)
                
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
        private String clientName; //Name of client (username)
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
                while (clientName == null) {
                    if (input.ready()) {
                        clientName = input.readLine(); //Get username from client
                        System.out.println("Client " + clientName + " has connected"); //Print username to console
                    }
                }
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
						commands.offer(command);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
         * This message runs the write method for each client connected
         */
      private void writeToAll() {
        for (int i = 0; i < clients.size(); i++) {
           clients.get(i).write(commands.peek()); //Send command to all clients connected to server
        }
      } //End of writeToAll()

        /**
         * getClientName
         * This method returns the name of the client managed by this handler
         * @return clientName, the name of the client
         */
        public String getClientName() {
            return clientName;
        } //End of getClientName()

    } //End of inner class

}
