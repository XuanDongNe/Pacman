package game.controller;

import java.awt.Graphics2D;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import game.model.Observer;
import game.model.entities.Entity;
import game.model.entities.Wall;
import game.model.entities.ghosts.Blinky;
import game.model.entities.ghosts.Ghost;
import game.model.entities.ghosts.GhostHouse;
import game.model.entities.pacman.PacGum;
import game.model.entities.pacman.Pacman;
import game.model.entities.pacman.SuperPacGum;
import game.model.ghostFactory.AbstractGhostFactory;
import game.model.ghostFactory.BlinkyFactory;
import game.model.ghostFactory.ClydeFactory;
import game.model.ghostFactory.InkyFactory;
import game.model.ghostFactory.PinkyFactory;
import game.model.ghostStates.EatenMode;
import game.model.ghostStates.FrightenedMode;
import game.model.utils.CollisionDetector;
import game.model.utils.CsvReader;
import game.model.utils.KeyHandler;
import game.view.GameLauncher;
import game.view.ScorePagePanel;
import game.view.UIPanel;

//Lớp quản lý trò chơi
public class Game implements Observer {
	// Danh sách các thực thể trên màn hình
	private List<Entity> objects = new ArrayList();
	private List<Ghost> ghosts = new ArrayList();
	private static List<Wall> walls = new ArrayList();

	private static Pacman pacman;
	private static Blinky blinky;

	private static boolean firstInput = false;

	// Phương thức khởi tạo của Game
	public Game() {
		// Khởi tạo trò chơi

		// Đọc dữ liệu từ tệp csv chứa mô tả của màn chơi
		List<List<String>> data = null;
		try {
			data = new CsvReader()
					.parseCsv(getClass().getClassLoader().getResource("resources/level/level2.csv").toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		int cellsPerRow = data.get(0).size();
		int cellsPerColumn = data.size();
		int cellSize = 8;

		CollisionDetector collisionDetector = new CollisionDetector(this);
		AbstractGhostFactory abstractGhostFactory = null;

		// Duyệt qua mỗi ô trong màn chơi và tạo các thực thể tương ứng
		for (int xx = 0; xx < cellsPerRow; xx++) {
			for (int yy = 0; yy < cellsPerColumn; yy++) {
				String dataChar = data.get(yy).get(xx);
				if (dataChar.equals("x")) { // Tạo các thành phần của bức tường
					objects.add(new Wall(xx * cellSize, yy * cellSize));
				} else if (dataChar.equals("P")) { // Tạo nhân vật Pacman
					pacman = new Pacman(xx * cellSize, yy * cellSize);
					pacman.setCollisionDetector(collisionDetector);

					// Đăng ký các Observer của Pacman
					pacman.registerObserver(GameLauncher.getUIPanel());
					pacman.registerObserver(this);
				} else if (dataChar.equals("b") || dataChar.equals("p") || dataChar.equals("i")
						|| dataChar.equals("c")) { // Tạo các con ma
					switch (dataChar) {
					case "b":
						abstractGhostFactory = new BlinkyFactory();
						break;
					case "p":
						abstractGhostFactory = new PinkyFactory();
						break;
					case "i":
						abstractGhostFactory = new InkyFactory();
						break;
					case "c":
						abstractGhostFactory = new ClydeFactory();
						break;
					}

					Ghost ghost = abstractGhostFactory.makeGhost(xx * cellSize, yy * cellSize);
					ghosts.add(ghost);
					if (dataChar.equals("b")) {
						blinky = (Blinky) ghost;
					}
				} else if (dataChar.equals(".")) { // Tạo viên gạch Pacman
					objects.add(new PacGum(xx * cellSize, yy * cellSize));
				} else if (dataChar.equals("o")) { // Tạo viên gạch đặc biệt Pacman
					objects.add(new SuperPacGum(xx * cellSize, yy * cellSize));
				} else if (dataChar.equals("-")) { // Tạo các thành phần của nhà ma
					objects.add(new GhostHouse(xx * cellSize, yy * cellSize));
				}
			}
		}
		objects.add(pacman);
		objects.addAll(ghosts);

		for (Entity o : objects) {
			if (o instanceof Wall) {
				walls.add((Wall) o);
			}
		}
	}

	// Phương thức trả về danh sách các thành phần tường
	public static List<Wall> getWalls() {
		return walls;
	}

	// Phương thức trả về danh sách các thực thể
	public List<Entity> getEntities() {
		return objects;
	}

	// Phương thức cập nhật trạng thái của tất cả các thực thể
	public void update() {
		for (Entity o : objects) {
			if (!o.isDestroyed())
				o.update();
		}
	}

	// Phương thức xử lý các input từ người chơi
	public void input(KeyHandler k) {
		pacman.input(k);
	}

	// Phương thức vẽ tất cả các thực thể lên màn hình
	public void render(Graphics2D g) {
		for (Entity o : objects) {
			if (!o.isDestroyed())
				o.render(g);
		}
	}

	// Phương thức trả về đối tượng Pacman
	public static Pacman getPacman() {
		return pacman;
	}

	// Phương thức trả về đối tượng Blinky
	public static Blinky getBlinky() {
		return blinky;
	}

	// Phương thức được gọi khi một viên gạch Pacman được ăn
	@Override
	public void updatePacGumEaten(PacGum pg) {
		pg.destroy(); // Viên gạch Pacman được phá hủy khi Pacman ăn nó
	}

	// Phương thức được gọi khi một viên gạch đặc biệt Pacman được ăn
	@Override
	public void updateSuperPacGumEaten(SuperPacGum spg) {
		spg.destroy(); // Viên gạch đặc biệt Pacman được phá hủy khi Pacman ăn nó
		for (Ghost gh : ghosts) {
			gh.getState().superPacGumEaten(); // Nếu có sự chuyển đổi đặc biệt khi một viên gạch đặc biệt Pacman được
												// ăn, trạng thái của các con ma sẽ thay đổi
		}
	}

	// Phương thức được gọi khi Pacman va chạm với một con ma
	@Override
	public void updateGhostCollision(Ghost gh) {
		if (gh.getState() instanceof FrightenedMode) {
			gh.getState().eaten(); // Nếu có sự chuyển đổi đặc biệt khi con ma bị ăn, trạng thái của con ma sẽ thay
									// đổi tương ứng
		} else if (!(gh.getState() instanceof EatenMode)) {
			// Khi Pacman va chạm với một con ma không trong trạng thái sợ hãi hoặc đã bị
			UIPanel uiPanel = GameLauncher.getUIPanel();
			uiPanel.showRestartButton();
			
			GameplayPanel gameplayPanel = new GameplayPanel();
			gameplayPanel.stop();
			gameplayPanel.run();
			System.out.println("Game over !\nScore : " + GameLauncher.getUIPanel().getScore());
		}
	}

	// Phương thức thiết lập trạng thái của việc nhận input lần đầu tiên
	public static void setFirstInput(boolean b) {
		firstInput = b;
	}

	// Phương thức trả về trạng thái của việc nhận input lần đầu tiên
	public static boolean getFirstInput() {
		return firstInput;
	}
}
