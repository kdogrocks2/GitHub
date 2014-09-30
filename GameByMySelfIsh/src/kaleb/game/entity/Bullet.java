package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Bullet extends GameObject {

	public Bullet(int x, int y, ID id) {
		super(x, y, id);
		boundingBox = new Rectangle(x, y, 5, 5);
		vely = 4;
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 5, 5);
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2d.draw(boundingBox);
	}

	public void tick() {
		y -= vely;
		boundingBox = new Rectangle(x, y, 5, 5);

	}

}
