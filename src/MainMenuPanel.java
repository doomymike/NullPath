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

        //Setup all buttons for pedigree options (AutoDom, AutoRec, XLinkDom, XLinkRec, YLink, Samples)
        JButton buttonOne = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Start.png"))));
        JButton buttonTwo = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Options.png"))));
        JButton buttonThree = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("Quit.png"))));


        // Draw start button (rectangles right now)
        this.add(buttonOne);
        buttonOne.setLocation(50,50);
        buttonOne.setSize(128,32);
        buttonOne.setContentAreaFilled(false);

        buttonOne.addMouseListener(new MouseAdapter(){
            public void mouseEntered(){

            }
            public void mouseExited(){

            }
            public void mousePressed(){
                selection = 0;
            }
            public void mouseClicked(){

            }
        });

        // Draw options button (rectangles right now)
        this.add(buttonTwo);
        buttonTwo.setLocation(50,150);
        buttonTwo.setSize(128,32);
        buttonTwo.setContentAreaFilled(false);

        buttonTwo.addMouseListener(new MouseAdapter(){
            public void mouseEntered(){

            }
            public void mouseExited(){

            }
            public void mousePressed(){
                selection = 1;
            }
            public void mouseClicked(){

            }
        });

        // Draw quit button (rectangles right now)
        this.add(buttonThree);
        buttonThree.setLocation(50,250);
        buttonThree.setSize(128,32);
        buttonThree.setContentAreaFilled(false);

        buttonThree.addMouseListener(new MouseAdapter(){
            public void mouseEntered(){

            }
            public void mouseExited(){

            }
            public void mousePressed(){
                selection = 2;
            }
            public void mouseClicked(){

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
