package game.model.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import game.controller.GameplayPanel;

public abstract class MovingEntity extends Entity {
	protected int spd; // Tốc độ của thực thể
	protected int xSpd = 0; // Tốc độ di chuyển theo trục x
	protected int ySpd = 0; // Tốc độ di chuyển theo trục y
	protected BufferedImage sprite; // Hình ảnh đại diện cho thực thể
	protected float subimage = 0; // Điểm bắt đầu của hình ảnh được hiển thị
	protected int nbSubimagesPerCycle; // Số lượng hình ảnh trong một chu kỳ hoạt ảnh
	protected int direction = 0; // Hướng di chuyển của thực thể
	protected float imageSpd = 0.2f; // Tốc độ chuyển đổi hình ảnh

	public MovingEntity(int size, int xPos, int yPos, int spd, String spriteName, int nbSubimagesPerCycle,
			float imageSpd) {
		super(size, xPos, yPos);
		this.spd = spd;
		try {
			// Đọc hình ảnh từ tệp và gán vào biến sprite
			this.sprite = ImageIO.read(getClass().getClassLoader().getResource("resources/img/" + spriteName));
			this.nbSubimagesPerCycle = nbSubimagesPerCycle; // Số lượng hình ảnh trong một chu kỳ hoạt ảnh
			this.imageSpd = imageSpd; // Tốc độ chuyển đổi hình ảnh
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		updatePosition(); // Cập nhật vị trí của thực thể
	}

	public void updatePosition() {
		// Cập nhật vị trí của thực thể dựa trên tốc độ di chuyển
		if (!(xSpd == 0 && ySpd == 0)) { // Nếu tốc độ di chuyển không bằng 0
			xPos += xSpd; // Di chuyển theo trục x
			yPos += ySpd; // Di chuyển theo trục y

			// Xác định hướng di chuyển của thực thể
			if (xSpd > 0) {
				direction = 0; // Phải
			} else if (xSpd < 0) {
				direction = 1; // Trái
			} else if (ySpd < 0) {
				direction = 2; // Lên
			} else if (ySpd > 0) {
				direction = 3; // Xuống
			}

			// Cập nhật hình ảnh được hiển thị dựa trên tốc độ chuyển đổi hình ảnh
			subimage += imageSpd;
			if (subimage >= nbSubimagesPerCycle) {
				subimage = 0; // Quay lại điểm bắt đầu nếu đã hiển thị hết tất cả các hình ảnh trong chu kỳ
								// hoạt ảnh
			}
		}

		// Xử lý trường hợp thực thể ra khỏi biên của vùng chơi
		if (xPos > GameplayPanel.width) {
			xPos = 0 - size + spd; // Nếu vượt quá biên phải, đặt vị trí về phía bên trái vùng chơi
		}

		if (xPos < 0 - size + spd) {
			xPos = GameplayPanel.width; // Nếu vượt quá biên trái, đặt vị trí về phía bên phải vùng chơi
		}

		if (yPos > GameplayPanel.height) {
			yPos = 0 - size + spd; // Nếu vượt quá biên dưới, đặt vị trí về phía trên vùng chơi
		}

		if (yPos < 0 - size + spd) {
			yPos = GameplayPanel.height; // Nếu vượt quá biên trên, đặt vị trí về phía dưới vùng chơi
		}
	}

	// Vẽ thực thể lên màn hình
	@Override
	public void render(Graphics2D g) {
		// Vẽ phần hình ảnh tương ứng với hướng di chuyển và frame hiện tại của chu kỳ
		// hoạt ảnh
		g.drawImage(sprite.getSubimage((int) subimage * size + direction * size * nbSubimagesPerCycle, 0, size, size),
				this.xPos, this.yPos, null);
	}

	// Kiểm tra xem thực thể có nằm trên ô lưới hay không
	public boolean onTheGrid() {
		return (xPos % 8 == 0 && yPos % 8 == 0); // Trả về true nếu vị trí của thực thể là bội số của 8
	}

	// Kiểm tra xem thực thể có nằm trong vùng chơi hay không
	public boolean onGameplayWindow() {
//        return !(xPos <= 0 || xPos >= GameplayPanel.width || yPos <= 0 || yPos >= GameplayPanel.height);
		return true;
	}

	// Trả về hitbox của thực thể
	public Rectangle getHitbox() {
		return new Rectangle(xPos, yPos, size, size);
	}

	// Cài đặt hình ảnh mới cho thực thể
	public void setSprite(BufferedImage sprite) {
		this.sprite = sprite;
	}

	// Cài đặt hình ảnh từ tên tệp
	public void setSprite(String spriteName) {
		try {
			this.sprite = ImageIO.read(getClass().getClassLoader().getResource("resources/img/" + spriteName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getSpd() {
		return spd;
	}

	public void setSpd(int spd) {
		this.spd = spd;
	}

	public int getxSpd() {
		return xSpd;
	}

	public void setxSpd(int xSpd) {
		this.xSpd = xSpd;
	}

	public int getySpd() {
		return ySpd;
	}

	public void setySpd(int ySpd) {
		this.ySpd = ySpd;
	}

	public float getSubimage() {
		return subimage;
	}

	public void setSubimage(float subimage) {
		this.subimage = subimage;
	}

	public int getNbSubimagesPerCycle() {
		return nbSubimagesPerCycle;
	}

	public void setNbSubimagesPerCycle(int nbSubimagesPerCycle) {
		this.nbSubimagesPerCycle = nbSubimagesPerCycle;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public float getImageSpd() {
		return imageSpd;
	}

	public void setImageSpd(float imageSpd) {
		this.imageSpd = imageSpd;
	}

	public BufferedImage getSprite() {
		return sprite;
	}


}
