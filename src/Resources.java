/**
 * Resources
 * Object that contains players, stages, and characters for the game
 * Authors: Brian Li, James Liang, Michael Oren, Brian Zhang
 * Date: January 21, 2019
 */

// Java imports
import java.io.*;

public class Resources {

	private static SimpleLinkedList<Stage> stages = new SimpleLinkedList<Stage>(); // List of stages
	
	private SimpleLinkedList<Character> characters = new SimpleLinkedList<Character>(); // List of characters
	
	private Stage currentStage; // Stores the current stage being played on
	
	private SimpleLinkedList<Player> players = new SimpleLinkedList<Player>(); // List of players

	/**
	 * Resources
	 * Constructor that makes a resources object with characters and stages in the game
	 */
	Resources() {
		
		//Initialize character objects (will add players when players are assigned)
		try {
			characters.add(new Character("Blue"));
			characters.add(new Character("Green"));
			characters.add(new Character("Red"));
			characters.add(new Character("Yellow"));
		} catch (IOException e) {
			
		}
		
		//Add all stages to list
		stages.add(new Stage("SkyFortress"));
		
	} // End of constructor

	/**
	 * getStages
	 * Returns list of stages
	 * @return SimpleLInkedList<Stage> of stages
	 */
	public static SimpleLinkedList<Stage> getStages() {
		return stages;
	} // End of getStages

	/**
	 * getRandomStage
	 * Returns a random stage from list of stages
	 * @return Random Stage object
	 */
	public static Stage getRandomStage() {
		if (stages.size() != 0) {
			int rand = (int)(Math.round(Math.random()*(stages.size()-1)));
			return stages.get(rand); // Choose random stage
		}
		return null;
	} // End of getRandomStage

	/**
	 * getCharacters
	 * Returns list of characters
	 * @return SimpleLinkedList<Character> of characters
	 */
	public SimpleLinkedList<Character> getCharacters() {
		return characters;
	} // End of getCharacters

	/**
	 * getRandomCharacter
	 * Returns a random character from list of characters
	 * @return Random character object
	 */
	public Character getRandomCharacter() {
		if (characters.size() != 0) {
			int rand = (int)(Math.round(Math.random()*(characters.size()-1)));
			return characters.get(rand); // Choose random character
		}
		return null;
	} // End of getRandomCharacter

	/**
	 * setCurrentStage
	 * Sets the active stage being played on
	 * @param stage, the stage to be set to the active stage
	 */
	public void setCurrentStage(Stage stage) {
		currentStage = stage;
	} // End of setCurrentStage

	/**
	 * getCurrentStage
	 * Returns the active stage
	 * @return Stage object representing the current stage played on
	 */
	public Stage getCurrentStage() {
		return currentStage;
	} // End of getCurrentStage

	/**
	 * getPlayers
	 * Returns list of players
	 * @return SimpleLinkedList<Player> of players
	 */
	public SimpleLinkedList<Player> getPlayers() {
		return players;
	} // End of getPlayers

	/**
	 * addPlayer
	 * Adds player to list of players
	 * @param name, String representing name of player added
	 */
	public void addPlayer(String name) {
		players.add(new Player(name));
	} // End of addPlayer

	/**
	 * removePlayer
	 * Removes player from list of players
	 * @param name, String representing name of player to be removed
	 */
	public void removePlayer(String name) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getName().equals(name)) {
				players.remove(i);
			}
		}
	} // End of removePlayer
	
} // End of class
