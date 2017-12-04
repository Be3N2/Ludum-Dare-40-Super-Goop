package Center;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import Entity.Player;
import Input.Keyboard;
import Input.Mouse;
import Level.Level;
import Screen.Screen;

public class Start {

	public static class Game extends Canvas implements Runnable {
		private static final long serialVersionUID = 1L;

		public static final int width = 480;
		public static final int height = 270;
		public static final int scale = 2;

		private Thread thread;
		private JFrame frame;
		private Keyboard key;
		private Mouse mouse;
		private boolean running = false;

		private Screen screen;

		public static String title = "Ludum Dare 40!";

		private Player player;
		private Level level;

		private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

		public Game() {
			Dimension size = new Dimension(width * scale, height * scale);
			setPreferredSize(size);

			screen = new Screen(width, height);
			frame = new JFrame();
			key = new Keyboard();
			addKeyListener(key);

			mouse = new Mouse();
			addMouseListener(mouse);
			addMouseMotionListener(mouse);

			player = new Player();
			level = new Level(width, height);
			level.startLevel(player, 2);
		}

		public synchronized void start() {
			running = true;
			thread = new Thread(this, "Thing Displayed");
			thread.start();
		}

		public synchronized void stop() {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void run() {
			long lastTime = System.nanoTime();
			long timer = System.currentTimeMillis();
			final double ns = 1000000000.0 / 60.0;
			double delta = 0;
			int frames = 0;
			int updates = 0;
			requestFocus();
			while (running) {
				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				while (delta >= 1) {
					update();
					updates++;
					delta--;
				}
				render();
				frames++;

				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					// System.out.println(updates + "ups," + frames + "fps" );
					frame.setTitle(title + "  |  " + updates + "ups," + frames + "fps");
					updates = 0;
					frames = 0;
				}
			}
			stop();
		}

		public void update() {

			key.update();

			screen.clear();

			player.update(key, level);
			// pass player position as offset for the level (its centered on the
			// player
			level.update((int) player.pos.x, (int) player.pos.y);

			if (key.esc) {
				System.exit(0);
			}
		}

		public void render() {
			BufferStrategy bs = getBufferStrategy();
			if (bs == null) {
				createBufferStrategy(3);
				return;
			}

			level.render(screen);
			player.render(screen, level);

			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}

			Graphics g = bs.getDrawGraphics();
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.dispose();
			bs.show();
		}

		public static void main(String[] args) {

			Game game = new Game();
			game.frame.setResizable(true);
			// game.frame.setUndecorated(true);
			game.frame.setTitle(Game.title);
			game.frame.add(game);
			game.frame.pack();
			game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.frame.setLocationRelativeTo(null);
			game.frame.setVisible(true);

			game.start();
		}

	}
}
