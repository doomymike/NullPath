/**
 * [Bomb.java]
 * Bomb object for NullPath game
 * @author Brian Li, James Liang, Michael Oren, Brian Zhang
 * January 22, 2019
 */

//java imports
import java.awt.image.BufferedImage;

public class Bomb extends Item {
  
  private static BufferedImage sprite; //Sprite for bomb
    private static int effectRadius = 30; //radius for what bomb can affect
  
  /**
   * Bomb
   * Constructor that makes a bomb with given coordinates
   * @param x, integer representing x coordinate
   */
  public Bomb(int x, int y) {
    super(x,y);
  } //End of constructor
  
  /**
   * getSprite
   * Returns the sprite for bombs
   * @return BufferedImage sprite, representing sprite of bomb
   */
  public static BufferedImage getSprite() {
    return sprite;
  } //End of getSprite

  /**
   * getEffectRadius
   * Returns the effect radius for a bomb
   * @return int effectRadius, representing radius of effect of bomb
   */
  public static int getEffectRadius() {
      return effectRadius;
  } //End of getEffectRadius

} //End of class
