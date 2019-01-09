import javax.swing.JPanel;
import java.awt.image.*;
import java.awt.event.*;
import java.awt.Graphics;

public class MapPlacement extends JPanel implements MouseListener{

	public MapPlacement() {
		addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		setDoubleBuffered(true);
		
		repaint();
		
	}

	public void mousePressed(MouseEvent e) {
		System.out.println("Pressed");
	}
	
	public void mouseReleased(MouseEvent e) {
		System.out.println("Release");
	}
	
	public void mouseEntered(MouseEvent e) {
	}
	
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked");
	}
	
	public void mouseExited(MouseEvent e) {
		System.out.println("Exit");
	}
	
}
