import java.awt.image.BufferedImage;

public class Bomb extends Item {
  
  private static BufferedImage sprite; //Need to set sprite
    private static int effectRadius = 30; //Placeholder value
  
  public Bomb(int x, int y) {
    super(x,y);
  }
  
  public static BufferedImage getSprite() {
    return sprite;
  }

  public static int getEffectRadius() {
      return effectRadius;
  }

}
