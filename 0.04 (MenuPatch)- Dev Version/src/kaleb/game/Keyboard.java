package kaleb.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import kaleb.game.entity.Bullet;
import kaleb.game.entity.GameObject;
import kaleb.game.entity.ID;

public class Keyboard implements KeyListener {
	private Handler handler;
	
	public Keyboard(Handler handler) {
		this.handler = handler;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// Goes through all the GameObjects
		for (int i = 0; i < handler.object.size(); i++) {
			// sets tempObject to the GameObject at index i
			GameObject tempObject = handler.object.get(i);

			// only runs the key events if the id of i is Player
			if (tempObject.getId() == ID.Player) {
				if (key == KeyEvent.VK_W) {
					tempObject.setVely(-3);
				}
				if (key == KeyEvent.VK_D) {
					tempObject.setVelx(3);
				}
				if (key == KeyEvent.VK_S) {
					tempObject.setVely(3);
				}
				if (key == KeyEvent.VK_A) {
					tempObject.setVelx(-3);
				}
				if (key == KeyEvent.VK_SPACE) {

					
					if(handler.isShooting() == false){
					handler.addObject(new Bullet(tempObject.getX() + 25, tempObject.getY(), ID.Bullet));
					handler.setShooting(true); 
					}
				}
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		// does the same thing as above but sets the velocity to 0 instead
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);

			if (tempObject.getId() == ID.Player) {
				if (key == KeyEvent.VK_W) {
					tempObject.setVely(0);
				}
				if (key == KeyEvent.VK_D) {
					tempObject.setVelx(0);
				}
				if (key == KeyEvent.VK_S) {
					tempObject.setVely(0);
				}
				if (key == KeyEvent.VK_A) {
					tempObject.setVelx(0);
				}
			}
		}
	}

	public void keyTyped(KeyEvent e) {

	}

}
