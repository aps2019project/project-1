package graphic.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.main.AssetHandler;

public class LoginScreen extends Screen {
    @Override
    public void create() {

    }

    @Override
    public void update() {

    }

    @Override
    public void render(SpriteBatch batch) {
        Texture texture = AssetHandler.getData().get("badlogic.jpg");
        batch.begin();
        batch.draw(texture, 10, 10);
        batch.end();
    }

    @Override
    public void dispose() {

    }
}
