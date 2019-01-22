/*[FanWind.java]
Method for handling fanwind physics and propelling players upwards
Authors: James Liang
Date: January 22, 2019
*/

//Import base
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FanWind extends VelocityModifier{

	//Base variables
	private static double speed = 2.5;
	private double dx;
	private double dy;
	private static int height = 250;
	private static int width = 50;
	
	public static int getH() {
		return height;
	}
	
	public static int getW() {
		return width;
	}
	
	//Base constructor
	public FanWind(int x, int y, int direction, int direction2) {
		//Direction 2 indicates lateral or not
		//Direction indicates positive or negative
		super(x, y, height, width, direction, direction2);

		if (direction == 0) {
			System.out.println("IMPROPER!");
		}
		
		if (direction2 == 0) {
			if (direction == 2) {
				dx = -speed;
				dy = 0;
			} else {
				dx = speed;
				dy = 0;
			}
		} else {
			if (direction == 2) {
				dy = -1*speed;
				dx = 0;
			} else {
				dy = speed;
				dx = 0;
			}
		}
		
		try {
			
			for (int i = 1;i<7;i++){
				this.addSprite(ImageIO.read(new File("resources/Fan"+i+".png")));
			}
			this.setImage(this.getSprites().get(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Method for retreving speed values
	public double[] getSpeed() {
		return new double[] {dx, dy};
	}

	//public static BufferedImage getSprite()
	
}
