import java.awt.Graphics;

import javax.swing.JPanel;



public class GamePanel extends JPanel {
	
	Resources resource;
	private String buttonPressed = "";

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
		}else if (resource.getPlayers().get(0).getFinished()||resource.getPlayers().get(1).getFinished()||resource.getPlayers().get(2).getFinished()||resource.getPlayers().get(3).getFinished()){
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

	public void setButtonPressed(String buttonPressed) {
		this.buttonPressed = buttonPressed;
	}
	
}
