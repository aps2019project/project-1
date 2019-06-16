package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.ArmyAnimation;
import graphic.main.Gif;
import graphic.main.Main;
import model.cards.Army;

import java.awt.*;
import java.util.ArrayList;

public class TestScreen extends Screen {

    private ShapeRenderer shapeRenderer;
//    private ArmyAnimation animation;
    private ArrayList<ArmyAnimation> generals = new ArrayList<ArmyAnimation>();
    private Gif gif;
    private Vector2 mousPos;

    @Override
    public void create() {
        setCameraAndVeiwport();
        mousPos = new Vector2();
        for (int i = 1; i < 12; ++i) {
            generals.add(new ArmyAnimation("Card/Hero/" + i + ".atlas"));
        }
        gif = new Gif("loading.gif");
        shapeRenderer = new ShapeRenderer();

    }

    @Override
    public void update() {
        camera.update();
        mousPos.set(Gdx.input.getX(), Gdx.input.getY());
        mousPos = viewport.unproject(mousPos);
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.A)
                    for (ArmyAnimation animation: generals)
                        animation.attack();
                else if (keycode == Input.Keys.D)
                    for (ArmyAnimation animation: generals)
                        animation.death();

                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                for (ArmyAnimation animation: generals)
                    animation.run(mousPos.x, mousPos.y);
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        });



    }

    @Override
    public void render(SpriteBatch batch) {

        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Main.toColor(new Color(98787)));
        shapeRenderer.rect(0, 0, Main.WIDTH, Main.HEIGHT);
        shapeRenderer.end();
        for (int i = 0; i < 6; ++i) {
            int x = 100 + 300 * i;
            int y = 100;
            generals.get(i).draw(batch, x, y);
        }
        for (int i = 0; i < 5; ++i) {
            int x = 100 + 300 * i;
            int y = 500;
            generals.get(i + 6).draw(batch, x, y);
        }

    }

    @Override
    public void dispose() {

    }
}
