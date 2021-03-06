package graphic.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.Others.PopUp;
import graphic.screen.GetIpScreen;
import graphic.screen.LoadingScreen;
import graphic.screen.ScreenManager;
import model.cards.Card;
import model.other.Account;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;

	public static final int WIDTH = 1600;
	public static final int HEIGHT = 900;

	@Override
	public void create () {
//		createMouseIcon("mouse.png");
		Card.scanAllCards();
		AssetHandler.load();
		ScreenManager.setScreen(new GetIpScreen());
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ScreenManager.getScreen().update();
		ScreenManager.getScreen().render(batch);
		PopUp.getInstance().draw(batch);
	}

	@Override
	public void dispose () {
		if (ScreenManager.getScreen() != null)
			ScreenManager.getScreen().dispose();
		batch.dispose();
	}

	private void createMouseIcon(String picPath) {
		Pixmap pm = new Pixmap(Gdx.files.internal(picPath));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
		pm.dispose();
	}

	public static Color toColor(java.awt.Color color) {
		float r = color.getRed() / 256f;
		float g = color.getGreen() / 256f;
		float b = color.getBlue() / 256f;
		float a = color.getAlpha() / 256f;
		return new Color(r, g, b, a);
	}

	public static boolean isCharacterOK(char character) {
		if (((int)character >= (int)'a' && (int)character <= (int)'z') ||
				((int)character >= (int)'A' && (int)character <= (int)'Z') ||
				((int)character >= (int)'0' && (int)character <= (int)'9'))
			return true;
		if (character == '_' || character == '*' || character == '-') return true;
		if (character == '.' || character == '~' || character == ' ') return true;
		return false;
	}

}
