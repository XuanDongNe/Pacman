package game.model.entities.pacman;

import java.util.ArrayList;
import java.util.List;

import game.controller.Game;
import game.model.Observer;
import game.model.Sujet;
import game.model.entities.MovingEntity;
import game.model.entities.ghosts.Ghost;
import game.model.utils.CollisionDetector;
import game.model.utils.KeyHandler;
import game.model.utils.WallCollisionDetector;

//Class để tạo nhân vật pacman
public class Pacman extends MovingEntity implements Sujet {
	private CollisionDetector collisionDetector; // Va chạm
	private List<Observer> observerCollection; // Quan sát

	public Pacman(int xPos, int yPos) {
		// size = 32, xPos, yPod, spd = 2, img = pacman.png, số lượng hình ảnh trong chu
		// kỳ là 4, tốc độ chuyển đổi giữa các hình ảnh là 0.3f
		super(32, xPos, yPos, 2, "pacman.png", 4, 0.3f);

		observerCollection = new ArrayList<>();
	}

	// Xử lý các sự kiện đầu vảo để điều khiển Pacman
	// KeyHandler xử lý sự kiện liên quan đến bàn phím
	public void input(KeyHandler k) {
		int new_xSpd = 0;
		int new_ySpd = 0;

		if (!onTheGrid())
			return; // Pacman phải ở trên 1 khu vực hình vuông trong trò chơi
		if (!onGameplayWindow())
			return; // Pacman phải ở trong game

		// Tùy theo phím bấm để thay đổi hướng của Pacman
		// Nếu bên trái và tốc độ di chuyển theo chiều ngang >=0(tiến) và nếu không có
		// sự va chạm với các bức tường(tham số nhận vào là pacman và vị trí x, y)
		// thì di chuyển trái, phải, lên, xuống
		if (k.k_left.isPressed && xSpd >= 0 && !WallCollisionDetector.checkWallCollision(this, -spd, 0)) {
			new_xSpd = -spd; // qua trái
		}
		if (k.k_right.isPressed && xSpd <= 0 && !WallCollisionDetector.checkWallCollision(this, spd, 0)) {
			new_xSpd = spd; // qua phải
		}
		if (k.k_up.isPressed && ySpd >= 0 && !WallCollisionDetector.checkWallCollision(this, 0, -spd)) {
			new_ySpd = -spd; // di chuyển lên
		}
		if (k.k_down.isPressed && ySpd <= 0 && !WallCollisionDetector.checkWallCollision(this, 0, spd)) {
			new_ySpd = spd; // di chuyển xuống
		}

		// Nếu tốc độ mới theo x và y bằng 0 thì không có nút nào được nhấn và không có
		// gì phải làm nên return
		if (new_xSpd == 0 && new_ySpd == 0)
			return;

		// Kiểm tra xem có input đầu vào đầu tiên từ nguời chơi chưa
		if (!Game.getFirstInput())
			Game.setFirstInput(true);

		// Kiểm tra xem hướng di chuyển mới có khác nhau không
		// Cập nhật lại x,ySPD để đi theo hướng mới
		if (Math.abs(new_xSpd) != Math.abs(new_ySpd)) {
			xSpd = new_xSpd;
			ySpd = new_ySpd;
		} else {// Nếu = nhau nghĩa là chưa có đổi hướng và tiếp tục di chueyenr theo hướng hiện
				// tại
			if (xSpd != 0) {
				xSpd = 0;
				ySpd = new_ySpd;
			} else {
				xSpd = new_xSpd;
				ySpd = 0;
			}
		}
	}

	@Override
	public void update() {
		// Kiểm tra xem mỗi lần Pacman tiếp xúc với Pacgum hay SuperPacgum hay ma không
		// và khi gặp ma hoặc ăn superpacgum sẽ được thông báo
		PacGum pg = (PacGum) collisionDetector.checkCollision(this, PacGum.class);// Tạo ra Pacgum và Pacgum này có bị
																					// ma ăn hay không
		if (pg != null) {// Nếu không có pacgum trả về nghĩa là chưa va chạm
			notifyObserverPacGumEaten(pg);// Sẽ thông báo đến pacgum
		}

		// Tương tựu như pacgum
		SuperPacGum spg = (SuperPacGum) collisionDetector.checkCollision(this, SuperPacGum.class);
		if (spg != null) {
			// Thông báo bằng cách biến mất hoặc chớp nháy
			notifyObserverSuperPacGumEaten(spg);
		}

		// Tương tự như Pacgum
		Ghost gh = (Ghost) collisionDetector.checkCollision(this, Ghost.class);
		if (gh != null) {
			// Thông báo bằng cách biến mất hoặc chớp nháy
			notifyObserverGhostCollision(gh);
		}

		// Nếu va chạm với bức tường thì update lại Position
		if (!WallCollisionDetector.checkWallCollision(this, xSpd, ySpd)) {
			updatePosition();
		}
	}

	public void setCollisionDetector(CollisionDetector collisionDetector) {
		this.collisionDetector = collisionDetector;
	}

	@Override
	public void registerObserver(Observer observer) {
		observerCollection.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observerCollection.remove(observer);
	}

	@Override
	public void notifyObserverPacGumEaten(PacGum pg) {
		observerCollection.forEach(obs -> obs.updatePacGumEaten(pg));
	}

	@Override
	public void notifyObserverSuperPacGumEaten(SuperPacGum spg) {
		observerCollection.forEach(obs -> obs.updateSuperPacGumEaten(spg));
	}

	@Override
	public void notifyObserverGhostCollision(Ghost gh) {
		observerCollection.forEach(obs -> obs.updateGhostCollision(gh));
	}
}
