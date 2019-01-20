import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Item {
	private int x,y,height, width, radius;
	private double dx,dy;
	private boolean affectGravity = true;
	private ArrayList<Integer> charIntersect = new ArrayList<Integer>(); //Every item contains a reference of which characters it's intersecting with
	private Player placer;
	private BufferedImage image;
	private SimpleLinkedList<BufferedImage> sprites;
	
	public void printCharLen() {
		System.out.println(charIntersect.size());
	}
	
	public boolean getGravity() {
		return affectGravity;
	}
	
	public void addChar(int uniqueTag) {
		charIntersect.add(uniqueTag);
	}
	
	public void removeChar(int uniqueTag) {
		charIntersect.remove(uniqueTag);
	}
	
	public boolean checkChar(int uniqueTag) {
		for (int i = 0; i < charIntersect.size(); i++) {
			if (charIntersect.get(i) == uniqueTag) {
				return true;
			}
		}
		return false;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public double[] getVel() {
		double[] vel = {dx, dy};
		return vel;
	}
	
	public int getRadius() {
		return radius;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	
	public void setRadius(int rad) {
		radius = rad;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setDx(double newDx) {
		dx = newDx;
	}
	
	public void setDy(double newDy) {
		dy = newDy;
	}
	
	Item(int x, int y, int height, int width, boolean affectGravity){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.affectGravity = affectGravity;
	}
	
	Item(int x, int y, int height, int width){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	//Moving Platform Constructor
	Item(int x, int y, int height, int width, double[] speed){
		this.affectGravity = false;
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.dx = speed[0];
		this.dy = speed[1];
	}
	
	Item(int x, int y, int height, int width, int speed, int direction1, int direction2){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	Item(int x, int y, int height, int width, int radius){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.radius=radius;
	}
	
	Item(int x, int y, int height, int width, int radius, double[] speed, boolean affectGravity){
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.radius=radius;
		this.dx = speed[0];
		this.dy = speed[1];
		this.affectGravity = affectGravity;
	}
	
	Item(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Player getPlacer() {
		return placer;
	}

	public void setPlacer(Player placer) {
		this.placer = placer;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public SimpleLinkedList<BufferedImage> getSprites() {
		return sprites;
	}

	public void addSprite(BufferedImage sprite) {
		if(this.sprites==null) {
			this.sprites = new SimpleLinkedList<BufferedImage>();
		}
		this.sprites.add(sprite);
	}
	
}
