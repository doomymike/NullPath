import java.awt.*;
import javax.swing.*;

public class ItemBoxPanel extends JPanel{

    private ItemBox box;

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
            //draw the boi on the screen
        }
    }


}
