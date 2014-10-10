package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import kaleb.game.Game;
import kaleb.game.Handler;

public class Button extends GameObject {

	public Button(int x, int y, String label, Handler handler, int gamestate, Game game, ID id) {
		super(x, y, label, handler, gamestate, game, id);

		boundingBox = new Rectangle(x, y, 100, 20);

	}

	public void render(Graphics g) {
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (game.getGameState() == 1) {

				if (tempObject.getId() == ID.ShopButton || tempObject.getId() == ID.StartButton) {
					g.setColor(Color.red);
					g.fillRect(x, y, 100, 20);
					g.setColor(Color.cyan);
					g.drawString(label, x + 50, y + 10);
				}
			}

			if (game.getGameState() == 3) {
				
				if (tempObject.getId() == ID.ExitButton) {
					g.setColor(Color.red);
					g.fillRect(x, y, 100, 20);
					g.setColor(Color.cyan);
					g.drawString(label, x + 50, y + 10);
				}
			}

		}
	}

	public void tick() {
		boundingBox = new Rectangle(x, y, 100, 20);
		if (game.getGameState() == 1 || game.getGameState() == 3) {
			for (int i = 0; i < handler.object.size(); i++) {
				GameObject tempObject = handler.object.get(i);

				// Runs this if it's a start button
				if (tempObject.getId() == ID.StartButton) {
					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							gamestate = 2;
							handler.setClickLocation(100000, 0);
							handler.setGameState(gamestate);
						}
				}

				// This is the open shop button
				// Later on when i implement the shope WHILE the player is
				// playing
				// I will need to store temporary variables so that way i can
				// restart the game
				// Ater the player is done shopping without having to completely
				// actually restart the game
				// For example i should copy the linked list, then delete the
				// object linkedlist in Handler
				// on clicking the start button to play again, i should set the
				// Handler object list to my saved one
				// that way when the player starts they will be in the same
				// posistion and so will the enemies, also save the TimeSurvived
				// variable in Game
				if (tempObject.getId() == ID.ShopButton) {
					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							handler.object.clear();
							handler.addObject(new Button(10,10, "Exit", handler, 1, game, ID.ExitButton));
							gamestate = 3;

							handler.setClickLocation(100000, 0);
							handler.setGameState(gamestate);

							System.out.println(gamestate);
						}
				}

				// This button exits the shop
				if (tempObject.getId() == ID.ExitButton) {
					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							handler.object.clear();
							gamestate = 1;
							handler.addObject(new Button(game.getWidth() / 2 - 50, game.getHeight() / 2 - 25, "Start", handler, 2, game, ID.StartButton));
							handler.addObject(new Button(game.getWidth() / 2 - 50, game.getHeight() / 2 + 35, "Shop", handler, 3, game, ID.ShopButton));
							System.out.println(gamestate);
							handler.setClickLocation(100000, 0);
							handler.setGameState(gamestate);
						}
				}

			}
		}
	}

}
