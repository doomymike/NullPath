import java.awt.image.BufferedImage;

public class Bomb extends Item {
  
  private static BufferedImage sprite; //Need to set sprite
  
  public Bomb(int x, int y) {
    super(x,y);
  }
  
  public void explode() {
  }
  
  public static BufferedImage getSprite() {
    return sprite;
  }
  
}
