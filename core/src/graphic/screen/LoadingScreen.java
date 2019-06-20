package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import graphic.main.AssetHandler;
import graphic.main.Gif;
import graphic.main.Main;
import graphic.screen.gameMenuScreens.ChooseNumberOfPlayersMenuScreen;

import java.awt.*;


public class LoadingScreen extends Screen {

    private float progress = 0;
    private Gif loadingGif;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        setCameraAndViewport();
        loadingGif = new Gif("loading.gif");
        font = new BitmapFont(Gdx.files.internal("fonts/Arial 48B.fnt"));
        glyphLayout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update() {
        camera.update();
        if (AssetHandler.getData().update())
        ScreenManager.setScreen(new MenuScreen());
        progress = AssetHandler.getData().getProgress() * 100;
          
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(0xFFFFFF)));
        shapeRenderer.rect(130, 300, 1225, 40);
        shapeRenderer.setColor(Main.toColor(new Color(0x000000)));
        shapeRenderer.rect(150 + (1200 * progress / 100), 305, 1200 - (1200 * progress / 100), 30);
        shapeRenderer.end();
        batch.begin();
        glyphLayout.setText(font, "Loading");
        font.draw(batch, "Loading", (Main.WIDTH - glyphLayout.width) / 2 - 100, Main.HEIGHT - (Main.HEIGHT - glyphLayout.height) / 2 + 50);
        glyphLayout.setText(font, (int) progress + "%");
        font.draw(batch, (int) progress + "%", 1400, 340 - (40 - glyphLayout.height) / 2);
        batch.end();
        loadingGif.draw(batch, Main.WIDTH/2, Main.HEIGHT/2 - 20 + 50);
    }

    @Override
    public void dispose() {

    }
}
