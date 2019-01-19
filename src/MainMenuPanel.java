import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends JPanel {

    private int selection = -1;
    private boolean next = false;

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

        //Setup all buttons for options
        JButton buttonOne = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Start.png"))));
        JButton buttonTwo = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Options.png"))));
        JButton buttonThree = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Quit.png"))));

        JLabel flow = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/WaterFlow.gif"))));

        // Draw start button (rectangles right now)
        this.add(buttonOne);
        buttonOne.setLocation(0,150);
        buttonOne.setSize(1000,64);
        buttonOne.setContentAreaFilled(false);
        buttonOne.setBorder(null);

        buttonOne.addMouseListener(new MouseAdapter(){
            public void mouseEntered(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/StartHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Start.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonOne.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/StartPress.png"))));
                selection = 0;
                next = true;
                System.out.println("Startpressed");
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
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/OptionsHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Options.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonTwo.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/OptionsPress.png"))));
                selection = 1;
                next = true;
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
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/QuitHover.png"))));
            }
            public void mouseExited(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/Quit.png"))));
            }
            public void mousePressed(java.awt.event.MouseEvent evt){
                buttonThree.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/QuitPress.png"))));
                selection = 2;
                next = true;
            }
            public void mouseClicked(java.awt.event.MouseEvent evt){

            }
        });

        this.add(flow);
        flow.setLocation(0,-100);
        flow.setSize(2000,1020);

        // Repaint
        repaint();
        setVisible(true);
    }

    public boolean getNext(){return next;}
    public int getSelection() {
        System.out.println("");
        return selection;
    }

}
