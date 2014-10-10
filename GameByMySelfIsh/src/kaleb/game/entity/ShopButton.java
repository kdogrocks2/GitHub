package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import kaleb.game.Game;

public class ShopButton extends MenuButton {

	public ShopButton(int x, int y, String label, Game game, ID id) {
		super(x, y, label, game, id);

		button = new Rectangle(x, y, 100, 20);
	}

	public void tick() {
		if(handler.getOneTick() >= 1)
		if (handler.getClickLocation() != null)
			if (button.contains(handler.getClickLocation())) {
				System.out.println("hi");
			}
	}

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, 100, 20);
		g.setColor(Color.cyan);
		g.drawString(label, x + x / 2, y / 2);
	}

}
