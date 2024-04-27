package game.model.ghostFactory;

import game.model.entities.ghosts.Ghost;
import game.model.entities.ghosts.Pinky;

//Tạo ra bóng ma Pinky trong trò chơi
public class PinkyFactory extends AbstractGhostFactory {
	@Override
	public Ghost makeGhost(int xPos, int yPos) {
		return new Pinky(xPos, yPos);
	}

}
