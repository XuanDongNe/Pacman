package game.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class LevelSelectionPanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;

    public LevelSelectionPanel(ActionListener levelSelectListener) {
        setLayout(new GridLayout(3, 1));

        easyButton = new JButton("Easy");
        easyButton.addActionListener(levelSelectListener);

        mediumButton = new JButton("Medium");
        mediumButton.addActionListener(levelSelectListener);

        hardButton = new JButton("Hard");
        hardButton.addActionListener(levelSelectListener);

        add(easyButton);
        add(mediumButton);
        add(hardButton);
    }
}

