package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class LateralEnemyLeftToRight extends GameObject{

	public LateralEnemyLeftToRight(int x, int y, ID id) {
		super(x, y, id);
		boundingBox = new Rectangle(x, y, 15, 15);
		velx = 5;
	}

	public void render(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(x, y, 15, 15);
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2d.draw(boundingBox);
	}

	public void tick() {
		x += velx;
		boundingBox = new Rectangle(x, y, 15, 15);
	}
}
