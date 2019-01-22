/*  [Pellet.java]
    Projectile movement for launchers and damage dealing.
    Author: Brian Zhang, James Liang, Michael Oren
    ICS4UE
    Date: 01/22/19
 */

//Import bases
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Pellet extends Projectile{
	
	private static int radius=10;
	
	//Base constructor
	public Pellet(int x, int y, double[] speed, boolean affectGravity, int tag) {
		super(x, y, radius, speed, affectGravity, tag);
		try {
			this.setImage(ImageIO.read(new File("resources/Cannonball.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Method for returning radius
	public static int getR() {
		return radius;
	}
	
}
