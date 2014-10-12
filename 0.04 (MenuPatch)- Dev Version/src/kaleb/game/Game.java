package kaleb.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;

import kaleb.game.entity.Button;
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
	Random rand = new Random();
	public int chance;
	public int score = 0;
	private BufferedReader reader;
	private int gameState;

	// makes sure the first spawn doesn't happen 60 times
	private boolean spawned = false;

	public Game() {
		chance = 1;
		handler = new Handler(this);
		this.addKeyListener(new Keyboard(handler));
		this.addMouseListener(new Mouse(handler));
		new Screen(WIDTH, HEIGHT, TITLE, this);
		timeDisplay = new String("You have survived for " + timeSurvived / 60 + ".");
		requestFocus();
		// if gamestate == 2 the player is playing the game
		// gamestate 1 is the title screen
		// 3 is shop
		// 4 is help
		setGameState(1);

		retrieveScore();

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

		g.fillRect(0, 0, WIDTH, HEIGHT);

		handler.render(g);
		g.setColor(Color.RED);
		if (getGameState() == 2)
			g.drawString(timeDisplay, 10, 10);
		g.drawString("Score: " + Integer.toString(score), this.getWidth() - 200, 10);

		g.dispose();
		bs.show();

	}

	private void tick() {
		if (getGameState() == 2 && isSpawned() == false) {
			handler.addObject(new Player(WIDTH / 2 - 32, HEIGHT / 2 - 32, handler, ID.Player));
			handler.addObject(new Enemy(20, 20, handler, ID.Enemy));
			handler.addObject(new Enemy(this.getWidth() - 30, this.getHeight() - 30, handler, ID.Enemy));
			setSpawned(true);
		}

		if (getGameState() == 2) {
			timeSurvived++;
			timeDisplay = new String("You have survived for " + timeSurvived / 60 + ".");
			if ((timeSurvived / 60) % 10 == 0 && timeSurvived / 60 != 0) {

				handler.addObject(new Enemy(20, 20, handler, ID.Enemy));

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
		handler.tick();
	}

	// /this is the code for the blue squares that go from left to right
	// Collision is handled in Handler and they are removed if they go off
	// screen to conserve resources
	public void latAttack() {
		handler.addObject(new LateralEnemyRightToLeft(640, 240, handler, ID.LatEnemyRL));
		handler.addObject(new LateralEnemyRightToLeft(680, (240 / 3), handler, ID.LatEnemyRL));
		handler.addObject(new LateralEnemyRightToLeft(700, 400, handler, ID.LatEnemyRL));
		handler.addObject(new LateralEnemyLeftToRight(0, this.getHeight() / 2, handler, ID.LatEnemyLR));
		handler.addObject(new LateralEnemyLeftToRight(-50, (240 / 3), handler, ID.LatEnemyLR));
		handler.addObject(new LateralEnemyLeftToRight(-35, 400, handler, ID.LatEnemyLR));
	}

	// This stores the current score in a txt file
	public void storeScore() throws IOException {
		// This code outputs a file to the directory that the jar is in
		// Should work on any computer but it might throw a security error
		// If i ever convert it into an APPLETT
		Writer output = null;

		// This changes score to a String so the writer can write it
		String text = Integer.toString(score);

		// this creates the file if it isn't made
		File file = new File("Score.txt");
		// this tells the writer what file to edit
		output = new BufferedWriter(new FileWriter(file));
		// This writes the things
		output.write(text);
		// this closes the file, it wont save if you don't have this
		output.close();

	}

	// This code sets the Score int equal to the one in the file'
	// Allows the player to leave the game and come back with the same
	// score.
	// FINNALLYYLLYLYLYLY
	private void retrieveScore() {

		try {
			reader = new BufferedReader(new FileReader("score.txt"));
			score = Integer.parseInt(reader.readLine());
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

		if (getGameState() == 1) {
			handler.addObject(new Button(this.getWidth() / 2 - 50, this.getHeight() / 2 - 25, "Start", handler, this, ID.StartButton));
			handler.addObject(new Button(this.getWidth() / 2 - 50, this.getHeight() / 2 + 35, "Shop", handler, this, ID.ShopButton));
			handler.addObject(new Button(this.getWidth() - 110, this.getHeight() - 20, "Help?", handler, this, ID.HelpButton));

		}
	}

	public void resetTimeSurvived() {
		timeSurvived = 0;
	}

	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		@SuppressWarnings("unused")
		int updates = 0;
		@SuppressWarnings("unused")
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
				// System.out.println("FPS: " + frames + " TICKS: " + updates);
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

	public boolean isSpawned() {
		return spawned;
	}

	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}

	public int getTimeSurvived() {
		return timeSurvived;
	}

	public void setTimeSurvived(int timeSurvived) {
		this.timeSurvived = timeSurvived;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}
}
