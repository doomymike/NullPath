
public class Player {

	private String name;
	private Character character;
	private int score;
	
	
	public Player(String name) {
		this.name = name;
	}
	
	public void setCharacter(Character character) {
		this.character = character;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
			
	public String getName() {
		return name;
	}
	
	public void die(){
		
	}

	
}
