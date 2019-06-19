package graphic.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import graphic.main.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 450;
		config.height = 450;
//		config.fullscreen = true;
		config.resizable = false;
		config.forceExit = true;
		new LwjglApplication(new Main(), config);
	}
}
