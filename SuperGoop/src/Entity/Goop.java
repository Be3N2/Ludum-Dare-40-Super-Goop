package Entity;

import java.util.Random;

import Level.Map;
import Load.Sprite;
import Load.Tile;
import Math.Vector;
import Screen.Screen;

public class Goop {

	public Vector pos;

	private Vector vel;
	private Vector maxVel = new Vector(2, 5);

	private Vector accel;
	private Vector maxAccel = new Vector(2, 1);

	private Vector gravity = new Vector(0, 0.1f);

	public Sprite sprite = Sprite.goop;

	private boolean jump = false;
	private Vector jumpUp = new Vector(0, -3);
	private Vector jumpForce = new Vector(2, -3);
	private int jumpCounter = 0;
	private int landCounter = 0;
	private int jumpChance = 80;
	private int visionDist = 200; // in pixels

	private int dir = 1;

	private float friction = 0.8f;

	public Vector affect;

	Random rand = new Random();

	public Goop(Vector spawnPos, Vector applyAffect) {
		pos = spawnPos;
		pos.mult(16);
		vel = new Vector();
		accel = new Vector();
		affect = applyAffect;
	}

	public void update(Map map, int playerX, int playerY) {

		accel.add(gravity);
		accel.mult(friction);
		vel.mult(friction);
		jump(playerX, playerY);
		accel.limit(maxAccel);

		vel.add(accel);
		vel.limit(maxVel);

		collision(map);
		pos.add(vel);

		if (vel.x > 0)
			dir = 1;
		else
			dir = -1;

		if (jump)
			jumpCounter++;
		setSprite(playerX, playerY);

	}

	public void render(Screen screen, int offX, int offY) {
		int renderX = (int) pos.x - offX;
		int renderY = (int) pos.y - offY;
		screen.renderSprite(sprite, renderX, renderY, dir);
	}

	private void jump(int playerX, int playerY) {
		Vector playerPos = new Vector(playerX, playerY);

		if (rand.nextInt(jumpChance) == 0) {
			if (!jump && Math.abs(pos.dist(playerPos)) < visionDist) {

				float randExtraForce = rand.nextFloat() * 2;
				jumpForce.x += randExtraForce;

				if (pos.x - playerX > 0)
					jumpForce.x *= -1;
				else
					jumpForce.x = Math.abs(jumpForce.x);

				jump = true;
				accel.add(jumpForce);
				vel.add(jumpForce);

			} else if (!jump) {
				// just jump straight up
				jump = true;
				accel.add(jumpUp);
				vel.add(jumpUp);
			}
		}
	}

	private void setSprite(int playerX, int playerY) {

		Vector playerPos = new Vector(playerX, playerY);
		if (pos.dist(playerPos) < visionDist) {
			if (pos.x - playerPos.x > 0)
				dir = -1;
			else
				dir = 1;
		}

		if (jump) {
			if (jumpCounter < 8) {
				sprite = Sprite.goopJump2;
			} else if (jumpCounter < 16) {
				sprite = Sprite.goopJump3;
			} else if (jumpCounter < 24) {
				sprite = Sprite.goopJump4;
			} else if (jumpCounter >= 24) {
				sprite = Sprite.goopJump5;
			}
		} else {
			// if its not in the air
			if (landCounter == 0) {
				sprite = Sprite.goop;
			} else if (landCounter < 5) {
				sprite = Sprite.goopJump6;
			} else if (landCounter < 10) {
				sprite = Sprite.goopJump7;
			} else {
				sprite = Sprite.goop;
			}

		}

	}

	// same collision as player class with a couple timers removed
	private void collision(Map map) {
		Vector futurePos = new Vector(pos.x, pos.y);
		futurePos.add(vel);

		// if colliding with anything vertically
		if (vel.y > 0) {
			int futureXTile = (int) ((pos.x) / 16);
			int futureXTileRight = (int) ((pos.x + sprite.WIDTH - 1) / 16);
			// feet

			int futureYTile = (int) ((futurePos.y + sprite.HEIGHT - 1) / 16);

			if (map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| map.getTile(futureXTileRight, futureYTile) != Tile.nothing) {
				// collision with feet
				vel.y = 0;
				accel.y = 0;
				if (jump)
					landCounter = 0;
				jump = false;
				landCounter++;
			}

		} else if (vel.y <= 0) { // would rarely equal 0 tho, gravity
			int futureXTile = (int) ((pos.x) / 16);
			int futureXTileRight = (int) ((pos.x + sprite.WIDTH - 1) / 16);
			// head
			int futureYTile = (int) (futurePos.y / 16);

			if (map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| map.getTile(futureXTileRight, futureYTile) != Tile.nothing) {
				// collision with head, apply gravity instead!!
				vel.y = 0;
				accel.y = 0;
			}
		}

		// colliding with anything horizontally
		if (vel.x > 0) {

			int futureXTile = (int) ((futurePos.x + sprite.WIDTH) / 16);
			// top of head
			int futureYTile = (int) (pos.y / 16);
			// bottom of sprite
			int futureYTileBot = (int) ((pos.y + sprite.HEIGHT - 1) / 16);

			if (map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| map.getTile(futureXTile, futureYTileBot) != Tile.nothing) {
				// collision!
				vel.x = 0;
				accel.x = 0;
			}

		} else if (vel.x <= 0) {
			int futureXTile = (int) ((futurePos.x) / 16);
			// top of head
			int futureYTile = (int) (pos.y / 16);
			// bottom of sprite
			int futureYTileBot = (int) ((pos.y + sprite.HEIGHT - 1) / 16);

			if (map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| map.getTile(futureXTile, futureYTileBot) != Tile.nothing) {
				// collision!
				vel.x = 0;
				accel.x = 0;
			}
		}

	}

}
