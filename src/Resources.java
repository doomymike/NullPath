//0 = left, 

import java.io.BufferedImage;

public class Resources {

	private static SimpleLinkedList<Stage> stages = new SimpleLinkedList<Stage>();
	//Add all stages to list
	
	private SimpleLinkedList<Character> characters = new SimpleLinkedList<Character>();
	//Initialize character objects (will add players when players are assigned)
	
	private Stage currentStage;
	
	private SimpleLinkedList<Player> players = new SimpleLinkedList<Player>();
	
	Resources() {
		//Default??
	}
	
	public static SimpleLinkedList<Stage> getStages() {
		return stages;
	}
	
	public static Stage getRandomStage() {
		if (stages.size() != 0) {
			int rand = (int)(Math.round(Math.rand()*(stages.size()-1)));
			return stages.get(rand);
		}
	}
	
	public SimpleLinkedList<Character> getCharacters() {
		return characters;
	}
	
	public Character getRandomCharacter() {
		if (characters.size() != 0) {
			int rand = (int)(Math.round(Math.rand()*(characters.size()-1)));
			return characters.get(rand);
		}
	}
	
	public void setCurrentStage(Stage stage) {
		currentStage = stage;
	}
	
	public Stage getCurrentStage() {
		return currentStage;
	}
	
	public SimpleLinkedList<Player> getPlayers() {
		return players;
	}
	
	public void addPlayer(String name) {
		players.add(new Player(name));
	}
	
	public void removePlayer(String name) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(name)) {
				players.remove(i);
			}
		}
	}
	
}
