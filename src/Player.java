/* [Player.java]
   Class for discerning base player variables and data
   Authors: Brian Li, Brian Zhang, James Liang, Michael Oren
   Date: January 21, 2019
*/
public class Player {

	//Base variables
	private String name;
	private Character character;
	private int score;
	
	//Constructors
	public Player(String name) {
		this.name = name;
	}
	
	//Method for setting base character variable
	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	//Method for retrieving score
	public int getScore() {
		return score;
	}
	
	//Method for setting score
	public void setScore(int score) {
		this.score = score;
	}
			
	//Method for retrieving name string
	public String getName() {
		return name;
	}
	
	
	public void die(){
		
	}

	
}
