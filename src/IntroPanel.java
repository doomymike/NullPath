import java.awt.*;

public IntroPanel extends JPanel {

	private String buttonPressed = "";
	
  public void paintComponent(graphics g) {
	  
	// Call the super class
    super.paintComponent(g);
    setDoubleBuffered(true);
    
    // Draw start button (rectangles right now)
    g.setColour(Color.BLUE);
    g.fillRect(400,200,200,100);
    g.setColour(Color.BLACK);
    g.drawRect(420,220,160,60);
    g.drawString("START",450,240);
    
    // Repaint
    repaint();
	  
  }
  
  public void setButtonPressed(String buttonPressed) {
	  this.buttonPressed = buttonPressed;
	  if (buttonPressed.equals("enter")) {
		  // Go to main menu frame
	  }
  }
  
  public void getButtonPressed() {
	  return buttonPressed;
  }

}
