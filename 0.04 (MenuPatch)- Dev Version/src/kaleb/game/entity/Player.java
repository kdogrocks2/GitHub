package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import kaleb.game.Handler;

public class Player extends GameObject {
	Handler handler;

	public Player(int x, int y,Handler handler, ID id) {
		super(x, y,handler, id);
		boundingBox = new Rectangle(x, y, 50, 50);

	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 50, 50);
		Graphics2D g2d = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g2d.draw(boundingBox);

	}

	public void tick() {
		
		
		x += velx;
		y += vely;
		
		
		boundingBox = new Rectangle(x, y, 50, 50);
	}
	
	

}
