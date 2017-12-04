package Math;

public class Vector {

	public float x = 0, y = 0;

	public Vector() {
		this.x = 0;
		this.y = 0;
	}

	public Vector(int x, int y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void limit(float max) {
		if (x > max)
			x = max;
		if (x < -max)
			x = -max;
		if (y > max)
			y = max;
		if (y < -max)
			y = -max;
	}

	public void limit(Vector max) {
		if (x > max.x)
			x = max.x;
		if (x < -max.x)
			x = -max.x;
		if (y > max.y)
			y = max.y;
		if (y < -max.y)
			y = -max.y;
	}

	public void add(int add) {
		x += add;
		y += add;
	}

	public void add(Vector add) {
		x += add.x;
		y += add.y;
	}

	public void sub(Vector sub) {
		x -= sub.x;
		y -= sub.y;
	}

	public void mult(float scaler) {
		x *= scaler;
		y *= scaler;
	}

	public void mult(Vector scalers) {
		x *= scalers.x;
		y *= scalers.y;
	}

	public void div(float scaler) {
		x /= scaler;
		y /= scaler;
	}

	public float dist(Vector distVector) {
		return (float) Math.hypot(x - distVector.x, y - distVector.y);
	}

	public float getMag() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public void maxMag(float maxSpeed) {
		float oldMag = getMag();
		x = x * (maxSpeed / oldMag);
		y = y * (maxSpeed / oldMag);
	}

	public void normalize() {
		float mag = getMag();
		x /= mag;
		y /= mag;
	}

}
