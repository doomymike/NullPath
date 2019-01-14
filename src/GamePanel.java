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
	private String[][] skyFortressMapCollision = new String[36][86];
	private BufferedImage skyFortressImage;
	private int lowX,lowY,highX,HighY,ratio;
	
	
	GamePanel() {
		try {
			skyFortressImage = ImageIO.read(new File("/resources/SkyFortress.png")); //idk if this is right pathname
		} catch (IOException e) {

		}
	}

	public void paintComponent(Graphics g) {
	  
	}

	public void mapInit(String mapName) {
		try {
			Scanner input = new Scanner(new File("/resources/SkyFortressCollision")); //idk if pathname is correct
			String line;
			int index = 0;
			if (mapName.equals("SkyFortress")) {
				while (input.hasNext()) {
					line = input.nextLine();
					for (int i = 0; i < 86; i++) {
						skyFortressMapCollision[index][i] = line.substring(i,i+1);
					}
					index++;
				}
			}
		} catch(IOException e) {

		}
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
			if (players.get(i).getCharacter().getPosition()[0] <minX){
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
			
			minX = Math.max(minX, 0);
			minY = Math.max(minY, 0);
			maxX = Math.min(maxX, 860);
			maxX = Math.min(minX, 380);
		}
		
		//gonna need a ratio ops
		//360 by 290
	}

	public void setButtonPressed(String buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
}
