package game.model.ghostStates;

import game.model.entities.ghosts.Ghost;
import game.model.utils.Utils;

//Lớp để mô tả trạng thái cụ thể của một con ma đang sợ hãi (sau khi Pacman đã ăn một viên SuperPacGum)
public class FrightenedMode extends GhostState {
	public FrightenedMode(Ghost ghost) {
		super(ghost);
	}

	// Chuyển trạng thái khi con ma bị ăn
	@Override
	public void eaten() {
		ghost.switchEatenMode();
	}

	// Chuyển trạng thái khi hết thời gian của trạng thái sợ hãi
	@Override
	public void timerFrightenedModeOver() {
		ghost.switchChaseModeOrScatterMode();
	}

	// Trong trạng thái này, vị trí mục tiêu là một ô ngẫu nhiên xung quanh con ma
	@Override
	public int[] getTargetPosition() {
		int[] position = new int[2];

		boolean randomAxis = Utils.randomBool();
		position[0] = ghost.getxPos() + (randomAxis ? Utils.randomInt(-1, 1) * 32 : 0);
		position[1] = ghost.getyPos() + (!randomAxis ? Utils.randomInt(-1, 1) * 32 : 0);
		return position;
	}
}
