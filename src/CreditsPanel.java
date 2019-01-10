import java.awt.*;
import javax.swing.*;

public class CreditsPanel extends JPanel {

  private String buttonPressed = "";
	private String selection = "";
	
  public void paintComponent(Graphics g) {
	  
	// Call the super class
    super.paintComponent(g);
    setDoubleBuffered(true);
    
    // Draw credits text (filler right now)
	  g.setColor(Color.BLACK);
    g.drawString("Credits",450,240);
    
    // Draw back button (top left)
    g.setColor(Color.BLUE);
    g.fillRect(20,20,100,50);
    g.setColor(Color.white);
    g.fillRect(30,30,80,30);
	  g.setColor(Color.BLACK);
    g.drawString("BACK",50,50);
    
    // Repaint
    repaint();
	  
  }
  
  public void setButtonPressed(String buttonPressed) {
	  this.buttonPressed = buttonPressed;
	  if (buttonPressed.equals("enter")) {
		  selection = "back";
	  }
  }
  
  public String getButtonPressed() {
	  return buttonPressed;
  }
	
	public String getSelection() {
		return selection;
	}

}
