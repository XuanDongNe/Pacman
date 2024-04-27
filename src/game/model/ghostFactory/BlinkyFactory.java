package game.model.ghostFactory;

import game.model.entities.ghosts.Blinky;
import game.model.entities.ghosts.Ghost;

//Tạo ra bóng ma Blinky trong trò chơi
public class BlinkyFactory extends AbstractGhostFactory {
	@Override
	public Ghost makeGhost(int xPos, int yPos) {
		return new Blinky(xPos, yPos);
	}

}
