package Level;

import java.util.ArrayList;

import Entity.Goop;
import Entity.Player;
import Load.Sprite;
import Math.Vector;
import Screen.Screen;

public class Level {

	// manages map you are on (the level);
	public Map map;
	private int width, height;
	public int offX, offY;

	public ArrayList<Goop> GoopList = new ArrayList<Goop>();
	public Vector objective;
	public Sprite objectiveSprite = Sprite.flag;

	private Vector goopAffect = new Vector(0, 10);

	public Level(int w, int h) {
		this.width = w;
		this.height = h;
	}

	public void startLevel(Player player, int levelNum) {
		map = new Map(levelNum);

		player.spawnPlayer(map.playerStart);

		GoopList.clear();

		for (int i = 0; i < map.SlimeSpawns.size(); i++) {
			Goop newGoop = new Goop(map.SlimeSpawns.get(i), goopAffect);
			GoopList.add(newGoop);
		}

		objectiveSprite = Sprite.flag;
		objective = map.objLoc;
	}

	public void update(int x, int y) {

		// offset of the level
		offX = x - ((width / 2) - 16); // player sprite width
		offY = y - ((height / 4) * 3);

		for (Goop goop : GoopList) {
			goop.update(map, x, y);
		}
	}

	public void render(Screen screen) {

		// calculate the position of every tile and render
		for (int y = -1; y < (height / 16) + 2; y++) { // 9
			for (int x = -1; x < (width / 16) + 1; x++) { // 30
				int posX = (offX / 16) + x;
				int posY = (offY / 16) + y;

				screen.renderTile(x * 16 - offX % 16, y * 16 - offY % 16, map.getTile(posX, posY));
			}
		}

		for (Goop goop : GoopList) {
			// if goop is on screen render it
			if (goop.pos.x + goop.sprite.WIDTH >= offX || goop.pos.x < offX + width) {
				if (goop.pos.y >= offY || goop.pos.y + goop.sprite.HEIGHT < offY + height) {
					goop.render(screen, offX, offY);
				}
			}
		}

		screen.renderSprite(objectiveSprite, (int) (objective.x * 16 - offX),
				(int) (objective.y * 16 - offY - (objectiveSprite.HEIGHT / 2)), 1);

	}

}
