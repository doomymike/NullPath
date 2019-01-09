//

import java.awt.image.BufferedImage;

public class MovingPlatform extends Platform{
	
	int x2,y2,x3,y3	,direction,speed; //x2y2 are bottom left x3y3 are top right
	
	MovingPlatform(int x, int y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}
	
	MovingPlatform(int x, int y, int height, int width) {
		super(x, y,height,width);
	}

	MovingPlatform(int x, int y, int height, int width,int x2,int y2, int direction, int speed) {
		super(x, y,height,width);
		this.direction=direction;
		this.speed = speed;
		// TODO Auto-generated constructor stub
	}
	
	public static BufferedImage getSprite(){
		return getSprite();
	}
	
	public void move(){
		if(direction == 0){ //left
			if(this.getX() <=x2){
				direction = 2; //right
				this.move();
			}else{
				this.setX(this.getX()-speed);
			}
		}else if(direction == 1){ //up
			if(this.getY()>=y3){
				direction = 3; //down
				this.move();
			}else{
				this.setY(this.getY()+speed);
			}
		}else if(direction == 2){ //right
			if(this.getY()+getHeight() >=x3){
				direction = 0; //left
				this.move();
			}else{
				this.setX(this.getX()+speed);
			}
		}else{ //down
			if(this.getY()+getWidth() <=x2){
				direction = 1; //down
				this.move();
			}else{
				this.setY(this.getY()-speed);
			}
		}
	}
}
