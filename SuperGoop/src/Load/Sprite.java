package Load;

public class Sprite {

	public final int WIDTH, HEIGHT;
	public int[] pixels;

	public static Sprite playerStand = new Sprite(16, 32, 16, 1, 0, LoadSheet.charSheet);

	public static Sprite playerWalk0 = new Sprite(16, 32, 16, 0, 2, LoadSheet.charSheet);
	public static Sprite playerWalk1 = new Sprite(16, 32, 16, 1, 2, LoadSheet.charSheet);
	public static Sprite playerWalk2 = new Sprite(16, 32, 16, 2, 2, LoadSheet.charSheet);
	public static Sprite playerWalk3 = new Sprite(16, 32, 16, 3, 2, LoadSheet.charSheet);
	public static Sprite playerWalk4 = new Sprite(16, 32, 16, 4, 2, LoadSheet.charSheet);
	public static Sprite playerWalk5 = new Sprite(16, 32, 16, 5, 2, LoadSheet.charSheet);

	public static Sprite playerStandg1 = new Sprite(16, 32, 16, 2, 0, LoadSheet.charSheet);
	public static Sprite playerStandg2 = new Sprite(16, 32, 16, 3, 0, LoadSheet.charSheet);
	public static Sprite playerStandg3 = new Sprite(16, 32, 16, 4, 0, LoadSheet.charSheet);
	public static Sprite playerStandg4 = new Sprite(16, 32, 16, 5, 0, LoadSheet.charSheet);

	public static Sprite playerStandDefeat = new Sprite(32, 32, 16, 6, 2, LoadSheet.charSheet);

	public static Sprite playerWalk0g1 = new Sprite(16, 32, 16, 0, 4, LoadSheet.charSheet);
	public static Sprite playerWalk1g1 = new Sprite(16, 32, 16, 1, 4, LoadSheet.charSheet);
	public static Sprite playerWalk2g1 = new Sprite(16, 32, 16, 2, 4, LoadSheet.charSheet);
	public static Sprite playerWalk3g1 = new Sprite(16, 32, 16, 3, 4, LoadSheet.charSheet);
	public static Sprite playerWalk4g1 = new Sprite(16, 32, 16, 4, 4, LoadSheet.charSheet);
	public static Sprite playerWalk5g1 = new Sprite(16, 32, 16, 5, 4, LoadSheet.charSheet);

	public static Sprite playerWalk0g2 = new Sprite(16, 32, 16, 0, 6, LoadSheet.charSheet);
	public static Sprite playerWalk1g2 = new Sprite(16, 32, 16, 1, 6, LoadSheet.charSheet);
	public static Sprite playerWalk2g2 = new Sprite(16, 32, 16, 2, 6, LoadSheet.charSheet);
	public static Sprite playerWalk3g2 = new Sprite(16, 32, 16, 3, 6, LoadSheet.charSheet);
	public static Sprite playerWalk4g2 = new Sprite(16, 32, 16, 4, 6, LoadSheet.charSheet);
	public static Sprite playerWalk5g2 = new Sprite(16, 32, 16, 5, 6, LoadSheet.charSheet);

	public static Sprite playerWalk0g3 = new Sprite(16, 32, 16, 0, 8, LoadSheet.charSheet);
	public static Sprite playerWalk1g3 = new Sprite(16, 32, 16, 1, 8, LoadSheet.charSheet);
	public static Sprite playerWalk2g3 = new Sprite(16, 32, 16, 2, 8, LoadSheet.charSheet);
	public static Sprite playerWalk3g3 = new Sprite(16, 32, 16, 3, 8, LoadSheet.charSheet);
	public static Sprite playerWalk4g3 = new Sprite(16, 32, 16, 4, 8, LoadSheet.charSheet);
	public static Sprite playerWalk5g3 = new Sprite(16, 32, 16, 5, 8, LoadSheet.charSheet);

	public static Sprite playerWalk0g4 = new Sprite(16, 32, 16, 8, 4, LoadSheet.charSheet);
	public static Sprite playerWalk1g4 = new Sprite(16, 32, 16, 9, 4, LoadSheet.charSheet);
	public static Sprite playerWalk2g4 = new Sprite(16, 32, 16, 10, 4, LoadSheet.charSheet);
	public static Sprite playerWalk3g4 = new Sprite(16, 32, 16, 11, 4, LoadSheet.charSheet);
	public static Sprite playerWalk4g4 = new Sprite(16, 32, 16, 12, 4, LoadSheet.charSheet);
	public static Sprite playerWalk5g4 = new Sprite(16, 32, 16, 13, 4, LoadSheet.charSheet);

	public static Sprite flag = new Sprite(16, 32, 16, 8, 8, LoadSheet.charSheet);
	public static Sprite nothing = new Sprite(16, 32, 16, 0, 20, LoadSheet.charSheet);
	public static Sprite victory = new Sprite(32, 64, 16, 10, 8, LoadSheet.charSheet);
	public static Sprite spikeDeath = new Sprite(32, 64, 16, 10, 12, LoadSheet.charSheet);

	public static Sprite goop = new Sprite(16, 16, 16, 0, 10, LoadSheet.charSheet);
	public static Sprite goopBlink = new Sprite(16, 16, 16, 1, 10, LoadSheet.charSheet);

	public static Sprite goopJump1 = new Sprite(16, 16, 16, 0, 11, LoadSheet.charSheet);
	public static Sprite goopJump2 = new Sprite(16, 16, 16, 1, 11, LoadSheet.charSheet);
	public static Sprite goopJump3 = new Sprite(16, 16, 16, 2, 11, LoadSheet.charSheet);
	public static Sprite goopJump4 = new Sprite(16, 16, 16, 3, 11, LoadSheet.charSheet);
	public static Sprite goopJump5 = new Sprite(16, 16, 16, 4, 11, LoadSheet.charSheet);
	public static Sprite goopJump6 = new Sprite(16, 16, 16, 5, 11, LoadSheet.charSheet);
	public static Sprite goopJump7 = new Sprite(16, 16, 16, 6, 11, LoadSheet.charSheet);
	public static Sprite goopJump8 = new Sprite(16, 16, 16, 7, 11, LoadSheet.charSheet);

	public Sprite(int w, int h, int offset, int x, int y, LoadSheet sheet) {
		this.WIDTH = w;
		this.HEIGHT = h;
		this.pixels = new int[WIDTH * HEIGHT];

		load(offset, x, y, sheet);
	}

	public Sprite(int[] pixels, int w, int h) {
		this.WIDTH = w;
		this.HEIGHT = h;

		this.pixels = pixels;
	}

	public void load(int offset, int x, int y, LoadSheet sheet) {

		for (int b = 0; b < HEIGHT; b++) {
			for (int a = 0; a < WIDTH; a++) {
				int aa = a + x * offset;
				int bb = b + y * offset;
				int col = sheet.pixels[aa + bb * sheet.WIDTH];
				if (col != 0xffcaa794)
					pixels[a + b * WIDTH] = col;
				else
					pixels[a + b * WIDTH] = 0xffff00ff;
			}
		}

	}

}
