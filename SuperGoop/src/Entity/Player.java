package Entity;

import java.util.ArrayList;

import Input.Keyboard;
import Level.Level;
import Load.Sprite;
import Load.Tile;
import Math.Vector;
import Screen.Screen;
import Sound.sound;

public class Player {

	public Vector pos;

	public int dir = 1;

	public Vector accel;
	public Vector maxAccel = new Vector(0.8f, 2.5f); // 0.8 2.5 effect 0

	private float speed = 0.2f; // effect 1

	public Vector vel;
	private Vector maxVel = new Vector(8, 8); // 6 6 effect 2

	private Vector friction = new Vector(0.8f, 0.8f);// 0.8 effect 3

	public static Vector gravity; // effect 4

	private Sprite sprite;
	private int motion;

	private boolean jump = true; // true while on ground
	private boolean doubleJump = true;
	private int jumpCounter = 0;
	private int touchTimer = 0; // time after touching the ground

	private int goopsHit = 0; // number of slimes touched
	private ArrayList<Vector> goopEffects = new ArrayList<Vector>();
	private int goopApply = 4;

	private boolean victory = false;
	private boolean dead = false;
	private Vector deathPos = new Vector(0, 0);

	private int gameTimout = 60;
	private int timeoutCounter = 0;

	private int levelNum = 2;

	private boolean startMusic = false;

	public Player() {
		pos = new Vector(-32, -32);
		vel = new Vector();
		accel = new Vector();

		gravity = new Vector(0, 0.15f); // 0.15

		sprite = Sprite.playerStand;
	}

	public void spawnPlayer(Vector spawn) {
		pos = spawn;
		pos.mult((float) 16);
		pos.y -= 16;

		goopsHit = 0;
		goopEffects.clear();
		victory = false;
		dead = false;
		deathPos = new Vector(0, 0);

		jumpCounter = 0;
		touchTimer = 0;
		timeoutCounter = 0;

		setDefaults();
	}

	public void update(Keyboard key, Level level) {

		if (!startMusic) {
			sound.ld40.play(true);
			startMusic = true;
		}

		if (!victory && !dead && goopsHit != 5) {
			// if player has won or is dead then don't accept movement

			setDefaults();
			applyEffects();

			if (key.left) {
				accel.x -= speed;
			}

			if (key.right) {
				accel.x += speed;
			}

			// if on the ground and jumping
			if (!jump)
				jumpCounter++;
			if (key.space) {
				if (jump && touchTimer >= 5) {
					vel.y = -maxVel.y;
					accel.y = -maxAccel.y;
					jump = false;
					touchTimer = 0;
				} else if (doubleJump && jumpCounter > 20) {
					vel.y = -maxVel.y / 4 * 3;
					accel.y = -maxAccel.y / 4 * 3;
					doubleJump = false;
					touchTimer = 0;
				}

			}

			accel.add(gravity);

			accel.limit(maxAccel);

			// scale down speed based on friction
			accel.mult(friction);
			vel.mult(friction);

			vel.add(accel);
			vel.limit(maxVel);

			collision(level);
			pos.add(vel);

			goopCollision(level);
			flagCollision(level);

			if (vel.x > 1 || vel.x < -1)
				motion++;
			else
				motion = 0;

			if (vel.x > 0)
				dir = 1;
			if (vel.x < 0)
				dir = -1;

			if (motion != 0 && jump)
				chooseSprite();
			else {
				switch (goopsHit) {
				case 0:
					sprite = Sprite.playerStand;
					break;
				case 1:
					sprite = Sprite.playerStandg1;
					break;
				case 2:
					sprite = Sprite.playerStandg2;
					break;
				case 3:
					sprite = Sprite.playerStandg3;
					break;
				case 4:
					sprite = Sprite.playerStandg4;
					break;
				default:
					sprite = Sprite.playerStand;
				}
			}
		} else {
			timeoutCounter++;
			if (timeoutCounter > gameTimout) {
				if (key.space || key.down || key.up || key.right || key.left || key.respawn) {
					if (victory) {
						levelNum++;
						level.startLevel(this, levelNum);
					} else {
						level.startLevel(this, levelNum);
					}
				}
			}
		}

	}

	public void render(Screen screen, Level level) {

		if (!victory && !dead && goopsHit != 5)
			screen.renderPlayer(sprite, dir);
		// always gonna be centered so no need for x and y
		else if (victory) {
			screen.renderSprite(Sprite.victory, (int) (level.objective.x * 16 - level.offX),
					(int) (level.objective.y * 16 - level.offY - (Sprite.victory.HEIGHT / 4 * 3)), 1);
		} else if (dead) {
			screen.renderSprite(Sprite.spikeDeath, (int) (deathPos.x * 16 - level.offX - 8),
					(int) (deathPos.y * 16 - level.offY - (Sprite.victory.HEIGHT)), 1);
		} else if (goopsHit == 5) {
			screen.renderSprite(Sprite.playerStandDefeat, (int) (pos.x - level.offX), (int) (pos.y - level.offY), 1);
		}
	}

	private void collision(Level level) {
		Vector futurePos = new Vector(pos.x, pos.y);
		futurePos.add(vel);

		// if colliding with anything vertically
		if (vel.y > 0) {
			int futureXTile = (int) ((pos.x) / 16);
			int futureXTileRight = (int) ((pos.x + sprite.WIDTH - 1) / 16);
			// feet

			int futureYTile = (int) ((futurePos.y + sprite.HEIGHT - 1) / 16);

			if (level.map.getTile(futureXTile, futureYTile) == Tile.spike
					|| level.map.getTile(futureXTileRight, futureYTile) == Tile.spike) {
				dead = true;
				vel.y = 0;
				accel.y = 0;
				accel.x = 0;
				vel.x = 0;
				deathPos.x = futureXTile;
				deathPos.y = futureYTile;
			} else if (level.map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| level.map.getTile(futureXTileRight, futureYTile) != Tile.nothing) {
				// collision with feet
				vel.y = 0;
				accel.y = 0;
				jump = true;
				doubleJump = true;
				jumpCounter = 0;
				touchTimer++;
			}

		} else if (vel.y <= 0) { // would rarely equal 0 tho, gravity
			int futureXTile = (int) ((pos.x) / 16);
			int futureXTileRight = (int) ((pos.x + sprite.WIDTH - 1) / 16);
			// head
			int futureYTile = (int) (futurePos.y / 16);

			if (level.map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| level.map.getTile(futureXTileRight, futureYTile) != Tile.nothing) {
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
			int futureYTileMid = (int) (pos.y + (sprite.HEIGHT / 2)) / 16;
			int futureYTileBot = (int) ((pos.y + sprite.HEIGHT - 1) / 16);

			if (level.map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| level.map.getTile(futureXTile, futureYTileBot) != Tile.nothing
					|| level.map.getTile(futureXTile, futureYTileMid) != Tile.nothing) {
				// collision!
				vel.x = 0;
				accel.x = 0;
			}

		} else if (vel.x <= 0) {
			int futureXTile = (int) ((futurePos.x) / 16);
			// top of head
			int futureYTile = (int) (pos.y / 16);
			// bottom of sprite
			int futureYTileMid = (int) (pos.y + (sprite.HEIGHT / 2)) / 16;
			int futureYTileBot = (int) ((pos.y + sprite.HEIGHT - 1) / 16);

			if (level.map.getTile(futureXTile, futureYTile) != Tile.nothing
					|| level.map.getTile(futureXTile, futureYTileBot) != Tile.nothing
					|| level.map.getTile(futureXTile, futureYTileMid) != Tile.nothing) {
				// collision!
				vel.x = 0;
				accel.x = 0;
			}
		}
	}

	private void goopCollision(Level level) {
		Goop currentGoop;

		int midBody = (int) pos.y + (sprite.HEIGHT / 2) - 1;
		int foot = (int) (pos.y + sprite.HEIGHT - 1);

		for (int i = level.GoopList.size() - 1; i >= 0; i--) {
			// for each goop compare positions for collision
			currentGoop = level.GoopList.get(i);

			// left side of player
			if (pos.x > currentGoop.pos.x && pos.x < currentGoop.pos.x + currentGoop.sprite.WIDTH) {
				// top of head
				if (pos.y > currentGoop.pos.y && pos.y < currentGoop.pos.y + currentGoop.sprite.HEIGHT) {

					goopsHit++;
					goopApply = level.map.goopEffect;
					level.GoopList.remove(i);
					sound.goopHit.play(false);

					// middle of body
				} else if (midBody > currentGoop.pos.y && midBody < currentGoop.pos.y + currentGoop.sprite.HEIGHT) {

					goopsHit++;
					goopApply = level.map.goopEffect;
					level.GoopList.remove(i);
					sound.goopHit.play(false);

					// bottom foot
				} else if (foot > currentGoop.pos.y && foot < currentGoop.pos.y + currentGoop.sprite.HEIGHT) {

					goopsHit++;
					goopApply = level.map.goopEffect;
					level.GoopList.remove(i);
					sound.goopHit.play(false);
				}
			}

			// right side player
			if (pos.x + sprite.WIDTH > currentGoop.pos.x
					&& pos.x + sprite.WIDTH < currentGoop.pos.x + currentGoop.sprite.WIDTH) {
				// top of head
				if (pos.y > currentGoop.pos.y && pos.y < currentGoop.pos.y + currentGoop.sprite.HEIGHT) {

					goopsHit++;
					goopApply = level.map.goopEffect;
					level.GoopList.remove(i);
					sound.goopHit.play(false);

					// middle of body
				} else if (midBody > currentGoop.pos.y && midBody < currentGoop.pos.y + currentGoop.sprite.HEIGHT) {

					goopsHit++;
					goopApply = level.map.goopEffect;
					level.GoopList.remove(i);
					sound.goopHit.play(false);

					// bottom foot
				} else if (foot > currentGoop.pos.y && foot < currentGoop.pos.y + currentGoop.sprite.HEIGHT) {

					goopsHit++;
					goopApply = level.map.goopEffect;
					level.GoopList.remove(i);
					sound.goopHit.play(false);

				}
			}

		}

	}

	private void flagCollision(Level level) {
		Vector flagPos = level.objective;

		if ((int) (pos.x / 16) == flagPos.x) {
			if ((int) (pos.y / 16) == flagPos.y || (int) ((pos.y + sprite.HEIGHT - 1) / 16) == flagPos.y) {
				// same tile as flag then play victory
				victory = true;
				level.objectiveSprite = Sprite.nothing;// don't draw the flag
														// anymore
			}
		}

	}

	// massive switch, not sure what else to do though
	private void chooseSprite() {

		int spriteNum = (motion / 5) % 5;
		switch (spriteNum) {
		case 0:
			switch (goopsHit) {
			case 0:
				sprite = Sprite.playerWalk0;
				break;
			case 1:
				sprite = Sprite.playerWalk0g1;
				break;
			case 2:
				sprite = Sprite.playerWalk0g2;
				break;
			case 3:
				sprite = Sprite.playerWalk0g3;
				break;
			case 4:
				sprite = Sprite.playerWalk0g4;
				break;
			}
			break;
		case 1:
			switch (goopsHit) {
			case 0:
				sprite = Sprite.playerWalk1;
				break;
			case 1:
				sprite = Sprite.playerWalk1g1;
				break;
			case 2:
				sprite = Sprite.playerWalk1g2;
				break;
			case 3:
				sprite = Sprite.playerWalk1g3;
				break;
			case 4:
				sprite = Sprite.playerWalk1g4;
				break;
			}
			break;
		case 2:
			switch (goopsHit) {
			case 0:
				sprite = Sprite.playerWalk2;
				break;
			case 1:
				sprite = Sprite.playerWalk2g1;
				break;
			case 2:
				sprite = Sprite.playerWalk2g2;
				break;
			case 3:
				sprite = Sprite.playerWalk2g3;
				break;
			case 4:
				sprite = Sprite.playerWalk2g4;
				break;
			}
			break;
		case 3:
			switch (goopsHit) {
			case 0:
				sprite = Sprite.playerWalk3;
				break;
			case 1:
				sprite = Sprite.playerWalk3g1;
				break;
			case 2:
				sprite = Sprite.playerWalk3g2;
				break;
			case 3:
				sprite = Sprite.playerWalk3g3;
				break;
			case 4:
				sprite = Sprite.playerWalk3g4;
				break;
			}
			break;
		case 4:
			switch (goopsHit) {
			case 0:
				sprite = Sprite.playerWalk4;
				break;
			case 1:
				sprite = Sprite.playerWalk4g1;
				break;
			case 2:
				sprite = Sprite.playerWalk4g2;
				break;
			case 3:
				sprite = Sprite.playerWalk4g3;
				break;
			case 4:
				sprite = Sprite.playerWalk4g4;
				break;
			}
			break;

		}
	}

	private void setDefaults() {
		friction.x = 0.8f;
		friction.y = 0.8f;
		maxAccel.x = 0.8f;
		maxAccel.y = 2.5f;
		maxVel.x = 8;
		maxVel.y = 8;
		speed = 0.2f;
		gravity.y = 0.15f;
	}

	private void applyEffects() {

		switch (goopApply) {
		case 0: // accel
			// Vector subtract = new Vector();
			maxAccel.x = 0.8f + (0.5f * goopsHit);
			break;
		case 1: // speed
			speed = 0.2f + (0.1f * goopsHit);
			break;
		case 2: // velocity
			break;
		case 3: // friction
			// slippery!
			friction.x = 0.8f + (0.02f * goopsHit);
			break;
		case 4: // gravity
			gravity.y = 0.15f - (0.025f * goopsHit);
			break;
		}

	}

}
