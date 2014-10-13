package kaleb.game.screen;

import javax.swing.*;

import java.awt.*;

import kaleb.game.Game;

/**
 * Created by Kaleb on 9/17/2014.
 */
public class Screen extends Canvas {
	private static final long serialVersionUID = 1L;

	public Screen(int w, int h, String title, Game game) {
		JFrame frame = new JFrame(title);

		frame.setMaximumSize(new Dimension(w, h));
		frame.setPreferredSize(new Dimension(w, h));
		frame.setMinimumSize(new Dimension(w, h));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.add(game);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		game.start();

	}
}
