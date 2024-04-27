package game.model.ghostStrategies;

import game.controller.Game;
import game.controller.GameplayPanel;
import game.model.entities.ghosts.Ghost;
import game.model.utils.Utils;

//Lớp này thể hiện chiến lược di chuyển của con ma Clyde trong trò chơi
public class ClydeStrategy implements IGhostStrategy {
	private Ghost ghost;

	public ClydeStrategy(Ghost ghost) {
		this.ghost = ghost;
	}

	// Trả về tọa độ (x, y) của đối tượng Pacman nếu khoảng cách giữa 2 vị trí nằm
	// ngoài bán kính 8 ô vuông, là mục tiêu mà con ma Clyde sẽ săn đuổi trong trạng
	// thái "chase"
	@Override
	public int[] getChaseTargetPosition() {
		if (Utils.getDistance(ghost.getxPos(), ghost.getyPos(), Game.getPacman().getxPos(),
				Game.getPacman().getyPos()) >= 256) {
			int[] position = new int[2];
			position[0] = Game.getPacman().getxPos();
			position[1] = Game.getPacman().getyPos();
			return position;
		} else {
			return getScatterTargetPosition();
		}
	}

	// Trả về tọa độ (x, y) của vị trí mục tiêu trong trạng thái "scatter"(phân
	// tán), Clyde sẽ di chuyển đến góc trên bên trái của bảng chơi.
	@Override
	public int[] getScatterTargetPosition() {
		int[] position = new int[2];
		position[0] = 0;
		position[1] = GameplayPanel.height;
		return position;
	}
}
