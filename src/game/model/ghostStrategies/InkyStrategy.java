package game.model.ghostStrategies;

import game.controller.Game;
import game.controller.GameplayPanel;
import game.model.entities.ghosts.Ghost;
import game.model.utils.Utils;

//Lớp này thể hiện chiến lược di chuyển của con ma Inky trong trò chơi
public class InkyStrategy implements IGhostStrategy{
 private Ghost otherGhost;
 public InkyStrategy(Ghost ghost) {
     this.otherGhost = ghost;
 }

 // Trả về tọa độ (x, y) của đối tượng Pacman. Trong trạng thái "chase"(săn đuổi), Inky sẽ tính toán vị trí mục tiêu dựa trên vị trí của con ma Blinky và vị trí trước mặt của Pacman
 @Override
 public int[] getChaseTargetPosition() {
     int[] position = new int[2];
     int[] pacmanFacingPosition = Utils.getPointDistanceDirection(Game.getPacman().getxPos(), Game.getPacman().getyPos(), 32d, Utils.directionConverter(Game.getPacman().getDirection()));
     double distanceOtherGhost = Utils.getDistance(pacmanFacingPosition[0], pacmanFacingPosition[1], otherGhost.getxPos(), otherGhost.getyPos());
     double directionOtherGhost = Utils.getDirection(otherGhost.getxPos(), otherGhost.getyPos(), pacmanFacingPosition[0], pacmanFacingPosition[1]);
     int[] blinkyVectorPosition = Utils.getPointDistanceDirection(pacmanFacingPosition[0], pacmanFacingPosition[1], distanceOtherGhost, directionOtherGhost);
     position[0] = blinkyVectorPosition[0];
     position[1] = blinkyVectorPosition[1];
     return position;
 }

 // Trả về tọa độ (x, y) của vị trí mục tiêu trong trạng thái "scatter"(phân tán), Inky sẽ di chuyển đến góc trên bên phải của bảng chơi.
 @Override
 public int[] getScatterTargetPosition() {
     int[] position = new int[2];
     position[0] = GameplayPanel.width;
     position[1] = GameplayPanel.height;
     return position;
 }
}
