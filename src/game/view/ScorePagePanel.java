package game.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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
	private JButton restartButton;

	public ScorePagePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.black);

        JLabel titleLabel = new JLabel("High Scores");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Thêm danh sách điểm số vào đây, có thể là một JList hoặc một JTextArea
        JTextArea scoreListArea = new JTextArea();
        scoreListArea.setEditable(false);
        scoreListArea.setForeground(Color.WHITE);
        scoreListArea.setBackground(Color.pink);
        // Gán nội dung điểm số vào scoreListArea ở đây
        
        restartButton = new JButton("Chơi lại");
//        restartButton.setVisible(false);
        this.add(restartButton, BorderLayout.CENTER);
        
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });

        JScrollPane scrollPane = new JScrollPane(scoreListArea);
        add(scrollPane, BorderLayout.CENTER);
    }
	
	
}
