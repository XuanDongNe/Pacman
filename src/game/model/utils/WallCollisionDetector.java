package game.model.utils;

import java.awt.Rectangle;

import game.controller.Game;
import game.model.entities.Entity;
import game.model.entities.Wall;
import game.model.entities.ghosts.GhostHouse;

//Lớp để phát hiện va chạm giữa một đối tượng và một bức tường (so với lớp CollisionDetector, các tường là tĩnh)
public class WallCollisionDetector {

	// Phương thức để kiểm tra xem có tường nào ở vị trí của một đối tượng + một
	// delta cụ thể không
	// (delta này giúp phát hiện tường trước khi đi vào)
	public static boolean checkWallCollision(Entity obj, int dx, int dy) {
		Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
		for (Wall w : Game.getWalls()) {
			if (w.getHitbox().intersects(r))
				return true;
		}
		return false;
	}

	// Tương tự như phương thức trước, nhưng ở đây có thể bỏ qua va chạm với các
	// tường của nhà ma
	public static boolean checkWallCollision(Entity obj, int dx, int dy, boolean ignoreGhostHouses) {
		Rectangle r = new Rectangle(obj.getxPos() + dx, obj.getyPos() + dy, obj.getSize(), obj.getSize());
		for (Wall w : Game.getWalls()) {
			if (!(ignoreGhostHouses && w instanceof GhostHouse) && w.getHitbox().intersects(r))
				return true;
		}
		return false;
	}
}
