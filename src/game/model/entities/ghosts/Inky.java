package game.model.entities.ghosts;

import game.controller.Game;
import game.model.ghostStrategies.InkyStrategy;

//Lớp cụ thể của Inky (con ma màu xanh)
public class Inky extends Ghost {
	public Inky(int xPos, int yPos) {
		super(xPos, yPos, "inky.png");
		setStrategy(new InkyStrategy(Game.getBlinky()));
	}
}
