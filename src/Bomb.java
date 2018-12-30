import java.awt.BufferedImage;

public class Bomb extends Item {
  
  private static BufferedImage sprite; //Need to set sprite
  
  public Bomb(int x, int y) {
    super(x,y);
  }
  
  public static int getRadius() {
    return 25; //Placeholder radius
  }
  
  public void explode() {
  }
  
  public static BufferedImage getSprite() {
    return sprite;
  }
  
}
