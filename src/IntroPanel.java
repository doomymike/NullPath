
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;


public class IntroPanel extends JPanel implements KeyListener {

    private String selection = "";
    private boolean next = false;

    //Override KeyListener Methods (proceed to next frame upon keypressed)
    public void keyTyped(KeyEvent e) {
        System.out.println("test1");
        next=true;
    }
    public void keyReleased(KeyEvent e){
    }
    public void keyPressed(KeyEvent e) {
    }


    public void paintComponent(Graphics g) {


        // Call the super class
        super.paintComponent(g);

        setDoubleBuffered(true);
        setLayout(null);

        //Initialize Logo
        JLabel logo = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("NullPathLogo.png"))));
        JLabel anykey = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Continue.png"))));
        JLabel flow = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("WaterFlow.gif"))));

        this.add(logo);
        logo.setLocation(65,150);
        logo.setSize(500,240);

        this.add(anykey);
        anykey.setLocation(200,600);
        anykey.setSize(630,42);


        this.add(flow);
        flow.setLocation(0,-100);
        flow.setSize(2000,1020);

        addKeyListener(this);
        repaint();

    }

    public boolean getNext(){
        System.out.println("");
        return this.next;
    }
    public String getSelection() {
        return selection;
    }

}
