public MainMenuPanel extends JPanel {
	
	private String buttonPressed = "";
	private String selection = "";
	private int currentHover = "Start Game";
	
	public void repaint(Graphics g) {
		
		// Call the super class
	    super.paintComponent(g);
	    setDoubleBuffered(true);
	    
	    // Draw start button (rectangles right now)
	    if (currentHover.equals("Start Game")) {
	    	
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(400,200,200,100);
	    g.setColor(Color.white);
	    g.fillRect(420,220,160,60);
		  g.setColor(Color.BLACK);
	    g.drawString("START GAME",450,240);
	    
	    // Draw the instructions button
	    if (currentHover.equals("Instructions")) {
	    	
	    }
	    g.setColor(Color.BLUE);
	    g.fillRect(400,240,200,100);
	    g.setColor(Color.white);
	    g.fillRect(420,260,160,60);
		  g.setColor(Color.BLACK);
	    g.drawString("INSTRUCTIONS",450,280);
	    
	    // Draw the credits button
	    if (currentHover.equals("Credits")) {
	    	
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
		// set selection (if statements)
	}
	
	public String getSelection() {
		return selection;
	}

}
