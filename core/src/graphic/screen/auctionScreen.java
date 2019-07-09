package graphic.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.main.AssetHandler;

public class auctionScreen extends Screen {

    private Texture background;

    @Override
    public void create() {
        setCameraAndViewport();
        background = AssetHandler.getData().get("backGround/auction background.png");

    }

    @Override
    public void update() {
        camera.update();

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.end();

    }

    @Override
    public void dispose() {

    }
}
