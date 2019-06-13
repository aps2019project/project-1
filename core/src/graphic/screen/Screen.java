package graphic.screen;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Screen {

    protected OrthographicCamera camera;
    protected Viewport viewport;


    public abstract void create();

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public abstract void dispose();

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
}
