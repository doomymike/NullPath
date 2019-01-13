
import java.awt.*;
import javax.swing.*;


public class IntroPanel extends JPanel {

  private String buttonPressed = "";
	private String selection = "";

  public void paintComponent(Graphics g) {
	  
	// Call the super class
    super.paintComponent(g);
    setDoubleBuffered(true);
    
    // Draw start button (rectangles right now)
    g.setColor(Color.BLUE);
    g.fillRect(400,200,200,100);
    g.setColor(Color.white);
    g.fillRect(420,220,160,60);
	  g.setColor(Color.BLACK);
    g.drawString("START",450,240);

    // Repaint
    repaint();
	  
  }
  
  public void setButtonPressed(String buttonPressed) {
	  this.buttonPressed = buttonPressed;
	  if (buttonPressed.equals("enter")) {
		  selection = "start";
	  }
  }
  
  public String getButtonPressed() {
	  return buttonPressed;
  }
	
	public String getSelection() {
		return selection;
	}

}