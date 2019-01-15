

import java.awt.*;
import javax.swing.*;


public class IntroPanel extends JPanel {

    private String selection = "";

    public void paintComponent(Graphics g) {

        // Call the super class
        super.paintComponent(g);
        setDoubleBuffered(true);
        setLayout(null);

        //Initialize Logo
        JLabel logo = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("NullPathLogo.png"))));
        JLabel anykey = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Continue.png"))));
        JLabel flow = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Flow.gif"))));

        this.add(logo);
        logo.setLocation(65,150);
        logo.setSize(500,240);

        this.add(anykey);
        anykey.setLocation(200,600);
        anykey.setSize(630,42);

        this.add(flow);
        flow.setLocation(0,0);
        flow.setSize(1000,750);

        // Repaint
        repaint();

    }

    public String getSelection() {
        return selection;
    }

}
