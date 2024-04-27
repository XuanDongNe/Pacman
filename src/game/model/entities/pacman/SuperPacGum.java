package game.model.entities.pacman;

import java.awt.Color;
import java.awt.Graphics2D;

import game.model.entities.StaticEntity;

//Class để tạo SuperPacGum là cục năng lượng bự, khi Pacman ăn thì sẽ được ăn lại mấy con ma
//Super Pacgum sẽ có 1 size và hitbox cố định
public class SuperPacGum extends StaticEntity {
	private int frameCount = 0; // điều chỉnh hiệu ứng nhấp nháy trên mà hình

	public SuperPacGum(int xPos, int yPos) {
		super(16, xPos, yPos); // size ban đầu là 16
	}

	@Override
	public void render(Graphics2D g) {
		// Làm cho SuperPacGum nhấp nháy, hiển thị SuperPacGum 30 khung hình trong 60
		// khung hình
		if (frameCount % 60 < 30) {
			g.setColor(new Color(255, 183, 174));
			g.fillOval(this.xPos, this.yPos, this.size, this.size);
		}
	}

	@Override
	public void update() {
		frameCount++;
	}
}
