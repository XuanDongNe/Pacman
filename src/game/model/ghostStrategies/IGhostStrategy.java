package game.model.ghostStrategies;

//Giao diện mô tả hành động của các loại bóng ma khác nhau trong trò chơi
public interface IGhostStrategy {
	int[] getChaseTargetPosition(); // Vị trí mục tiêu khi bóng ma đang (theo dõi hoặc săn đuổi) Pacman

	int[] getScatterTargetPosition(); // Vị trí mục tiêu khi bóng ma đang (phân tán hoặc tránh)

}