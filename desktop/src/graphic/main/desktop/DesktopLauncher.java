package graphic.main.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import graphic.main.Main;
import model.cards.Card;
import model.other.Account;
import network.Client;
import network.Server;

import java.io.IOException;
import java.net.Socket;

public class DesktopLauncher {
	public static Client client = null;
	public static void main (String[] arg) {
		setup(arg);
	}

	public static void mainClient(String[] arg) {
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

	public static void setup(String[] args) {
		if(!isServerCreated()) {
			Server server = new Server();
			Card.scanAllCards();
			Account.readAccountDetails();
			server.main(args);
		}
		else {
			client = new Client("127.0.0.1", 5000);
			mainClient(args);
		}
	}
	public static boolean isServerCreated() {
		try (Socket s = new Socket("127.0.0.1", 5000)) {
			return true;
		} catch (IOException ex) {
			/* ignore */
		}
		return false;
	}


}
