/*[ItemBoxPanel.java]
   Panel for displaying randomized items for the user to select from
   Authors: Brian Li, Brian Zhang
   Date: January 22, 2019 
*/

//Base imports
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ItemBoxPanel extends JPanel{

    //Base variables
    private ItemBox box;
    private String itemname;

    //Mouse Position
    private int xPos = 0;
    private int yPos = 0;

    private boolean itemSelected;
    private GameClient client;
    private boolean[] itemsSelected = new boolean[6];
    private String[] itemNames = new String[6];

    private JButton[] items = new JButton[6];

    //Override paint component
    public void paintComponent(Graphics g) {

        //Check for client selection
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                if ((client.getItemsHeld()[i].equals(itemNames[j])) && (!itemsSelected[j])) { //Read from client (see if any player has selected an item)
                    itemsSelected[j] = true; //Mark as item selected
                    items[j].setVisible(false); //Grey out selected item
                }
            }
        }

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

    public void setClient(GameClient client) {
        this.client = client;
    }

    private int getIndexOfItem(String name) {
        for (int i = 0; i < 6; i++) {
            if (itemNames[i].equals(name)) {
                return i;
            }
        }
        return 0; //Should never reach for what we are using it for
    }

    public ItemBoxPanel(){
        box = new ItemBox(4); //should be num players

        for(int i =0;i<box.getItems().size();i++){
            //draw the boi on the screen, draw each individual item
                System.out.println("Item generated:"+box.getItems().get(i));
                itemname = box.getItems().get(i);
                itemNames[i] = itemname;

                //Image transformation
                Image base = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("/resources/"+itemname+".png"));
                ImageIcon test = new ImageIcon(base);

                ImageIcon itemN = new ImageIcon(base.getScaledInstance(test.getIconWidth()*4,test.getIconHeight()*4,Image.SCALE_SMOOTH));
                JButton itemize = new JButton(itemN);

                items[i] = itemize;

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

                    if ((!itemSelected) && (!itemsSelected[getIndexOfItem(itemname)])) { //If player has not yet selected an item and the item they want to select has not been selected

                        //Send info to server
                        client.setItemSelected(itemname); //Get name of corresponding item

                    }

                }
                public void mouseClicked(java.awt.event.MouseEvent evt){
                }
            });

        }
    }


}
