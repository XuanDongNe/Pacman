package game.model.entities.ghosts;

import game.model.ghostStrategies.ClydeStrategy;

//Lớp cụ thể của Clyde (con ma màu vàng)
public class Clyde extends Ghost {
	public Clyde(int xPos, int yPos) {
		super(xPos, yPos, "clyde.png");
		setStrategy(new ClydeStrategy(this));
	}
}
