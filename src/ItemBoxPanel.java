import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemBoxPanel extends JPanel{

    private ItemBox box;
    private String itemname;

    //Mouse Position
    private int xPos = 0;
    private int yPos = 0;

    public void paintComponent(Graphics g) {

        //Override mouseListener methods
        addMouseListener(new MouseAdapter(){
            public void mousePressed(MouseEvent e){
                xPos = e.getX();
                yPos = e.getY();
            }
        });

        JLabel select = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/ItemBox.gif"))));

        // Call the super class
        super.paintComponent(g);
        setDoubleBuffered(true);

        //Box
        this.add(select);
        select.setLocation(150,0);
        select.setSize(700,700);

        // Repaint
        repaint();

    }

    public ItemBoxPanel(){
        box = new ItemBox(4); //should be num players

        for(int i =0;i<box.getItems().size();i++){
            //draw the boi on the screen, draw each individual item
                System.out.println("Item generated:"+box.getItems().get(i));
                itemname = box.getItems().get(i);

                //Image transformation
                Image base = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/"+Ritemname+".png"));
                ImageIcon test = new ImageIcon(base);

                ImageIcon itemN = new ImageIcon(base.getScaledInstance(test.getIconWidth()*4,test.getIconHeight()*4,Image.SCALE_SMOOTH));
                JButton itemize = new JButton(itemN);

                double itemizeW = itemize.getPreferredSize().getWidth();
                double itemizeH = itemize.getPreferredSize().getHeight();

                Dimension itemizeD = new Dimension((int)(itemizeW*2), (int)(itemizeH*2));
                itemize.setPreferredSize(itemizeD);
                this.add(itemize);
                itemize.setContentAreaFilled(false);
                itemize.setBorder(null);

                itemize.addMouseListener(new MouseAdapter(){
                public void mouseEntered(java.awt.event.MouseEvent evt){

                }
                public void mouseExited(java.awt.event.MouseEvent evt){

                }
                public void mousePressed(java.awt.event.MouseEvent evt){
                    //Select the item for the player
                    itemize.setVisible(false);
                }
                public void mouseClicked(java.awt.event.MouseEvent evt){
                }
            });

        }
    }


}
