import java.awt.*;
import javax.swing.*;

public class ItemBoxPanel extends JPanel{

    private ItemBox box;
    private String itemname;

    public void paintComponent(Graphics g) {

        JLabel select = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("ItemBox.gif"))));

        // Call the super class
        super.paintComponent(g);
        setDoubleBuffered(true);

        //Box
        this.add(select);
        select.setLocation(50,0);
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
                Image base = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/"+itemname+".png"));
                ImageIcon test = new ImageIcon(base);

                ImageIcon itemN = new ImageIcon(base.getScaledInstance(test.getIconWidth()*4,test.getIconHeight()*4,Image.SCALE_SMOOTH));
                JLabel itemize = new JLabel(itemN);

                double itemizeW = itemize.getPreferredSize().getWidth();
                double itemizeH = itemize.getPreferredSize().getHeight();

                Dimension itemizeD = new Dimension((int)(itemizeW*2), (int)(itemizeH*2));
                itemize.setPreferredSize(itemizeD);
                this.add(itemize);


        }
    }


}
