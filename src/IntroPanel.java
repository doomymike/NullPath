public IntroPanel extends JPanel {

	private String buttonPressed = "";
	
  public void paintComponent(graphics g) {
	  
	// Call the super class
    super.paintComponent(g);
    setDoubleBuffered(true);
    
    // Do all other code stuff here
    
    // Repaint
    repaint();
	  
  }
  
  public void setButtonPressed(String buttonPressed) {
	  this.buttonPressed = buttonPressed;
	  //do the stuff for when they press the button
  }

}
