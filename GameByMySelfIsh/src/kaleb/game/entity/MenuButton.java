package kaleb.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import kaleb.game.Game;
import kaleb.game.Handler;

public abstract class MenuButton {

	String label;
	Rectangle button;
	Handler handler;
	Game game;
	ID id;

	int x, y;

	MenuButton(int x, int y, String label, Game game, ID id) {
		this.label = label;
		this.x = x;
		this.y = y;
		this.id = id;

		button = new Rectangle(x, y, 100, 20);

	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
