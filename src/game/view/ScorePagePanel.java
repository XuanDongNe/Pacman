package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class ScorePagePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ScorePagePanel() {
		setLayout(new BorderLayout());
		setBackground(Color.BLACK);

		JLabel titleLabel = new JLabel("High Scores");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
		titleLabel.setForeground(Color.WHITE);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(titleLabel, BorderLayout.NORTH);

		// Thêm danh sách điểm số vào đây, có thể là một JList hoặc một JTextArea
		JTextArea scoreListArea = new JTextArea();
		scoreListArea.setEditable(false);
		scoreListArea.setForeground(Color.WHITE);
		scoreListArea.setBackground(Color.BLACK);
		// Gán nội dung điểm số vào scoreListArea ở đây

		JScrollPane scrollPane = new JScrollPane(scoreListArea);
		add(scrollPane, BorderLayout.CENTER);
	}
}
