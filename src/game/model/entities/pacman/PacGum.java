package game.model.entities.pacman;

import java.awt.Color;
import java.awt.Graphics2D;

import game.model.entities.StaticEntity;

//Class để tạo PacGum là các viên nhỏ mà Pacman cần ăn hết trong mê cung
//PacGum sẽ có 1 diện tích xung quanh thực thể cố định, 1 cái size cố định
public class PacGum extends StaticEntity {
  public PacGum(int xPos, int yPos) {
      super(4, xPos + 8, yPos + 8);
  }

  @Override
  public void render(Graphics2D g) {
      g.setColor(new Color(255, 183, 174));
      g.fillRect(xPos, yPos, size, size);
  }
}