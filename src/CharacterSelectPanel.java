// ADD BACK BUTTON

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JPanel;

public class CharacterSelectPanel extends JPanel {

	private int currentHover = 0;
	private String buttonPressed = "";
	private String selection = "";
	private String[] possibleSelections = {"Blue", "Green", "Red", "Yellow"};
	Resources resource = null;
	
	public void paintComponent(Graphics g) {
	  
		// Call the super class
	    super.paintComponent(g);
	    setDoubleBuffered(true);
	    
	    // Draw start button (rectangles right now)
	    if (currentHover == 0) {
	    	g.setColor(Color.RED);
		    g.drawRect(399,199,102,202);
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(400,200,100,200);
	    g.setColor(Color.white);
	    g.fillRect(420,220,60,160);
		  g.setColor(Color.BLACK);
	    g.drawString("Character 1",450,240);
	    
	    // Draw the instructions button
	    if (currentHover == 1) {
	    	g.setColor(Color.RED);
		    g.drawRect(439,199,102,202);
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(440,200,100,200);
	    g.setColor(Color.white);
	    g.fillRect(460,220,60,160);
		  g.setColor(Color.BLACK);
	    g.drawString("Character 2",490,240);
	    
	    // Draw the credits button
	    if (currentHover == 2) {
	    	g.setColor(Color.RED);
		    g.drawRect(479,199,102,202);
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(480,200,100,200);
	    g.setColor(Color.white);
	    g.fillRect(500,200,60,160);
		  g.setColor(Color.BLACK);
	    g.drawString("Character 3",530,240);
	    
	    // Repaint
	    repaint();
		
	}
  
	public void setResources(Resources resource){
		this.resource = resource;
	}
	
	public void setButtonPressed(String buttonPressed) {
		this.buttonPressed = buttonPressed;
		this.buttonPressed = buttonPressed;
		if (buttonPressed.equals("right")) {
			if (currentHover == 3) {
				currentHover = 0;
			} else {
				currentHover += 1;
			}
		} else if (buttonPressed.equals("left")) {
			if (currentHover == 0) {
				currentHover = 3;
			} else {
				currentHover -= 1;
			}
		} else if (buttonPressed.equals("enter")) {
			selection = possibleSelections[currentHover]; // User uses enter key to select option from menu
		}
	}
	
	public String getSelection() {
		return selection;
	}

}
