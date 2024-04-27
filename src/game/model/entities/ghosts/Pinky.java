package game.model.entities.ghosts;

import game.model.ghostStrategies.PinkyStrategy;

//Lớp cụ thể của Inky (con ma màu hong)
public class Pinky extends Ghost {
  public Pinky(int xPos, int yPos) {
      super(xPos, yPos, "pinky.png");
      setStrategy(new PinkyStrategy());
  }
}
