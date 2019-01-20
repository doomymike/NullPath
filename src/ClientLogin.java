
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

    ClientLogin(){
        super("nullpath login");

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


    public void actionPerformed(ActionEvent e) {
        Object button = e.getSource();

        if(button == login) {
            String username = enterField.getText().trim();
            if(username.length() == 0) {
                return;
            }
            String server = serverField.getText().trim();
            if(server.length() == 0) {
                return;
            }

            String portNumber = portField.getText().trim();
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

            //Forward collected information to Client (<<<<TO_DO)

            dispose();
        }

    }
}
