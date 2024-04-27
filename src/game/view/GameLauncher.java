package game.view;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.controller.GameplayPanel;

//Điểm vào của ứng dụng
public class GameLauncher {
	private static UIPanel uiPanel;

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setTitle("Pacman"); // Đặt tiêu đề của cửa sổ là "Pacman"
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Thiết lập hành động mặc định khi đóng cửa sổ là kết
																// thúc ứng dụng

		JPanel gameWindow = new JPanel(); // Tạo một JPanel để chứa giao diện trò chơi và giao diện người dùng

		// Tạo "vùng chơi" của trò chơi
		try {
			gameWindow.add(new GameplayPanel(448, 496)); // Thêm một GameplayPanel vào gameWindow với kích thước 448x496
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Tạo giao diện người dùng (để hiển thị điểm)
		uiPanel = new UIPanel(256, 496); // Tạo một UIPanel mới với kích thước 256x496
		gameWindow.add(uiPanel); // Thêm UIPanel vào gameWindow

		window.setContentPane(gameWindow);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	// Phương thức để truy cập UIPanel từ các lớp khác
	public static UIPanel getUIPanel() {
		return uiPanel;
	}
}
