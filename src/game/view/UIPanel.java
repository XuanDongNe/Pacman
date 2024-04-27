package game.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.model.Observer;
import game.model.entities.ghosts.Ghost;
import game.model.entities.pacman.PacGum;
import game.model.entities.pacman.SuperPacGum;
import game.model.ghostStates.FrightenedMode;

////Giao diện người dùng
//public class UIPanel extends JPanel implements Observer {
//	public static int width;
//	public static int height;
//
//	private int score = 0;
//	private JLabel scoreLabel;
//
//	public UIPanel(int width, int height) {
//		this.width = width;
//		this.height = height;
//		setPreferredSize(new Dimension(width, height));
//		this.setBackground(Color.black);
//		scoreLabel = new JLabel("Score: " + score); // Tạo một nhãn để hiển thị điểm
//		scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
//		scoreLabel.setForeground(Color.white);
//		this.add(scoreLabel, BorderLayout.WEST);
//	}
//
//	public void updateScore(int incrScore) {
//		this.score += incrScore; // Tăng điểm
//		this.scoreLabel.setText("Score: " + score); // Cập nhật nhãn điểm
//	}
//
//	public int getScore() {
//		return score;
//	}
//
//	// Giao diện được thông báo khi Pacman tiếp xúc với một PacGum, SuperPacGum hoặc
//	// ma, và cập nhật điểm hiển thị tương ứng
//	@Override
//	public void updatePacGumEaten(PacGum pg) {
//		updateScore(10); // Cập nhật điểm khi Pacman ăn một PacGum
//
//	}
//
//	@Override
//	public void updateSuperPacGumEaten(SuperPacGum spg) {
//		updateScore(100); // Cập nhật điểm khi Pacman ăn một SuperPacGum
//
//	}
//
//	@Override
//	public void updateGhostCollision(Ghost gh) {
//		if (gh.getState() instanceof FrightenedMode) { // Trong trường hợp Pacman va vào một ma, chỉ cập nhật điểm khi
//			// ma ở chế độ "sợ hãi"
//			updateScore(500); // Cập nhật điểm khi Pacman ăn một ma
//		}
//
//	}
//}



//Panneau de l'interface utilisateur
public class UIPanel extends JPanel implements Observer {
  public static int width;
  public static int height;

  private int score = 0;
  private JLabel scoreLabel;

  public UIPanel(int width, int height) {
      this.width = width;
      this.height = height;
      setPreferredSize(new Dimension(width, height));
      this.setBackground(Color.black);
      scoreLabel = new JLabel("Score: " + score);
      scoreLabel.setFont(scoreLabel.getFont().deriveFont(20.0F));
      scoreLabel.setForeground(Color.white);
   // Buttons

      JButton levelButton = new JButton("Level");
      JButton scoreButton = new JButton("Score");
      JButton quitButton = new JButton("Quit");

      // Button Action Listeners

      levelButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
          	 openLevelSelection();
          }

          private void openLevelSelection() {
              JFrame levelSelectionFrame = new JFrame("Select Level");
              levelSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
              levelSelectionFrame.setSize(300, 200);
              levelSelectionFrame.setLocationRelativeTo(null); 

            
              LevelSelectionPanel levelSelectionPanel = new LevelSelectionPanel(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                     
                      String level = e.getActionCommand();
                      System.out.println("Selected Level: " + level);
                      levelSelectionFrame.dispose(); 
                  }
              });
              levelSelectionFrame.add(levelSelectionPanel);

              levelSelectionFrame.setVisible(true);
          }
      });

      scoreButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
          	 openScorePage();
          }

			private void openScorePage() {
				JFrame scoreFrame = new JFrame("Score Page");
		        scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		        scoreFrame.setSize(400, 300);
		        scoreFrame.setLocationRelativeTo(null);

		        
		        ScorePagePanel scorePagePanel = new ScorePagePanel();
		        scoreFrame.add(scorePagePanel);

		        scoreFrame.setVisible(true);
			}
      });

      quitButton.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              
              System.exit(0);
          }
      });

     
      this.add(levelButton);
      this.add(scoreButton);
      this.add(quitButton);
      this.add(scoreLabel, BorderLayout.WEST);
  }

  public void updateScore(int incrScore) {
      this.score += incrScore;
      this.scoreLabel.setText("Score: " + score);
  }

  public int getScore() {
      return score;
  }

  //L'interface est notifiÃ©e lorsque Pacman est en contact avec une PacGum, une SuperPacGum ou un fantÃ´me, et on met Ã  jour le score affichÃ© en consÃ©quence
  @Override
  public void updatePacGumEaten(PacGum pg) {
      updateScore(10);
  }

  @Override
  public void updateSuperPacGumEaten(SuperPacGum spg) {
      updateScore(100);
  }

  @Override
  public void updateGhostCollision(Ghost gh) {
      if (gh.getState() instanceof FrightenedMode) { //Dans le cas oÃ¹ Pacman est en contact avec un fantÃ´me on ne met Ã  jour le score que lorsque ce dernier est en mode "frightened"
          updateScore(500);
      }
  }
}