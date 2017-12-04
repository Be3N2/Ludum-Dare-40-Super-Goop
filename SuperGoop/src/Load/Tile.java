package Load;

public class Tile {

	public final int WIDTH, HEIGHT;
	public int[] pixels;

	public static Tile nothing = new Tile(16, 16, 16, 3, 0, LoadSheet.tiles);

	public static Tile grass0 = new Tile(16, 16, 16, 0, 0, LoadSheet.tiles);
	public static Tile grass1 = new Tile(16, 16, 16, 1, 0, LoadSheet.tiles);
	public static Tile grass2 = new Tile(16, 16, 16, 2, 0, LoadSheet.tiles);
	public static Tile grass3 = new Tile(16, 16, 16, 0, 1, LoadSheet.tiles);
	public static Tile grass4 = new Tile(16, 16, 16, 1, 1, LoadSheet.tiles);
	public static Tile grass5 = new Tile(16, 16, 16, 2, 1, LoadSheet.tiles);
	public static Tile grass6 = new Tile(16, 16, 16, 0, 2, LoadSheet.tiles);
	public static Tile grass7 = new Tile(16, 16, 16, 1, 2, LoadSheet.tiles);
	public static Tile grass8 = new Tile(16, 16, 16, 2, 2, LoadSheet.tiles);

	public static Tile grassCurve1 = new Tile(16, 16, 16, 3, 5, LoadSheet.tiles);
	public static Tile grassCurve2 = new Tile(16, 16, 16, 5, 5, LoadSheet.tiles);
	public static Tile grassCurve3 = new Tile(16, 16, 16, 7, 5, LoadSheet.tiles);
	public static Tile grassCurve4 = new Tile(16, 16, 16, 8, 5, LoadSheet.tiles);

	public static Tile plat0 = new Tile(16, 16, 16, 4, 0, LoadSheet.tiles);
	public static Tile plat1 = new Tile(16, 16, 16, 5, 0, LoadSheet.tiles);
	public static Tile plat2 = new Tile(16, 16, 16, 7, 0, LoadSheet.tiles);

	public static Tile spike = new Tile(16, 16, 16, 4, 2, LoadSheet.tiles);

	public static Tile move1 = new Tile(16, 16, 16, 2, 7, LoadSheet.tiles);
	public static Tile move2 = new Tile(16, 16, 16, 3, 7, LoadSheet.tiles);
	public static Tile jump1 = new Tile(16, 16, 16, 4, 7, LoadSheet.tiles);
	public static Tile jump2 = new Tile(16, 16, 16, 5, 7, LoadSheet.tiles);

	public Tile(int w, int h, int offset, int x, int y, LoadSheet sheet) {
		this.WIDTH = w;
		this.HEIGHT = h;
		this.pixels = new int[WIDTH * HEIGHT];

		load(offset, x, y, sheet);
	}

	public void load(int offset, int x, int y, LoadSheet sheet) {

		for (int b = 0; b < HEIGHT; b++) {
			for (int a = 0; a < WIDTH; a++) {
				int aa = a + x * offset;
				int bb = b + y * offset;
				int col = sheet.pixels[aa + bb * sheet.WIDTH];
				pixels[a + b * WIDTH] = col;
			}
		}

	}

}
