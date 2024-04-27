package game.model.entities;

import java.awt.Rectangle;

//Lớp trừu tượng để mô tả một thực thể không di chuyển
public abstract class StaticEntity extends Entity {

	protected Rectangle hitbox; // Hộp va chạm của thực thể

	public StaticEntity(int size, int xPos, int yPos) {
		super(size, xPos, yPos);
		this.hitbox = new Rectangle(xPos, yPos, size, size); // Hitbox chỉ được định nghĩa một lần khi tạo thực thể
	}

	// Phương thức trả về hộp va chạm
	public Rectangle getHitbox() {
		return hitbox;
	}
}
