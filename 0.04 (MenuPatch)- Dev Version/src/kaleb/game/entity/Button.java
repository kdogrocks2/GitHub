package kaleb.game.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import kaleb.game.Game;
import kaleb.game.Handler;

public class Button extends GameObject {

	private int insufficientFunds = 0;
	private boolean speedMaxed = false;
	private boolean enemySpeedMaxed = false;

	public Button(int x, int y, String label, Handler handler, Game game, ID id) {
		super(x, y, label, handler, game, id);

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

				if (tempObject.getId() == ID.ExitButton || tempObject.getId() == ID.SpeedUpgradeButton) {
					g.setColor(Color.red);
					g.fillRect(x, y, 100, 20);
					g.setColor(Color.cyan);
					g.drawString(label, x + 30, y + 10);
					if (speedMaxed) {
						g.drawString("You've maxed speed!", 350, 380);
					}

				}
				if (tempObject.getId() == ID.EnemySpeedDowngradeButton) {
					g.setColor(Color.red);
					g.fillRect(x, y, 100, 20);
					g.setColor(Color.cyan);
					g.drawString(label, x + 30, y + 10);
					if (enemySpeedMaxed) {
						g.drawString("You've pooped on the enemies speed!", 350, 400);
					}
				}
				
				switch(insufficientFunds){
				case 1: g.drawString("You need 2000 for that!", 320,400);
				break;
				case 2: g.drawString("You need 8000 for that!", 320,400);
				break;
				
				}

			}

			if (game.getGameState() == 4) {

				if (tempObject.getId() == ID.ExitButton) {
					g.setColor(Color.red);
					g.fillRect(x, y, 100, 20);
					g.setColor(Color.cyan);
					g.drawString(label, x + 50, y + 10);

					g.drawString("Welcome to my game!", 130, 20);
					g.drawString("Your score is determined by how many enemies you kill. ", 130, 40);
					g.drawString("The later on you go in the game, the more they are worth.", 130, 60);
					g.drawString("Press SPACE to shoot!", 130, 120);
					g.drawString("HAVE FUUN!1!", 130, 160);
				}
			}

		}
	}

	public void tick() {
		boundingBox = new Rectangle(x, y, 100, 20);
		if (game.getGameState() == 1 || game.getGameState() == 3 || game.getGameState() == 4) {
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
							// Add any button you want to show on the Shop
							// screen between these slashes
							// ///
							handler.addObject(new Button(80, 150, "+Speed!", handler, game, ID.SpeedUpgradeButton));
							handler.addObject(new Button(80, 180, "-EnemySpeed!", handler, game, ID.EnemySpeedDowngradeButton));

							// ///
							handler.addObject(new Button(10, 10, "Exit", handler, game, ID.ExitButton));
							gamestate = 3;

							handler.setClickLocation(100000, 0);
							handler.setGameState(gamestate);

							System.out.println(gamestate);
						}
				}

				// This button exits things and returns to menu 1
				if (tempObject.getId() == ID.ExitButton) {
					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							handler.object.clear();
							gamestate = 1;
							handler.addObject(new Button(game.getWidth() / 2 - 50, game.getHeight() / 2 - 25, "Start", handler, game, ID.StartButton));
							handler.addObject(new Button(game.getWidth() / 2 - 50, game.getHeight() / 2 + 35, "Shop", handler, game, ID.ShopButton));
							handler.addObject(new Button(game.getWidth() - 110, game.getHeight() - 20, "Help?", handler, game, ID.HelpButton));
							System.out.println(gamestate);
							handler.setClickLocation(100000, 0);
							handler.setGameState(gamestate);
						}
				}
				// Displays the help screen
				if (tempObject.getId() == ID.HelpButton) {
					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							handler.object.clear();
							handler.addObject(new Button(10, 10, "Exit", handler, game, ID.ExitButton));
							gamestate = 4;
							System.out.println(gamestate);
							handler.setClickLocation(100000, 0);
							handler.setGameState(gamestate);
						}
				}

				// Upgrades the players speed for 5000 points
				if (tempObject.getId() == ID.SpeedUpgradeButton) {

					// The price of a speed upgrade
					price = 2000;

					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							if (handler.speedUpgraded <= 3) {
								if (game.getScore() > price) {
									System.out.println(handler.speedUpgraded);
									handler.setClickLocation(100000, 0);
									System.out.println("Upgraded Speed!");
									game.setScore(game.getScore() - price);

									// Tells handler what level your speed is
									// Keyboard uses this
									handler.speedUpgraded++;

									// If you dont have enough money it tells
									// the player
								} else {
									if (tempObject.getBounds().contains(handler.getClickLocation())) {
										insufficientFunds = 1;
									}
								}

								// If you've maxed the speed it displays a
								// string to tell the player
							} else {
								if (tempObject.getBounds().contains(handler.getClickLocation())) {
									speedMaxed = true;
								}
							}
						}
				}

				if (tempObject.getId() == ID.EnemySpeedDowngradeButton) {

					// The price of a speed downgrade
					price = 8000;

					if (handler.getClickLocation() != null)
						if (tempObject.getBounds().contains(handler.getClickLocation())) {
							if (handler.speedDowngraded <= 2) {
								if (game.getScore() > price) {
									System.out.println(handler.speedDowngraded);
									handler.setClickLocation(100000, 0);
									System.out.println("Downgraded enemy speed");
									game.setScore(game.getScore() - price);

									// Tells handler what level your speed is
									// Keyboard uses this
									handler.speedDowngraded++;

									// If you dont have enough money it tells
									// the player
								} else {
									if (tempObject.getBounds().contains(handler.getClickLocation())) {
										insufficientFunds = 2;
									}
								}

								// If you've maxed the speed it displays a
								// string to tell the player
							} else {
								if (tempObject.getBounds().contains(handler.getClickLocation())) {
									enemySpeedMaxed = true;
								}
							}
						}
				}

			}
		}
	}
}
