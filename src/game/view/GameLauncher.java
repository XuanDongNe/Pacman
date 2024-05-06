package game.view;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import game.controller.GameplayPanel;

public class GameLauncher {
    private static JFrame window;
    private static UIPanel uiPanel;

    public static void main(String[] args) {
        createGameWindow(); // Tạo cửa sổ trò chơi

        // Khởi tạo giao diện trò chơi và giao diện người dùng
        JPanel gameWindow = new JPanel();
        GameplayPanel gameplayPanel = createGameplayPanel();
        gameWindow.add(gameplayPanel);
        createUIPanel(gameWindow, gameplayPanel);

        // Hiển thị cửa sổ
        window.setContentPane(gameWindow);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    // Tạo cửa sổ trò chơi
    private static void createGameWindow() {
        window = new JFrame();
        window.setTitle("Pacman");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
    }

    // Tạo GameplayPanel
    private static GameplayPanel createGameplayPanel() {
        try {
            return new GameplayPanel(448, 496);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Tạo UIPanel và thiết lập gameplayPanel
    private static void createUIPanel(JPanel gameWindow, GameplayPanel gameplayPanel) {
        uiPanel = new UIPanel(256, 496);
        uiPanel.setGameplayPanel(gameplayPanel);
        gameWindow.add(uiPanel);
    }

    // Khởi chạy lại ứng dụng
    public static void restartGame() {
        window.dispose(); // Đóng cửa sổ hiện tại
        main(null); // Khởi chạy lại ứng dụng
    }

    // Phương thức để truy cập UIPanel từ các lớp khác
    public static UIPanel getUIPanel() {
        return uiPanel;
    }
}
