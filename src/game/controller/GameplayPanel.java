package game.controller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import game.model.utils.KeyHandler;

//Bảng chơi của trò chơi
public class GameplayPanel extends JPanel implements Runnable {
	public static int width;
	public static int height;
	private Thread thread;
	private boolean running = false;

	private BufferedImage img;
	private Graphics2D g;
	private Image backgroundImage;

	private KeyHandler key;

	private Game game;

	public GameplayPanel(int width, int height) throws IOException {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		requestFocus();
//		FileInputStream fis = new FileInputStream("resources/img/background.png");
//		BufferedImage backgroundImage = ImageIO.read(fis);
		backgroundImage = ImageIO.read(getClass().getClassLoader().getResource("resources/img/background.png")); // Đọc hình nền
																										// từ tệp hình
																										// ảnh
	}

	@Override
	public void addNotify() {
		super.addNotify();

		if (thread == null) {
			thread = new Thread(this, "GameThread");
			thread.start();
		}
	}

	// Khởi tạo trò chơi
	public void init() {
		running = true;
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) img.getGraphics();

		key = new KeyHandler(this);

		game = new Game();
	}

	// Cập nhật trò chơi
	public void update() {
		game.update();
	}

	// Xử lý đầu vào
	public void input(KeyHandler key) {
		game.input(key);
	}

	// "Vẽ trò chơi"; chúng ta chuẩn bị những gì sẽ được hiển thị bằng cách vẽ trên
	// một "hình ảnh": một nền và các đối tượng của trò chơi trên đó
	public void render() {
		if (g != null) {
			g.drawImage(backgroundImage, 0, 0, width, height, null); // Vẽ hình nền
			game.render(g); // Vẽ các đối tượng của trò chơi
		}
	}

	// Hiển thị trò chơi: chúng ta hiển thị hình ảnh đã vẽ
	public void draw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(img, 0, 0, width, height, null); // Hiển thị hình ảnh
		g2.dispose();
	}

	@Override
	public void run() {
		init();

		// Để đảm bảo rằng trò chơi chạy ở 60 FPS
		final double GAME_HERTZ = 60.0;
		final double TBU = 1000000000 / GAME_HERTZ;

		final int MUBR = 5;

		double lastUpdateTime = System.nanoTime();
		double lastRenderTime;

		final double TARGET_FPS = 60.0;
		final double TTBR = 1000000000 / TARGET_FPS;

		int frameCount = 0;
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		int oldFrameCount = 0;

		while (running) {
			double now = System.nanoTime();
			int updateCount = 0;
			while ((now - lastUpdateTime) > TBU && (updateCount < MUBR)) {
				input(key);
				update();
				lastUpdateTime += TBU;
				updateCount++;
			}

			if (now - lastUpdateTime > TBU) {
				lastUpdateTime = now - TBU;
			}

			render();
			draw();
			lastRenderTime = now;
			frameCount++;

			int thisSecond = (int) (lastUpdateTime / 1000000000);
			if (thisSecond > lastSecondTime) {
				if (frameCount != oldFrameCount) {
					// System.out.println("FPS : " + frameCount);
					oldFrameCount = frameCount;
				}
				frameCount = 0;
				lastSecondTime = thisSecond;
			}

			while ((now - lastRenderTime < TTBR) && (now - lastUpdateTime < TBU)) {
				Thread.yield();

				try {
					Thread.sleep(1);
				} catch (Exception e) {
					System.err.println("ERROR yielding thread");
				}

				now = System.nanoTime();
			}
		}
	}
}
