import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class GamePanel extends JPanel {
	
	Resources resource;
	private String buttonPressed = "";
	
	private int lowX,lowY,highX,highY,ratio;
	
	
	GamePanel() {
		
	}

	public void paintComponent(Graphics g) {
	  
	}
  	
	void setResources(Resources resource){
		this.resource = resource;
	}
  
	void givePoints(Player p, int amount){
		p.setScore(p.getScore()+amount);
		
	}
	
	void death(){
		
	}

	void end(){
		if(resource.getPlayers().get(0).getFinished()&&resource.getPlayers().get(1).getFinished()&&resource.getPlayers().get(2).getFinished()&&resource.getPlayers().get(3).getFinished()){
			//too easy no point  (writing code here gottem)
		}else if (resource.getPlayers().get(0).getFinished()||resource.getPlayers().get(1).getFinished()||resource.getPlayers().get(2).getFinished()||resource.getPlayers().get(3).getFinished()){ //if anyone finished
			for (int i=0;i<4;i++){
				if(!resource.getPlayers().get(i).getFinished()){
					if (resource.getPlayers().get(i).getKilledBy().getPlacer()!= null && (resource.getPlayers().get(i).getKilledBy().getPlacer()!= resource.getPlayers().get(i))){
						givePoints(resource.getPlayers().get(i).getKilledBy().getPlacer(),1);//trap
					}
				}else{
					givePoints(resource.getPlayers().get(i),3);//finish
				}
			}
		}
			
		
	}
	
	public void CameraAdjust(SimpleLinkedList<Player> players){
		int minX = players.get(0).getCharacter().getPosition()[0];
		int maxX = minX;
		int minY = players.get(0).getCharacter().getPosition()[1];
		int maxY = minY;
		
		for(int i =1;i<4;i++){
			if (players.get(i).getCharacter().getPosition()[0] <minX){ ///AAAAAAAAAAAAAAAHHHHHHHHHHHHHHH CHARACTER DIMENSIONS
				minX = players.get(i).getCharacter().getPosition()[0];
			}
			if (players.get(i).getCharacter().getPosition()[0] > maxX){
				maxX = players.get(i).getCharacter().getPosition()[0];
			}
			if (players.get(i).getCharacter().getPosition()[1] <minY){
				minY = players.get(i).getCharacter().getPosition()[1];
			}
			if (players.get(i).getCharacter().getPosition()[0] >maxY){
				maxY = players.get(i).getCharacter().getPosition()[1];
			}
			
			minX-=20;
			maxX+=20;
			minY-=20;
			maxY+=20;
			
			//don't think we need this bc its different now because we can show offscreen
//			minX = Math.max(minX, 0);
//			minY = Math.max(minY, 0);
//			maxX = Math.min(maxX, 860);
//			maxY = Math.min(maxY, 380);
		}
		
		
		double tempRatio = (maxX-minX)/(maxY-minY);
		int tempAmount;
		
		if (tempRatio > 36.0/29.0){ //too much x
			tempAmount = (int)Math.round((maxX-minX)*29.0/36.0);
			//fix y
			tempAmount /=2;
			if(minY> tempAmount){
				minY-=tempAmount;
				if(maxY<380-tempAmount){ //maxY can't be above 380
					maxY+= tempAmount;
				}else{
					int leftOver = 380-maxY;
					maxY =380;
					minY -= leftOver;
				}
			}
			
		}else if(tempRatio < 36.0/29.0){ //too much y
			tempAmount = (int)Math.round((maxY-minY)*36.0/29.0);
			//fix x
			tempAmount /=2;
			minX-=tempAmount;
			maxX+=tempAmount;
		}
		
		//gonna need a ratio ops
		//360 by 290
		
		//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHHHHHHHHHHHHHHHHHH USE OTHER SPRITE IF ZOOM OUT OF CAMERA
		this.highX= maxX;
		this.highY= maxY;
		this.lowX= minX;
		this.lowY= minY;
		
		//ratio time
		//ngl super unsure about this boi
		this.ratio = 360/(maxX-minX);
				
		
	}

	public void setButtonPressed(String buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
	public int[] getScreen(){
		int[] dimensions = new int[4];
		dimensions[0]= lowX;
		dimensions[1]= highX;
		dimensions[2]= lowY;
		dimensions[3]= highY;
		return dimensions;
	}
	
	public double getRatio(){
		return ratio;
	}
}
