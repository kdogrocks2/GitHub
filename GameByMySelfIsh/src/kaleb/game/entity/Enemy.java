package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import kaleb.game.Handler;

public class Enemy extends GameObject {
	Handler handler;

	public Enemy(int x, int y, ID id) {
		super(x, y, id);
		velx = 4;
		vely = 4;
		boundingBox = new Rectangle(x, y, 20, 20);

	}

	public void render(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, 20, 20);
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2d.draw(boundingBox);
	}

	public void tick() {
		x += velx;
		y += vely;
		if (x > 612 || x < 0)
			velx = -velx;

		if (y > 425 || y < 0)
			vely = -vely;
		boundingBox = new Rectangle(x, y, 20, 20);

	}

}
