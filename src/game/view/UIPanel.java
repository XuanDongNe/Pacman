package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import game.controller.GameplayPanel;
import game.model.Observer;
import game.model.entities.ghosts.Ghost;
import game.model.entities.pacman.PacGum;
import game.model.entities.pacman.SuperPacGum;
import game.model.ghostStates.FrightenedMode;

public class UIPanel extends JPanel implements Observer {
	public static int width;
	public static int height;

	private int score = 0;
	private JLabel scoreLabel;

	private JButton restartButton;

	private GameplayPanel gameplayPanel;

	public UIPanel() {
	}

	public void setGameplayPanel(GameplayPanel gameplayPanel) {
		this.gameplayPanel = gameplayPanel;
	}

	// Hàm khởi tạo của UIPanel
	public UIPanel(int width, int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		scoreLabel = new JLabel("Score: " + score);
		scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
		scoreLabel.setForeground(Color.white);
		scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Nút chơi lại
		restartButton = new JButton("Chơi lại");
		restartButton.setPreferredSize(new Dimension(100, 50));
		restartButton.setBounds(150, 100, 100, 50);
		restartButton.setVisible(false);

		// Đặt layout của panel thành BorderLayout
		this.setLayout(new BorderLayout());

		// Thêm label điểm số vào panel ở vị trí phía BẮC
		this.add(scoreLabel, BorderLayout.CENTER);

		// Thêm nút chơi lại vào panel ở vị trí phía NAM
		this.add(restartButton, BorderLayout.SOUTH);

		restartButton.addActionListener(new ActionListener() {
			@Override
			 public void actionPerformed(ActionEvent e) {
		        // Tạo một luồng mới để chạy lại phần khởi động của ứng dụng
		        Thread restartThread = new Thread(() -> {
		            try {
		                // Tạm dừng luồng một chút để đảm bảo rằng các tác vụ hiện tại đã hoàn thành trước khi khởi động lại
		                Thread.sleep(100);
		            } catch (InterruptedException ex) {
		                ex.printStackTrace();
		            }

		            // Gọi phương thức main của GameLauncher để khởi chạy màn hình GameLauncher
		            GameLauncher.restartGame();
		        });
		        
		        // Bắt đầu chạy luồng mới
		        restartThread.start();
		    }
		});
	}

	// Phương thức cập nhật điểm số
	public void updateScore(int incrScore) {
		this.score += incrScore; // Tăng điểm số thêm một lượng incrScore
		this.scoreLabel.setText("Score: " + score); // Cập nhật label điểm số
	}

	// Phương thức trả về điểm số hiện tại
	public int getScore() {
		return score;
	}

	// Phương thức cập nhật điểm số khi Pacman ăn viên gạch PacGum
	@Override
	public void updatePacGumEaten(PacGum pg) {
		updateScore(10); // Cập nhật điểm số thêm 10 điểm
	}

	// Phương thức cập nhật điểm số khi Pacman ăn viên gạch đặc biệt SuperPacGum
	@Override
	public void updateSuperPacGumEaten(SuperPacGum spg) {
		updateScore(100); // Cập nhật điểm số thêm 100 điểm
	}

	// Phương thức cập nhật điểm số khi Pacman va chạm với một con ma
	@Override
	public void updateGhostCollision(Ghost gh) {
		if (gh.getState() instanceof FrightenedMode) { // Trong trường hợp Pacman va chạm với một con ma và con ma đang
														// trong trạng thái sợ hãi
			updateScore(500); // Cập nhật điểm số thêm 500 điểm
		}
	}

	// Phương thức hiển thị nút chơi lại
	public void showRestartButton() {
		this.restartButton.setVisible(true); // Hiển thị nút chơi lại
	}
}
