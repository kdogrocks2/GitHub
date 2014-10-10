package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import kaleb.game.Game;
import kaleb.game.Handler;

public class Button extends GameObject {

	String label;
	Handler handler;
	Game game;

	public Button(int x, int y, String label, Handler handler, Game game, ID id) {

		super(x, y, id);
		this.game = game;
		this.handler = handler;
		this.label = label;
		boundingBox = new Rectangle(x, y, 100, 20);
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (game.getGameState() == 1) {
			g.setColor(Color.red);
			g.fillRect(x, y, 100, 20);
			g.setColor(Color.red);
			g2d.fill(boundingBox);
			g.setColor(Color.cyan);
			g.drawString(label, x + 50, y + 10);
		}

	}

	public void tick() {

		boundingBox = new Rectangle(x, y, 100, 20);
		if (game.getGameState() == 1) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);

				if (tempObject.getId() == ID.Button) {
					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							System.out.println("Starting...");
							System.out.println("Starting..");
							handler.setClickLocation(100000,0);
							handler.setGameState(2);
						}
				}
			}
		}

	}
}
