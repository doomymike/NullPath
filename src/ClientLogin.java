/*[ClientLogin.java]
    JFrame from retrieving client information and making connection to server
    Authors: Brian Li, Brian Zhang 
    Date: January 22, 2019
*/

//Import bases
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientLogin extends JFrame implements ActionListener{

    //UserName retrieval, handles client login
    private JLabel text;

    private JTextField enterField;
    private JTextField serverField, portField;

    private JLabel text3;

    //Button SetUp
    private JButton login;

    private String[] users; // Names of users connected
    private String thisUser = "";
    private String server = "";
    private String portNumber = "";

    GameClient client;

    Resources resources;

    //Base constructor
    ClientLogin(GameClient client, Resources resources){
        super("nullpath login");

        users = new String[4];
        this.client = client;

        //GUI Layout Management [CardLayout]
        JPanel upperPanel = new JPanel(new GridLayout(8,1,0,50));
        JPanel dataPanel = new JPanel(new GridLayout(1,1,0,0));

        //Spacer
        upperPanel.add(new JLabel(""));

        serverField = new JTextField();
        serverField.setBackground(new Color(254, 234, 255));
        portField = new JTextField();
        portField.setBackground(new Color(254, 234, 255));
        portField.setHorizontalAlignment(SwingConstants.RIGHT);

        dataPanel.add(new JLabel("Server address:  "));
        dataPanel.add(serverField);
        dataPanel.add(new JLabel("Port number:  ",SwingConstants.RIGHT));
        dataPanel.add(portField);
        dataPanel.add(new JLabel(""));


        upperPanel.add(dataPanel);

        // the Label and the TextField
        text = new JLabel("Enter your username below: ", SwingConstants.CENTER);

        upperPanel.add(text);
        enterField = new JTextField("anon");
        enterField.setBackground(new Color(255, 231, 234));
        upperPanel.add(enterField);
        text3 = new JLabel("", SwingConstants.CENTER);
        upperPanel.add(text3);
        add(upperPanel, BorderLayout.NORTH);

        //Buttons
        login = new JButton("Connect!");
        //Action Listener for login button
        login.addActionListener(this);

        JPanel southPanel = new JPanel();
        southPanel.add(login);
        add(southPanel, BorderLayout.SOUTH);

        //Frame Setup
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setVisible(true);
        enterField.requestFocus();

    }


    //Override actionPerformed
    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();

        if(button == login) {
            thisUser = enterField.getText().trim();
            if(thisUser.length() == 0) {
                return;
            }
            server = serverField.getText().trim();
            if(server.length() == 0) {
                return;
            }

            portNumber = portField.getText().trim();
            if(portNumber.length() == 0) {
                return;
            }
            int port = 0;
            try {
                port = Integer.parseInt(portNumber);
            }
            catch(Exception en) {
                return;
            }

            //Server Connected Check

            //Start client (connect to server) (<< ADD PORT NUMBER AND IP STUFF - will be passed into go() method)
            client.go(server, port);
            Thread t = new Thread(client);
            t.start();

            //Forward name of user to Client
            client.setUsername(thisUser);

            while (client.getUsers().size() != 4) {
                // Display some "waiting for players to connect" message
                System.out.println("Waiting for all players to connect...");
            }

            for (int i = 0; i < 4; i++) {
                resources.addPlayer(client.getUsers().get(i)); // Add player objects to resources
            }

            dispose(); // Close client login
        }

    }
}
