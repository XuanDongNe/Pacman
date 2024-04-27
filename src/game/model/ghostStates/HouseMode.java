package game.model.ghostStates;

import game.model.entities.ghosts.Ghost;
import game.model.utils.Utils;
import game.model.utils.WallCollisionDetector;

//Lớp để mô tả trạng thái cụ thể của một con ma trong nhà của nó
public class HouseMode extends GhostState{
	public HouseMode(Ghost ghost) {
		super(ghost);
	}

	// Chuyển trạng thái khi con ma ra khỏi nhà
	@Override
	public void outsideHouse() {
		this.ghost.switchChaseModeOrScatterMode();
	}

	// Trong trạng thái này, vị trí mục tiêu là ô phía trên nhà của các con ma
	@Override
	public int[] getTargetPosition(){
		int[] position = new int[2];
		position[0] = 208;
		position[1] = 168;
		return position;
	}

	// Tương tự như phương thức của lớp trừu tượng, nhưng ở đây ta bỏ qua các va chạm với tường trong nhà của các con ma
	@Override
	public void computeNextDir() {
		int new_xSpd = 0;
		int new_ySpd = 0;

		if (!ghost.onTheGrid()) return;
		if (!ghost.onGameplayWindow()) return;

		double minDist = Double.MAX_VALUE;

		if (ghost.getxSpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, -ghost.getSpd(), 0, true)) {
			double distance = Utils.getDistance(ghost.getxPos() - ghost.getSpd(), ghost.getyPos(), getTargetPosition()[0], getTargetPosition()[1]);
			if (distance < minDist) {
				new_xSpd = -ghost.getSpd();
				new_ySpd = 0;
				minDist = distance;
			}
		}
		if (ghost.getxSpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, ghost.getSpd(), 0, true)) {
			double distance = Utils.getDistance(ghost.getxPos() + ghost.getSpd(), ghost.getyPos(),  getTargetPosition()[0], getTargetPosition()[1]);
			if (distance < minDist) {
				new_xSpd = ghost.getSpd();
				new_ySpd = 0;
				minDist = distance;
			}
		}
		if (ghost.getySpd() <= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, -ghost.getSpd(), true)) {
			double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() - ghost.getSpd(), getTargetPosition()[0], getTargetPosition()[1]);
			if (distance < minDist) {
				new_xSpd = 0;
				new_ySpd = -ghost.getSpd();
				minDist = distance;
			}
		}
		if (ghost.getySpd() >= 0 && !WallCollisionDetector.checkWallCollision(ghost, 0, ghost.getSpd(), true)) {
			double distance = Utils.getDistance(ghost.getxPos(), ghost.getyPos() + ghost.getSpd(), getTargetPosition()[0], getTargetPosition()[1]);
			if (distance < minDist) {
				new_xSpd = 0;
				new_ySpd = ghost.getSpd();
				minDist = distance;
			}
		}

		if (new_xSpd == 0 && new_ySpd == 0) return;

		if (java.lang.Math.abs(new_xSpd) != java.lang.Math.abs(new_ySpd)) {
			ghost.setxSpd(new_xSpd);
			ghost.setySpd(new_ySpd);
		} else {
			if (ghost.getxSpd() != 0) {
				ghost.setxSpd(0);
				ghost.setxSpd(new_ySpd);
			}else{
				ghost.setxSpd(new_xSpd);
				ghost.setySpd(0);
			}
		}
	}
}

