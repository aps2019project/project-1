package graphic.screen;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen {

    public abstract void create();

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public abstract void dispose();
}
