import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel {

    private int selection;

    //Mouse Position
    private int xPos = 0;
    private int yPos = 0;

    public void paintComponent(Graphics g) {

        // Call the super class
        super.paintComponent(g);
        setDoubleBuffered(true);

        //Override mouseListener methods
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                xPos = e.getX();
                yPos = e.getY();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent evt){
                setLocation(evt.getXOnScreen()-xPos, evt.getYOnScreen()-yPos);
            }
        });

        this.setLayout(null);

        JLabel cyber = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Cyberpunk.gif"))));
        //this.add(cyber);
        //cyber.setLocation(0,0);
        //cyber.setSize(1080,900);

        //Setup all buttons for pedigree options (AutoDom, AutoRec, XLinkDom, XLinkRec, YLink, Samples)
        JButton buttonOne = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Start.png"))));
        JButton buttonTwo = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Options.png"))));
        JButton buttonThree = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Quit.png"))));


        // Draw start button (rectangles right now)
        this.add(buttonOne);
        buttonOne.setLocation(0,150);
        buttonOne.setSize(1000,64);
        buttonOne.setContentAreaFilled(false);
        buttonOne.setBorder(null);

        buttonOne.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("StartHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Start.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("StartPress.png"))));
                selection = 0;
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Draw options button (rectangles right now)
        this.add(buttonTwo);
        buttonTwo.setLocation(0,300);
        buttonTwo.setSize(1000,64);
        buttonTwo.setContentAreaFilled(false);
        buttonTwo.setBorder(null);

        buttonTwo.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("OptionsHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Options.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("OptionsPress.png"))));
                selection = 1;
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Draw quit button (rectangles right now)
        this.add(buttonThree);
        buttonThree.setLocation(0,450);
        buttonThree.setSize(1000,64);
        buttonThree.setContentAreaFilled(false);
        buttonThree.setBorder(null);

        buttonThree.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("QuitHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Quit.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("QuitPress.png"))));
                selection = 2;
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        // Repaint
        repaint();
        setVisible(true);
    }

    public int getSelection() {
        return selection;
    }

}
