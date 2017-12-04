package Screen;

import Load.Sprite;
import Load.Tile;

public class Screen {

	private final int WIDTH, HEIGHT;
	public int[] pixels;

	public Screen(int w, int h) {
		this.WIDTH = w;
		this.HEIGHT = h;

		this.pixels = new int[WIDTH * HEIGHT];
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			// pixels[i] = 0xff3c5156;
			pixels[i] = 0xff304247;
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		for (int y = 0; y < tile.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 - tile.WIDTH || xa >= WIDTH || ya < 0 || ya >= HEIGHT)
					break;
				if (xa < 0)
					xa = 0;
				int col = tile.pixels[x + y * tile.WIDTH];
				if (col != 0xffff00ff)
					pixels[xa + ya * WIDTH] = col;
			}
		}
	}

	public void renderSprite(Sprite sprite, int xp, int yp, int dir) {
		for (int y = 0; y < sprite.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 - sprite.WIDTH || xa >= WIDTH || ya < 0 || ya >= HEIGHT)
					break;
				if (xa < 0)
					xa = 0;

				int xflip = x;
				if (dir == -1)
					xflip = sprite.WIDTH - 1 - x;
				int col = sprite.pixels[xflip + y * sprite.WIDTH];
				if (col != 0xffff00ff)
					pixels[xa + ya * WIDTH] = col;
			}
		}
	}

	public void renderPlayer(Sprite sprite, int dir) {
		for (int y = 0; y < sprite.HEIGHT; y++) {
			int ya = y + ((HEIGHT / 4) * 3);
			for (int x = 0; x < sprite.WIDTH; x++) {
				int xa = x + (WIDTH / 2) - sprite.WIDTH;
				if (xa < 0 - sprite.WIDTH || xa >= WIDTH || ya < 0 || ya >= HEIGHT)
					break;
				if (xa < 0)
					xa = 0;

				int xflip = x;
				if (dir == -1)
					xflip = sprite.WIDTH - 1 - x;
				int col = sprite.pixels[xflip + y * sprite.WIDTH];
				if (col != 0xffff00ff)
					pixels[xa + ya * WIDTH] = col;
			}
		}
	}
}
