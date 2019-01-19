import java.awt.*;
import javax.swing.*;

public class InstructionsPanel extends JPanel {

    private String buttonPressed = "";
    private String selection = "";

    public void paintComponent(Graphics g) {

        JLabel flow = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/WaterFlow.gif"))));
        JLabel instructions = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Instructions.png"))));
        JLabel copyright = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Copyright.png"))));

        // Call the super class
        super.paintComponent(g);
        setDoubleBuffered(true);

        //Instructions L
        this.add(instructions);
        instructions.setLocation(100,25);
        instructions.setSize(800,514);

        this.add(copyright);
        copyright.setLocation(100,600);
        copyright.setSize(800,57);

        this.add(flow);
        flow.setLocation(0,-100);
        flow.setSize(2000,1020);

        // Repaint
        repaint();

    }

    public String getSelection() {
        return selection;
    }

}
