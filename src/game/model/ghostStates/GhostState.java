package game.model.ghostStates;

import game.model.entities.ghosts.Ghost;
import game.model.utils.Utils;
import game.model.utils.WallCollisionDetector;

//Lớp trừu tượng để mô tả các trạng thái khác nhau của con ma
public abstract class GhostState {
	protected Ghost ghost;

	public GhostState(Ghost ghost) {
		this.ghost = ghost;
	}

	// Các chuyển đổi khác nhau từ một trạng thái sang trạng thái khác
	public void superPacGumEaten() {}
	public void timerModeOver() {}
	public void timerFrightenedModeOver() {}
	public void eaten() {}
	public void outsideHouse() {}
	public void insideHouse() {}

	public int[] getTargetPosition(){
		return new int[2];
	} // trả về điểm mà con ma sẽ nhắm tới

	// Phương thức để tính toán hướng tiếp theo mà con ma sẽ đi
	public void computeNextDir() {
		int new_xSpd = 0;
		int new_ySpd = 0;

		if (!ghost.onTheGrid()) return; // Con ma phải ở trên "ô" của khu vực chơi
		if (!ghost.onGameplayWindow()) return;  // Con ma phải trong khu vực chơi

		double minDist = Double.MAX_VALUE; // Khoảng cách tối thiểu hiện tại giữa con ma và mục tiêu theo hướng tiếp theo của nó

		// Nếu con ma hiện đang đi về bên trái và không có tường bên trái ...
		if (ghost.getxSpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, -ghost.getSpd(), 0)) {
			// Chúng ta xem xét khoảng cách giữa vị trí mục tiêu và vị trí tiềm năng của con ma nếu nó đi về bên trái
			double distance = Utils.getDistance(ghost.getxPos() - ghost.getSpd(), ghost.getyPos(), getTargetPosition()[0], getTargetPosition()[1]);

			// Nếu khoảng cách này nhỏ hơn khoảng cách tối thiểu hiện tại, chúng ta nói rằng con ma đang đi về bên trái và cập nhật khoảng cách tối thiểu
			if (distance < minDist) {
				new_xSpd = -ghost.getSpd();
				new_ySpd = 0;
				minDist = distance;
			}
		}

		// Tương tự cho hướng bên phải
		if (ghost.getxSpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, ghost.getSpd(), 0)) {
			double distance = Utils.getDistance(ghost.getxPos() + ghost.getSpd(), ghost.getyPos(),  getTargetPosition()[0], getTargetPosition()[1]);
			if (distance < minDist) {
				new_xSpd = ghost.getSpd();
				new_ySpd = 0;
				minDist = distance;
			}
		}

		// Tương tự cho hướng lên
		if (ghost.getySpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, -ghost.getSpd())) {
			double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() - ghost.getSpd(), getTargetPosition()[0], getTargetPosition()[1]);
			if (distance < minDist) {
				new_xSpd = 0;
				new_ySpd = -ghost.getSpd();
				minDist = distance;
			}
		}

		// Tương tự cho hướng xuống
		if (ghost.getySpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, ghost.getSpd())) {
			double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() + ghost.getSpd(), getTargetPosition()[0], getTargetPosition()[1]);
			if (distance < minDist) {
				new_xSpd = 0;
				new_ySpd = ghost.getSpd();
				minDist = distance;
			}
		}

		if (new_xSpd == 0 && new_ySpd == 0) return;

		// Khi đã kiểm tra tất cả các trường hợp, chúng ta thay đổi hướng của con ma (trong trường hợp, do hướng này được xác định bằng một vận tốc ngang và một vận tốc dọc, chúng ta vẫn thực hiện một kiểm tra để đảm bảo rằng nó không thể đi theo đường chéo)
		if (Math.abs(new_xSpd) != Math.abs(new_ySpd)) {
			ghost.setxSpd(new_xSpd);
			ghost.setySpd(new_ySpd);
		} else {
			if (new_xSpd != 0) {
				ghost.setxSpd(0);
				ghost.setxSpd(new_ySpd);
			}else{
				ghost.setxSpd(new_xSpd);
				ghost.setySpd(0);
			}
		}
	}
}