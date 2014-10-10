package kaleb.game.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import kaleb.game.Game;
import kaleb.game.Handler;

public abstract class GameObject {
	protected int x, y;
	protected int velx, vely;
	protected ID id;
	protected Rectangle boundingBox;
	
	//This determines what gamestate the button goes to if you click on it
	//You don't have to use it, just set it to negative one
	protected int gamestate;
	protected String label;
	protected Handler handler;
	protected Game game;
	
	
	public GameObject(int x,int y,ID id){
		this.id = id;
		this.x = x;
		this.y = y;
		
	}
	
	
	//this is the constructor that buttons use, i didn't want to have to remake it every time i  have a new button but normal entities dont need this stuff
	public GameObject(int x, int y, String label, Handler handler,int gamestate, Game game, ID id){
		this.x = x;
		this.y = y;
		this.label = label;
		this.handler = handler;
		this.gamestate = gamestate;
		this.game = game;
		this.id = id;
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
