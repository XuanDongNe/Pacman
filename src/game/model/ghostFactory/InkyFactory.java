package game.model.ghostFactory;

import game.model.entities.ghosts.Ghost;
import game.model.entities.ghosts.Inky;

//Tạo ra bóng ma Inky trong trò chơi
public class InkyFactory extends AbstractGhostFactory {
	@Override
	public Ghost makeGhost(int xPos, int yPos) {
		return new Inky(xPos, yPos);
	}

}
