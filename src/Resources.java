import java.io.BufferedImage;

public class Resources {

	private static SimpleLinkedList<Stage> stages = new SimpleLinkedList<Stage>();
	//Add all stages to list
	
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
	
}
