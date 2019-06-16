package graphic.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import graphic.main.AssetHandler;
import graphic.main.Main;
import model.other.Account;


public class LoadingScreen extends Screen {


    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    @Override
    public void update() {
        camera.update();
        if (AssetHandler.getData().update())
            ScreenManager.setScreen(new TestScreen());


    }

    @Override
    public void render(SpriteBatch batch) {
    }

    @Override
    public void dispose() {

    }
}
