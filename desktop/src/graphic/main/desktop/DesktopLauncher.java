package graphic.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import graphic.main.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = 0;
		config.y = 0;
		config.resizable = false;
		config.fullscreen = false;
		config.width = 1600;
		config.height = 900;
		config.forceExit = true;
		new LwjglApplication(new Main(), config);
	}
}
