package graphic.main.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import graphic.main.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = 0;
		config.y = 0;
		config.resizable = true;
		config.fullscreen = false;
		config.title = "Duelyst";
		config.width = 1600;
		config.height = 901;
		config.forceExit = true;
		config.addIcon("icon.png", Files.FileType.Internal);
		new LwjglApplication(new Main(), config);
	}
}
