package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import kaleb.game.Handler;

public class LateralEnemyRightToLeft extends GameObject {

	public LateralEnemyRightToLeft(int x, int y, Handler handler, ID id) {
		super(x, y, handler, id);
		boundingBox = new Rectangle(x, y, 15, 15);
		switch (handler.speedDowngraded) {
		case 0:
			velx = 5;
			break;
		case 1:
			velx = 4;
			break;
		case 2:
			velx = 3;
			break;
		case 3:
			velx = 2;
			break;

		}
	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 15, 15);
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2d.draw(boundingBox);
	}

	public void tick() {
		x -= velx;
		boundingBox = new Rectangle(x, y, 15, 15);
	}

}
