package game.model.ghostStates;

import game.model.entities.ghosts.Ghost;

//Lớp để mô tả trạng thái cụ thể của một con ma đang trong trạng thái phân tán
public class ScatterMode extends GhostState {
	public ScatterMode(Ghost ghost) {
		super(ghost);
	}

	// Chuyển trạng thái khi một viên SuperPacGum được ăn
	@Override
	public void superPacGumEaten() {
		ghost.switchFrightenedMode();
	}

	// Chuyển trạng thái khi hết thời gian của trạng thái hiện tại (nó xen kẽ giữa
	// ChaseMode và ScatterMode)
	@Override
	public void timerModeOver() {
		ghost.switchChaseMode();
	}

	// Trong trạng thái này, vị trí mục tiêu phụ thuộc vào chiến lược của con ma
	@Override
	public int[] getTargetPosition() {
		return ghost.getStrategy().getScatterTargetPosition();
	}
}