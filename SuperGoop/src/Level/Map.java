package Level;

import java.util.ArrayList;

import Load.LoadSheet;
import Load.Tile;
import Math.Vector;

public class Map {

	public int width, height;// number of tiles/pixels
	public LoadSheet map = LoadSheet.map1;

	public Vector objLoc;

	public ArrayList<Vector> SlimeSpawns = new ArrayList<Vector>();
	public Vector playerStart;

	public int goopEffect = 1;

	public Map(int mapNum) {
		switch (mapNum) {
		case 1:
			map = LoadSheet.map1;
			goopEffect = 4;
			break;
		case 2:
			map = LoadSheet.map2;
			goopEffect = 4;
			break;
		case 3:
			map = LoadSheet.map3;
			goopEffect = 4;
			break;
		case 4:
			map = LoadSheet.map4;
			goopEffect = 4;
			break;
		case 5:
			map = LoadSheet.map5;
			goopEffect = 3;
			break;
		case 6:
			map = LoadSheet.map6;
			goopEffect = 3;
			break;
		default:
			map = LoadSheet.map6;
			goopEffect = 4;
			break;
		}

		width = map.WIDTH;
		height = map.HEIGHT;

		for (int y = 0; y < map.HEIGHT; y++) {
			for (int x = 0; x < map.WIDTH; x++) {
				if (map.pixels[x + y * map.WIDTH] == 0xffeb8931) { // player
																	// spawn
					playerStart = new Vector(x, y);
					// these colors get defaulted to nothing later so nothing to
					// worry about
				}
				if (map.pixels[x + y * map.WIDTH] == 0xffa46422) { // objective
					objLoc = new Vector(x, y);
				}
				if (map.pixels[x + y * map.WIDTH] == 0xfff60101) { // slime
																	// spawns
					Vector slimeSpawn = new Vector((float) x, (float) y);
					SlimeSpawns.add(slimeSpawn);
				}
			}
		}
	}

	public Tile getTile(int x, int y) {

		if (x < 0 || x >= width || y < 0 || y >= width)
			return Tile.nothing;

		int tileCol = map.pixels[x + y * map.WIDTH];
		switch (tileCol) {
		case 0xffe06f8b:
			return Tile.nothing;
		case 0xff000000:
			return Tile.grass0;
		case 0xffffffff:
			return Tile.grass1;
		case 0xff9d9d9d:
			return Tile.grass2;
		case 0xfff7e26b:
			return Tile.grass3;
		case 0xffa3ce27:
			return Tile.grass4;
		case 0xff2f484e:
			return Tile.grass5;
		case 0xff005784:
			return Tile.grass6;
		case 0xff1b2632:
			return Tile.grass7;
		case 0xff1b322a:
			return Tile.grass8;
		case 0xff55fffd:
			return Tile.grassCurve1;
		case 0xff5567ff:
			return Tile.grassCurve2;
		case 0xff55ff63:
			return Tile.grassCurve3;
		case 0xffde55ff:
			return Tile.grassCurve4;
		case 0xffb2dcef:
			return Tile.plat0;
		case 0xff31a2f2:
			return Tile.plat1;
		case 0xffbe2633:
			return Tile.plat2;
		case 0xff2a602e:
			return Tile.spike;
		case 0xff896b1a:
			return Tile.move1;
		case 0xff894a1a:
			return Tile.move2;
		case 0xff89291a:
			return Tile.jump1;
		case 0xff891a6d:
			return Tile.jump2;
		default:
			return Tile.nothing;
		}

	}

}
