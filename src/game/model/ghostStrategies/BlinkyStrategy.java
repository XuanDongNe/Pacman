package game.model.ghostStrategies;

import game.controller.Game;
import game.controller.GameplayPanel;

//Lớp này thể hiện chiến lược di chuyển của con ma Blinky trong trò chơi
public class BlinkyStrategy implements IGhostStrategy {
	// Trả về tọa độ (x, y) của đối tượng Pacman, là vị trí mục tiêu mà con ma
	// Blinky sẽ săn đuổi trong trạng thái "chase"
	@Override
	public int[] getChaseTargetPosition() {
		int[] position = new int[2];
		position[0] = Game.getPacman().getxPos();
		position[1] = Game.getPacman().getyPos();
		return position;
	}

	// Trả về tọa độ (x, y) của vị trí mục tiêu trong trạng thái "scatter"(phân
	// tán), Blinky sẽ di chuyển đến góc trên bên phải của bảng chơi.
	@Override
	public int[] getScatterTargetPosition() {
		int[] position = new int[2];
		position[0] = GameplayPanel.width;
		position[1] = 0;
		return position;
	}
}