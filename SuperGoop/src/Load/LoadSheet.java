package Load;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadSheet {

	public final int WIDTH, HEIGHT;
	public int[] pixels;

	public static LoadSheet charSheet = new LoadSheet(512, 1024, "/CharSheet.png");

	public static LoadSheet map1 = new LoadSheet(32, 32, "/map1.png");
	public static LoadSheet map2 = new LoadSheet(128, 128, "/map2.png");
	public static LoadSheet tiles = new LoadSheet(256, 256, "/Tiles.png");
	public static LoadSheet map3 = new LoadSheet(128, 128, "/map3.png");
	public static LoadSheet map4 = new LoadSheet(128, 128, "/map4.png");
	public static LoadSheet map5 = new LoadSheet(128, 128, "/map5.png");
	public static LoadSheet map6 = new LoadSheet(128, 128, "/map6.png");

	public LoadSheet(int w, int h, String path) {
		this.WIDTH = w;
		this.HEIGHT = h;

		pixels = new int[WIDTH * HEIGHT];

		load(path);
	}

	public void load(String path) {
		try {
			BufferedImage image = ImageIO.read(LoadSheet.class.getResource(path));
			image.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
