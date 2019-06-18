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
import graphic.Others.CardTexture;
import graphic.main.Gif;
import graphic.main.Main;
import model.cards.Army;

import java.awt.*;
import java.util.ArrayList;

public class TestScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private CardTexture cardTexture;
    private Vector2 mousPos;
//    private ArmyAnimation animation;

    @Override
    public void create() {
//        animation = new ArmyAnimation("Card/Hero/1.atlas");
        cardTexture = new CardTexture("adasd", "adahgfhgfhfhfhfhfhfhg jfghgfhg jfhgf hfhgf hfhgfgh da", 12, 3, "Card/Hero/8.atlas");
        setCameraAndVeiwport();
        mousPos = new Vector2();
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
        cardTexture.draw(batch, 500, 500);

//        animation.draw(batch, 100, 100, 300, 300);

    }

    @Override
    public void dispose() {

    }
}
