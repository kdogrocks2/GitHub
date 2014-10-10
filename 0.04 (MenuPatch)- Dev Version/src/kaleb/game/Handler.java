package kaleb.game;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;

import kaleb.game.entity.Button;
import kaleb.game.entity.GameObject;
import kaleb.game.entity.ID;
import kaleb.game.entity.Player;

public class Handler {

	Player player;
	Game game;
	Rectangle tempBoundsPlayer;
	Rectangle tempBoundsBullet;
	Rectangle tempBoundsBullet2;
	Point clickLocation;
	boolean shooting = false;

	public int oneTick = 0;

	public Handler(Game game) {
		this.game = game;
	}

	// should contain all objects in the game
	public LinkedList<GameObject> object = new LinkedList<GameObject>();

	// goes through each object and ticks them
	public void tick() {
		for (int i = 0; i < object.size(); i++) {

			GameObject tempObject = object.get(i);
			// q just makes sure the first time handler is called it doesn't run
			// this because you get a null pointer
			// This is a sloppy thing to do but i cant fix it so wtf i dont
			// care!
			if (oneTick == 1) {
				// This saves the players bounding box to a temporary
				// variable
				// every tick for use later
				// I tried to do this with the enemies but i would have had
				// to
				// create a tempBounds variable
				// for each one so this is better to do
				// THIS WILL FALL APART IF WE HAVE MULTIPLE PLAYERS!!!!
				if (tempObject.getId() == ID.Player) {
					tempBoundsPlayer = tempObject.getBounds();
				}

				if (tempObject.getId() == ID.Bullet) {
					// removes the bullet if it goes off the screen

					if (tempObject.getBounds().x > 640) {
						setShooting(false);
						removeObject(tempObject);
					}
					if (tempObject.getBounds().y < 0) {
						removeObject(tempObject);
						setShooting(false);
					}
					tempBoundsBullet = tempObject.getBounds();

					// Runs through the entire list of objects to check for
					// enemy collision with bullets
					// This solved the collision bug
					for (int q = 0; q < object.size(); q++) {
						GameObject tempEnemy = object.get(q);
						if (tempEnemy.getId() == ID.Enemy || tempEnemy.getId() == ID.Enemy2 || tempEnemy.getId() == ID.Enemy3 || tempEnemy.getId() == ID.Enemy4 || tempEnemy.getId() == ID.Enemy5) {

							if (tempEnemy.getBounds().intersects(tempBoundsBullet)) {
								removeObject(tempEnemy);

							}
						}
					}
				}
			}

			// removes latEnemies if they are off the screen, but only on
			// the opposite side of
			// their spawn point
			// That way we can spawn them off the screen

			if (tempObject.getId() == ID.LatEnemyRL) {
				if (tempObject.getX() < 0) {
					removeObject(tempObject);
				}
			}
			if (tempObject.getId() == ID.LatEnemyLR) {
				if (tempObject.getX() > 640) {
					removeObject(tempObject);
				}
			}

			// Checks if the Enemy's bounding box intersects the temp
			// bounding box we made earlier
			// runs every tick
			// if it returns true the player loses
			if (tempBoundsPlayer != null) {
				if (tempObject.getId() == ID.Enemy || tempObject.getId() == ID.Enemy2 || tempObject.getId() == ID.LatEnemyRL || tempObject.getId() == ID.LatEnemyLR) {
					if (tempObject.getBounds().intersects(tempBoundsPlayer)) {

						game.setGameState(1);
						object.clear();
						addObject(new Button(game.getWidth() / 2 - 50, game.getHeight() / 2, "Start", this, game, ID.Button));
						setShooting(false);
						game.setSpawned(false);
						// sets timeSurvived in Game == 0;
						game.resetTimeSurvived();
					}
				}
			}

			if (oneTick == 0)
				oneTick++;

			tempObject.tick();

		}
	}

	// goes through each object and renders them
	public void render(Graphics g) {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.render(g);
		}
	}

	public void addObject(GameObject object) {
		this.object.add(object);

	}

	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	// Returns the players bounding box
	// We can use this for collision
	// make sure this doesn't get called before oneTick > 0 or else you will get
	// a null pointer
	public Rectangle getTempBoundsPlayer() {
		return tempBoundsPlayer;
	}

	public Rectangle getTempBoundsBullet() {
		return tempBoundsBullet;
	}

	public Point getClickLocation() {
		return clickLocation;
	}

	public void setClickLocation(int x, int y) {
		clickLocation = new Point(x, y);
	}

	public void setGameState(int state) {
		game.setGameState(state);
	}

	public int getGameState() {
		return game.getGameState();
	}

	public boolean isShooting() {
		return shooting;
	}

	public void setShooting(boolean shooting) {
		this.shooting = shooting;
	}

	// Just a failsafe so handler never calls anything that is null
	// Just made it public so i don't have to make a oneTick everytime i have a
	// null pointer
	// use this if you are getting null pointers because of handler calling
	// things that aren't made yet
	public int getOneTick() {
		return oneTick;
	}

}
