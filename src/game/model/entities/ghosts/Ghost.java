package game.model.entities.ghosts;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.controller.Game;
import game.model.entities.MovingEntity;
import game.model.ghostStates.ChaseMode;
import game.model.ghostStates.EatenMode;
import game.model.ghostStates.FrightenedMode;
import game.model.ghostStates.GhostState;
import game.model.ghostStates.HouseMode;
import game.model.ghostStates.ScatterMode;
import game.model.ghostStrategies.IGhostStrategy;

public abstract class Ghost extends MovingEntity {
	protected GhostState state;

	protected final GhostState chaseMode;
	protected final GhostState scatterMode;
	protected final GhostState frightenedMode;
	protected final GhostState eatenMode;
	protected final GhostState houseMode;

	protected int modeTimer = 0;
	protected int frightenedTimer = 0;
	protected boolean isChasing = false;

	protected static BufferedImage frightenedSprite1;
	protected static BufferedImage frightenedSprite2;
	protected static BufferedImage eatenSprite;

	protected IGhostStrategy strategy;

	/**
	 * Constructor cho lớp Ghost.
	 * 
	 * @param xPos       Tọa độ x của ma.
	 * @param yPos       Tọa độ y của ma.
	 * @param spriteName Tên của sprite cho ma.
	 */
	public Ghost(int xPos, int yPos, String spriteName) {
		super(32, xPos, yPos, 2, spriteName, 2, 0.1f);

		// Tạo các trạng thái khác nhau của ma
		chaseMode = new ChaseMode(this);
		scatterMode = new ScatterMode(this);
		frightenedMode = new FrightenedMode(this);
		eatenMode = new EatenMode(this);
		houseMode = new HouseMode(this);

		state = houseMode; // Trạng thái ban đầu

		try {
			frightenedSprite1 = ImageIO.read(getClass().getClassLoader().getResource("resources/img/ghost_frightened.png"));
			frightenedSprite2 = ImageIO.read(getClass().getClassLoader().getResource("resources/img/ghost_frightened_2.png"));
			eatenSprite = ImageIO.read(getClass().getClassLoader().getResource("resources/img/ghost_eaten.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Các phương thức cho các chuyển đổi giữa các trạng thái khác nhau

	/**
	 * Chuyển đổi trạng thái của ma sang chế độ đuổi.
	 */
	public void switchChaseMode() {
		state = chaseMode;
	}

	/**
	 * Chuyển đổi trạng thái của ma sang chế độ phân tán.
	 */
	public void switchScatterMode() {
		state = scatterMode;
	}

	/**
	 * Chuyển đổi trạng thái của ma sang chế độ sợ hãi.
	 */
	public void switchFrightenedMode() {
		frightenedTimer = 0;
		state = frightenedMode;
	}

	/**
	 * Chuyển đổi trạng thái của ma sang chế độ bị ăn.
	 */
	public void switchEatenMode() {
		state = eatenMode;
	}

	/**
	 * Chuyển đổi trạng thái của ma sang chế độ nhà.
	 */
	public void switchHouseMode() {
		state = houseMode;
	}

	/**
	 * Chuyển đổi trạng thái của ma giữa chế độ đuổi và chế độ phân tán dựa trên chế
	 * độ hiện tại.
	 */
	public void switchChaseModeOrScatterMode() {
		if (isChasing) {
			switchChaseMode();
		} else {
			switchScatterMode();
		}
	}

	public IGhostStrategy getStrategy() {
		return this.strategy;
	}

	public void setStrategy(IGhostStrategy strategy) {
		this.strategy = strategy;
	}

	public GhostState getState() {
		return state;
	}

	@Override
	public void update() {
		if (!Game.getFirstInput())
			return; // Ma không di chuyển cho đến khi người chơi di chuyển

		// Nếu ma đang trong trạng thái sợ hãi, một bộ đếm thời gian 7 giây bắt đầu, và
		// sau đó trạng thái sẽ được thông báo để áp dụng chuyển tiếp thích hợp
		if (state == frightenedMode) {
			frightenedTimer++;

			if (frightenedTimer >= (60 * 7)) {
				state.timerFrightenedModeOver();
			}
		}

		// Ma xen kẽ giữa chế độ đuổi và chế độ phân tán với một bộ đếm thời gian
		// Nếu ma đang ở chế độ đuổi hoặc chế độ phân tán, một bộ đếm thời gian bắt đầu,
		// và sau 5 giây hoặc 20 giây tùy thuộc vào chế độ, trạng thái sẽ được thông báo
		// để áp dụng chuyển tiếp thích hợp
		if (state == chaseMode || state == scatterMode) {
			modeTimer++;

			if ((isChasing && modeTimer >= (60 * 20)) || (!isChasing && modeTimer >= (60 * 5))) {
				state.timerModeOver();
				isChasing = !isChasing;
			}
		}

		// Nếu ma đang trên ô ngay phía trên nhà của mình, trạng thái sẽ được thông báo
		// để áp dụng chuyển tiếp thích hợp
		if (xPos == 208 && yPos == 168) {
			state.outsideHouse();
		}

		// Nếu ma đang trên ô giữa của nhà, trạng thái sẽ được thông báo để áp dụng
		// chuyển tiếp thích hợp
		if (xPos == 208 && yPos == 200) {
			state.insideHouse();
		}

		// Tùy thuộc vào trạng thái, ma tính toán hướng di chuyển tiếp theo của mình, và
		// sau đó vị trí của nó được cập nhật
		state.computeNextDir();
		updatePosition();
	}

	@Override
	public void render(Graphics2D g) {
		// Sử dụng các sprite khác nhau tùy thuộc vào trạng thái của ma
		if (state == frightenedMode) {
			if (frightenedTimer <= (60 * 5) || frightenedTimer % 20 > 10) {
				g.drawImage(frightenedSprite1.getSubimage((int) subimage * size, 0, size, size), this.xPos, this.yPos,
						null);
			} else {
				g.drawImage(frightenedSprite2.getSubimage((int) subimage * size, 0, size, size), this.xPos, this.yPos,
						null);
			}
		} else if (state == eatenMode) {
			g.drawImage(eatenSprite.getSubimage(direction * size, 0, size, size), this.xPos, this.yPos, null);
		} else {
			g.drawImage(
					sprite.getSubimage((int) subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size),
					this.xPos, this.yPos, null);
		}

	}
}