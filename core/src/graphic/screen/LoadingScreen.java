package graphic.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import graphic.main.AssetHandler;
import graphic.main.Gif;
import graphic.main.Main;


public class LoadingScreen extends Screen {

    private Gif loadingGif;

    @Override
    public void create() {
        loadingGif = new Gif("loading.gif");
        camera = new OrthographicCamera();
        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    @Override
    public void update() {
        camera.update();
        if (AssetHandler.getData().update())
            ScreenManager.setScreen(new LoginScreen());
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        loadingGif.draw(batch, (viewport.getScreenWidth() - 150) / 2, (viewport.getScreenHeight() - 150) / 2);
    }

    @Override
    public void dispose() {

    }
}
