import java.awt.Graphics;
import java.swing.*

import javax.swing.JPanel;

public class CharacterSelectPanel extends JPanel {

	private int currentCharacter = 0;
	private String buttonPressed = "";
	private String selection = "";
	Resources resource = null;
	

	public void paintComponent(Graphics g) {
	  
		// Call the super class
	    super.paintComponent(g);
	    setDoubleBuffered(true);
	    
	    // Draw start button (rectangles right now)
	    if (currentHover == 0) {
	    	g.setColor(Color.RED);
		    g.drawRect(399,199,202,102);
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(400,200,200,100);
	    g.setColor(Color.white);
	    g.fillRect(420,220,160,60);
		  g.setColor(Color.BLACK);
	    g.drawString("START GAME",450,240);
	    
	    // Draw the instructions button
	    if (currentHover == 1) {
	    	g.setColor(Color.RED);
		    g.drawRect(399,199,202,102);
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(400,240,200,100);
	    g.setColor(Color.white);
	    g.fillRect(420,260,160,60);
		  g.setColor(Color.BLACK);
	    g.drawString("INSTRUCTIONS",450,280);
	    
	    // Draw the credits button
	    if (currentHover == 2) {
	    	g.setColor(Color.RED);
		    g.drawRect(399,199,202,102);
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(400,280,200,100);
	    g.setColor(Color.white);
	    g.fillRect(420,300,160,60);
		  g.setColor(Color.BLACK);
	    g.drawString("CREDITS",450,320);
	    
	    // Repaint
	    repaint();
		
	}
  
	public void setResources(Resources resource){
		this.resource = resource;
	}
	
	public void setButtonPressed(String buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
	public String getSelection() {
		return selection;
	}

}
