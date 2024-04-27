package game.model;

import game.model.entities.ghosts.Ghost;
import game.model.entities.pacman.PacGum;
import game.model.entities.pacman.SuperPacGum;

public interface Observer {
	void updatePacGumEaten(PacGum pg);

	void updateSuperPacGumEaten(SuperPacGum spg);

	void updateGhostCollision(Ghost gh);
}

