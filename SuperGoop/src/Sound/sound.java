package Sound;

import java.applet.Applet;
import java.applet.AudioClip;

public class sound {

	public static final sound goopHit = new sound("/sound/slime.wav");
	public static final sound ld40 = new sound("/sound/ld40.wav");

	private AudioClip sound;

	private sound(String name) {
		sound = Applet.newAudioClip(sound.class.getResource(name));
	}

	public void play(boolean loop) {
		if (loop) {
			sound.loop();
		} else {
			sound.play();
		}
	}

	public void stop() {
		sound.stop();
	}

	public static void stopAll() {
		goopHit.stop();
	}
}
