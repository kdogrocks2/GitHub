package kaleb.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	protected int x, y;
	protected int velx, vely;
	protected ID id;
	protected Rectangle boundingBox;
	
	public GameObject(int x,int y,ID id){
		this.id = id;
		this.x = x;
		this.y = y;
	}


	public abstract void render(Graphics g);

	public abstract void tick();

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVelx() {
		return velx;
	}

	public void setVelx(int velx) {
		this.velx = velx;
	}

	public int getVely() {
		return vely;
	}

	public void setVely(int vely) {
		this.vely = vely;
	}

	public ID getId() {
		return id;
	}

	public void setId(ID id) {
		this.id = id;
	}
	
	public Rectangle getBounds(){
		return boundingBox;
	}

}
