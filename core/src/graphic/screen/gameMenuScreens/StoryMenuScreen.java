package graphic.screen.gameMenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.MoveAnimation;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;

import java.util.ArrayList;

public class StoryMenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic;
    private Button multiPlayerButton;
    private Button storyButton;
    private Button customButton;
    private Button exitButton;
    private Vector2 mousePos;
    private ArrayList<MoveAnimation> lanternAnimation;
    private Sprite stone1;
    private Sprite stone2;
    private Sprite stone3;
    @Override
    public void create() {
        setCameraAndViewport();
        backGroundPic = AssetHandler.getData().get("backGround/storyMenu.jpg");
        stone1 = new Sprite(AssetHandler.getData().get("simpleIcons/stone1.png", Texture.class));
        stone2 = new Sprite(AssetHandler.getData().get("simpleIcons/stone2.png", Texture.class));
        stone3 = new Sprite(AssetHandler.getData().get("simpleIcons/stone3.png", Texture.class));

        stone1.setOrigin(20,0);
        stone2.setOrigin(0,0);
        stone3.setOrigin(52,0);
        stone1.setPosition(946,400);
        stone2.setPosition(762,403);
        stone3.setPosition(569,389);

        mousePos = new Vector2();

    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);
        if (stone1.getBoundingRectangle().contains(mousePos))
            stoneAnimation(stone1);
        stone2Animation(stone2);
        stone3Animation(stone3);
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
                if (button != Input.Buttons.LEFT)
                    return false;
                if (multiPlayerButton.isActive())
                    ScreenManager.setScreen(new MultiPlayerMenuScreen());
                if (storyButton.isActive())
                    ScreenManager.setScreen(new MultiPlayerMenuScreen());
                if (customButton.isActive())
                    ScreenManager.setScreen(new MultiPlayerMenuScreen());

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
        drawBackGround(batch);
        batch.begin();
        stone1.draw(batch);
        stone2.draw(batch);
        stone3.draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
    }


    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGroundPic, 0, 0);
        batch.end();
    }

    private void stoneAnimation(Sprite sprite) {
        if(sprite.getRotation() < 60) {
            sprite.rotate(1);

        }
    }
    private void stone2Animation(Sprite sprite) {
        System.out.println(sprite.getScaleY());
        if(sprite.getScaleY() > .3) {
            sprite.setScale(1, sprite.getScaleY() - 0.01f);
        }
    }
    private void stone3Animation(Sprite sprite) {
        if(sprite.getRotation() > -60) {
            sprite.rotate(-1);

        }
    }
}
