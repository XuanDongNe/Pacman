package game.model;

import game.model.entities.ghosts.Ghost;
import game.model.entities.pacman.PacGum;
import game.model.entities.pacman.SuperPacGum;

//Lớp interface thể hiện thông báo 
public interface Sujet {

	void registerObserver(Observer observer);

	void removeObserver(Observer observer);

	void notifyObserverPacGumEaten(PacGum pg);

	void notifyObserverSuperPacGumEaten(SuperPacGum spg);

	void notifyObserverGhostCollision(Ghost gh);
}
