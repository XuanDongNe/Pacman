package game.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameInterface extends JFrame {
    private JButton newGameButton; // Nút bắt đầu trò chơi mới
    private JButton levelButton; // Nút chọn cấp độ
    private JButton scoreButton; // Nút xem điểm
    private JButton quitButton; // Nút thoát trò chơi

    public GameInterface() {
        setTitle("Game Interface"); // Đặt tiêu đề cho cửa sổ
        setSize(600, 600); // Đặt kích thước cửa sổ
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Thiết lập hành động khi đóng cửa sổ
        setLayout(new GridBagLayout()); // Đặt layout cho cửa sổ là GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Tạo một đối tượng GridBagConstraints để điều chỉnh các thành phần trong cửa sổ
        gbc.fill = GridBagConstraints.HORIZONTAL; // Thiết lập fill cho GridBagConstraints là HORIZONTAL
        gbc.insets = new Insets(10, 10, 10, 10); // Padding giữa các thành phần

        // Tạo các nút bằng JButton
        newGameButton = new JButton("New Game"); // Nút bắt đầu trò chơi mới
        levelButton = new JButton("Level"); // Nút chọn cấp độ
        scoreButton = new JButton("Score"); // Nút xem điểm
        quitButton = new JButton("Quit"); // Nút thoát trò chơi

        // Đặt kích thước ưa thích cho các nút
        Dimension buttonSize = new Dimension(150, 50);
        newGameButton.setPreferredSize(buttonSize);
        levelButton.setPreferredSize(buttonSize);
        scoreButton.setPreferredSize(buttonSize);
        quitButton.setPreferredSize(buttonSize);

        // Thêm các ActionListener cho các nút
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Khởi chạy trò chơi mới bằng cách gọi main() của GameLauncher
                try {
                    GameLauncher.main(null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                // Đóng frame hiện tại của GameInterface
                dispose();
            }
        });

        levelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLevelSelection(); // Mở cửa sổ chọn cấp độ
            }

            private void openLevelSelection() {
                // Tạo cửa sổ chọn cấp độ
                JFrame levelSelectionFrame = new JFrame("Select Level");
                levelSelectionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng frame khi đóng cửa sổ
                levelSelectionFrame.setSize(300, 200); // Đặt kích thước cửa sổ
                levelSelectionFrame.setLocationRelativeTo(null); // Đặt vị trí cửa sổ vào giữa màn hình

                // Tạo panel chọn cấp độ
                LevelSelectionPanel levelSelectionPanel = new LevelSelectionPanel(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String level = e.getActionCommand();
                        System.out.println("Selected Level: " + level); // In ra console cấp độ đã chọn
                        levelSelectionFrame.dispose(); // Đóng cửa sổ chọn cấp độ
                    }
                });
                levelSelectionFrame.add(levelSelectionPanel); // Thêm panel vào cửa sổ chọn cấp độ

                levelSelectionFrame.setVisible(true); // Hiển thị cửa sổ chọn cấp độ
            }
        });

        scoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openScorePage(); // Mở cửa sổ xem điểm
            }

            private void openScorePage() {
                JFrame scoreFrame = new JFrame("Score Page");
                scoreFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Đóng frame khi đóng cửa sổ
                scoreFrame.setSize(400, 300); // Đặt kích thước cửa sổ
                scoreFrame.setLocationRelativeTo(null); // Đặt vị trí cửa sổ vào giữa màn hình

                // Tạo panel xem điểm
                ScorePagePanel scorePagePanel = new ScorePagePanel();
                scoreFrame.add(scorePagePanel); // Thêm panel vào cửa sổ xem điểm

                scoreFrame.setVisible(true); // Hiển thị cửa sổ xem điểm
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Thoát ứng dụng
            }
        });

        // Thêm các nút vào cửa sổ với GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(newGameButton, gbc); // Thêm nút bắt đầu trò chơi mới

        gbc.gridy = 1;
        add(levelButton, gbc); // Thêm nút chọn cấp độ

        gbc.gridy = 2;
        add(scoreButton, gbc); // Thêm nút xem điểm

        gbc.gridy = 3;
        add(quitButton, gbc); // Thêm nút thoát trò chơi

        setLocationRelativeTo(null); // Đặt vị trí của cửa sổ vào giữa màn hình
        setVisible(true); // Hiển thị cửa sổ
    }

    private void switchToUIPanel() {
        // Ẩn frame hiện tại của GameInterface
        setVisible(false);

        // Tạo và hiển thị UIPanel
        JFrame uiPanelFrame = new JFrame("UI Panel");
        uiPanelFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Đóng ứng dụng khi đóng cửa sổ
        uiPanelFrame.getContentPane().add(new UIPanel(600, 600)); // Giả định kích thước giống với GameInterface
        uiPanelFrame.pack(); // Điều chỉnh kích thước frame
        uiPanelFrame.setLocationRelativeTo(null); // Đặt vị trí của frame vào giữa màn hình
        uiPanelFrame.setVisible(true); // Hiển thị frame
    }

    public static void main(String[] args) {
        new GameInterface(); // Khởi chạy giao diện trò chơi
    }
}
