package kaleb.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.util.Random;

import kaleb.game.entity.Enemy;
import kaleb.game.entity.ID;
import kaleb.game.entity.LateralEnemyLeftToRight;
import kaleb.game.entity.LateralEnemyRightToLeft;
import kaleb.game.entity.Player;
import kaleb.game.screen.Screen;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private final int WIDTH = 640, HEIGHT = 480;
	private final String TITLE = "Game";
	private boolean running = false;
	private Thread thread;
	private Handler handler;
	private int timeSurvived = 0;
	public String timeDisplay;
	private Rectangle startButton;
	Random rand = new Random();
	public int chance;
	private int enemyNum;
	private int gameState;

	// makes sure the first spawn doesn't happen 60 times
	private boolean spawned = false;

	public Game() {
		chance = 1;
		enemyNum = 1;
		handler = new Handler();
		this.addKeyListener(new Keyboard(handler));
		this.addMouseListener(new Mouse(handler));
		new Screen(WIDTH, HEIGHT, TITLE, this);
		timeDisplay = new String("You have survived for " + timeSurvived / 60 + ".");
		requestFocus();

		// if gamestate == 2 the player is playing the game
		// gamestate 1 is the title screen
		setGameState(1);

		if (getGameState() == 1) {
			startButton = new Rectangle(this.getWidth() / 2 - 50, this.getHeight() / 2 - 25, 100, 20);
		}

	}

	public synchronized void start() {
		thread = new Thread(this);
		if (running)
			return;

		running = true;
		thread.start();

	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;

		// if (timeSurvived / 60 % 3 == 0)
		// g.setColor(Color.black);
		// else if (timeSurvived / 60 % 4 == 0)
		// g.setColor(Color.blue);
		// else {
		// g.setColor(Color.CYAN);
		// }

		g.fillRect(0, 0, WIDTH, HEIGHT);

		if (getGameState() == 1) {
			g.setColor(Color.RED);
			g2d.fill(startButton);
			g.setColor(Color.CYAN);
			g.drawString("Start", this.getWidth() / 2 - 20, this.getHeight() / 2 - 20);

		}
		if (getGameState() == 2) {
			handler.render(g);
		}
		g.setColor(Color.RED);
		if (getGameState() == 2)
			g.drawString(timeDisplay, 10, 10);

		g.dispose();
		bs.show();

	}

	private void tick() {
		if (getGameState() == 2 && spawned == false) {
			handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, ID.Player));
			handler.addObject(new Enemy(20, 20, ID.Enemy));
			handler.addObject(new Enemy(this.getWidth() - 30, this.getHeight() - 30, ID.Enemy2));
			spawned = true;
		}

		if (getGameState() == 2) {
			handler.tick();
			timeSurvived++;
			timeDisplay = new String("You have survived for " + timeSurvived / 60 + ".");
			if ((timeSurvived / 60) % 10 == 0) {
				if ((timeSurvived / 60) == 10 && (enemyNum / 60) == 1) {
					enemyNum++;
					handler.addObject(new Enemy(20, 20, ID.Enemy));
				}
				if ((timeSurvived / 60) == 20 && (enemyNum / 60) == 2) {
					handler.addObject(new Enemy(20, 20, ID.Enemy));
				}
				if ((timeSurvived / 60) == 30 && (enemyNum / 60) == 3) {
					handler.addObject(new Enemy(20, 20, ID.Enemy));
				}
				enemyNum++;
			}
			// every 3 seconds it roles a dice (represented by chance)
			// if chance == 81 it calls latAttack()
			if ((timeSurvived / 60) % 3 == 0 && timeSurvived / 60 != 0) {
				chance = rand.nextInt(100);
				if (chance != 0) {
					if (chance == 81) {
						chance = 1;
						latAttack();
					}
				}
			}
		}

		if (handler.getClickLocation() != null) {
			if (startButton.contains(handler.getClickLocation())) {
				handler.clickLocation = new Point(10000, 0);
				startButton = new Rectangle(20000, 0);
				setGameState(2);

			}
		}

	}

	public void latAttack() {
		handler.addObject(new LateralEnemyRightToLeft(640, 240, ID.LatEnemyRL));
		handler.addObject(new LateralEnemyRightToLeft(680, (240 / 3), ID.LatEnemyRL));
		handler.addObject(new LateralEnemyRightToLeft(700, 400, ID.LatEnemyRL));
		handler.addObject(new LateralEnemyLeftToRight(0, this.getHeight() / 2, ID.LatEnemyLR));
		handler.addObject(new LateralEnemyLeftToRight(-50, (240 / 3), ID.LatEnemyLR));
		handler.addObject(new LateralEnemyLeftToRight(-35, 400, ID.LatEnemyLR));
	}

	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	public static void main(String[] args) {
		new Game();
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

}
