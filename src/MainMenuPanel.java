import java.awt.*;
import java.swing.*;

public class MainMenuPanel extends JPanel {
	
	private String buttonPressed = "";
	private String selection = "";
	private int currentHover = 0;
	private String[] possibleSelections = {"Start Game", "Instructions", "Credits"};
	
	public void repaint(Graphics g) {
		
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
	
	public void setButtonPressed(String buttonPressed) {
		this.buttonPressed = buttonPressed;
		if (buttonPressed.equals("down") || buttonPressed.equals("tab")) {
			if (currentHover == 2) {
				currentHover = 0;
			} else {
				currentHover += 1;
			}
		} else if (buttonPressed.equals("up")) {
			if (currentHover == 0) {
				currentHover = 2;
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
