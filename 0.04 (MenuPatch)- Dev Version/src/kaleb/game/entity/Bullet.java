package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import kaleb.game.Handler;

public class Bullet extends GameObject {

	public Bullet(int x, int y,Handler handler, ID id) {
		super(x, y,handler, id);
		boundingBox = new Rectangle(x, y, 5, 5);
		vely = 4;
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
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
