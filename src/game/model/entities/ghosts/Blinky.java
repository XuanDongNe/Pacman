package game.model.entities.ghosts;

import game.model.ghostStrategies.BlinkyStrategy;

//Lớp cụ thể của Blinky (con ma màu đỏ)
public class Blinky extends Ghost {
	public Blinky(int xPos, int yPos) {
		super(xPos, yPos, "blinky.png");
		setStrategy(new BlinkyStrategy());
	}
}