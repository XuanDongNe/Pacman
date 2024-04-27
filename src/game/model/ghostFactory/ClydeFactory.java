package game.model.ghostFactory;

import game.model.entities.ghosts.Clyde;
import game.model.entities.ghosts.Ghost;

//Tạo ra bóng ma Clyde trong trò chơi
public class ClydeFactory extends AbstractGhostFactory {
	@Override
	public Ghost makeGhost(int xPos, int yPos) {
		return new Clyde(xPos, yPos);
	}

}
