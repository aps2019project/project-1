package graphic.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Others.PopUp;
import graphic.screen.LoadingScreen;
import graphic.screen.ScreenManager;
import model.cards.Card;
import model.other.Account;

public class Main extends ApplicationAdapter {
	private static SpriteBatch batch;

	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;

	@Override
	public void create () {
//		control.Main main = new control.Main();
//		main.start();
		Card.scanAllCards();
		Account.readAccountDetails();
		AssetHandler.load();
		ScreenManager.setScreen(new LoadingScreen());
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		try {
			Gdx.gl.glClearColor(0, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			ScreenManager.getScreen().update();
			ScreenManager.getScreen().render(batch);
			PopUp.getInstance().draw(batch);
		} catch (Exception e) {
			PopUp.getInstance().setText("Exception in render:" + e.getMessage());
		}
	}
	
	@Override
	public void dispose () {
		if (ScreenManager.getScreen() != null)
			ScreenManager.getScreen().dispose();
		batch.dispose();
	}

	public static Color toColor(java.awt.Color color) {
		float r = color.getRed() / 256f;
		float g = color.getGreen() / 256f;
		float b = color.getBlue() / 256f;
		float a = color.getAlpha() / 256f;
		return new Color(r, g, b, a);
	}

	public static SpriteBatch getBatch() {
		return batch;
	}

}
