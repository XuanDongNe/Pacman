package game.model.utils;

import game.controller.Game;
import game.model.entities.Entity;

//Lớp để phát hiện va chạm giữa hai đối tượng
public class CollisionDetector {
	private Game game;

	public CollisionDetector(Game game) {
		this.game = game;
	}

	/*
	 * Phát hiện va chạm giữa các đối tượng kiểu collisionCheck và một đối tượng 
	 * obj; trả về đối tượng đã được kiểm tra nếu có va chạm  Các đối tượng
	 * kiểu collisionCheck có hitbox là hình chữ nhật, và ở đây giả sử  hitbox của
	 * đối tượng obj là một điểm  (trong trường hợp va chạm giữa Pacman và ma,
	 * điều này giúp tạo ra một phần  kết thúc hơi mở và không quá nghiêm trọng)
	 */
	public Entity checkCollision(Entity obj, Class<? extends Entity> collisionCheck) {
		for (Entity e : game.getEntities()) {
			if (!e.isDestroyed() && collisionCheck.isInstance(e)
					&& e.getHitbox().contains(obj.getxPos() + obj.getSize() / 2, obj.getyPos() + obj.getSize() / 2))
				return e;
		}
		return null;
	}

	// Tương tự như phương thức trước, nhưng tất cả các hitbox đều được xem xét là
	// hình chữ nhật
	public Entity checkCollisionRect(Entity obj, Class<? extends Entity> collisionCheck) {
		for (Entity e : game.getEntities()) {
			if (!e.isDestroyed() && collisionCheck.isInstance(e) && e.getHitbox().intersects(obj.getHitbox()))
				return e;
		}
		return null;
	}
}