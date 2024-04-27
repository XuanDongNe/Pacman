package game.model.ghostFactory;

import game.model.entities.ghosts.Ghost;

//Tạo ra các đối tượng bóng ma khác nhau trong trò chơi
public abstract class AbstractGhostFactory {
	public abstract Ghost makeGhost(int xPos, int yPos);	
}

