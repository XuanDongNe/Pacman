package game.model.ghostStrategies;

import game.controller.Game;
import game.model.utils.Utils;

//Lớp này thể hiện chiến lược di chuyển của con ma Pinky trong trò chơi
public class PinkyStrategy implements IGhostStrategy {
	// Trả về tọa độ (x, y) của đối tượng Pacman. Trong trạng thái "chase"(săn
	// đuổi), Pinky sẽ di chuyển đến vị trí hai ô vuông trước mặt Pacman
	@Override
	public int[] getChaseTargetPosition() {
		int[] position = new int[2];
		int[] pacmanFacingPosition = Utils.getPointDistanceDirection(Game.getPacman().getxPos(),
				Game.getPacman().getyPos(), 64, Utils.directionConverter(Game.getPacman().getDirection()));
		position[0] = pacmanFacingPosition[0];
		position[1] = pacmanFacingPosition[1];
		return position;
	}

	// Trả về tọa độ (x, y) của vị trí mục tiêu trong trạng thái "scatter"(phân
	// tán), Pinky sẽ di chuyển đến góc trên bên trái của bảng chơi.
	@Override
	public int[] getScatterTargetPosition() {
		int[] position = new int[2];
		position[0] = 0;
		position[1] = 0;
		return position;
	}
}
