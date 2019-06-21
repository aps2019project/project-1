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
import graphic.main.Main;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;

import java.util.ArrayList;

public class StoryMenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic;
    private Button story1Button;
    private Button story2Button;
    private Button story3Button;
    private Button exitButton;
    private Vector2 mousePos;
    private ArrayList<MoveAnimation> lanternAnimation;
    private Sprite stone1;
    private Sprite stone2;
    private Sprite stone3;
    private boolean stone1Active = false,stone2Active = false,stone3Active = false;
    @Override
    public void create() {
        setCameraAndViewport();
        shapeRenderer = new ShapeRenderer();

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

        String font = "fonts/Arial 36.fnt";
        story1Button = new Button("button/storyButton1.psd", "button/storyButton1-1.psd","sfx/playerChangeButton1.mp3", 945, 377);
        story2Button =  new Button("button/storyButton2.psd", "button/storyButton2-1.psd","sfx/playerChangeButton2.mp3",743, 366);
        story3Button =  new Button("button/storyButton3.psd","button/storyButton3-1.psd","sfx/playerChangeButton3.mp3", 503, 379);
        exitButton = new Button("button/exit.png", Main.WIDTH - 200, Main.HEIGHT - 200);
        createBackGroundMusic();        mousePos = new Vector2();
        mousePos = new Vector2();

    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        story3Button.setActive(story3Button.contains(mousePos));
        story2Button.setActive(story2Button.contains(mousePos));
        story1Button.setActive(story1Button.contains(mousePos));

        stone3Active = story3Button.contains(mousePos);
        stone2Active = story2Button.contains(mousePos);
        stone1Active = story1Button.contains(mousePos);

        exitButton.setActive(exitButton.contains(mousePos));

        stone1Animation(stone1);
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
                if (story1Button.isActive())
                    ScreenManager.setScreen(new MultiPlayerMenuScreen());
                if (story2Button.isActive())
                    ScreenManager.setScreen(new MultiPlayerMenuScreen());
                if (story3Button.isActive())
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
        shapeRenderer.setProjectionMatrix(camera.combined);

        drawBackGround(batch);
        story1Button.draw(batch);
        story2Button.draw(batch);
        story3Button.draw(batch);
        exitButton.draw(batch);
        batch.begin();
        stone2.draw(batch);
        stone1.draw(batch);
        stone3.draw(batch);
        batch.end();
    }


    @Override
    public void dispose() {
        music.dispose();
    }
    private void createBackGroundMusic() {
        music = AssetHandler.getData().get("music/login.mp3");
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
    }


    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGroundPic, 0, 0);
        batch.end();
    }

    private void stone1Animation(Sprite sprite) {
        if(stone1Active && sprite.getRotation() < 60) {
            sprite.rotate(1);
        }
        else if(!stone1Active && sprite.getRotation() > 0) {
            sprite.rotate(-1);
        }
    }
    private void stone2Animation(Sprite sprite) {
        if(stone2Active && sprite.getScaleY() > .3) {
            sprite.setScale(1, sprite.getScaleY() - 0.01f);
        }
        else if(!stone2Active && sprite.getScaleY() < 1) {
            sprite.setScale(1, sprite.getScaleY() + 0.01f);
        }
    }
    private void stone3Animation(Sprite sprite) {
        if(stone3Active && sprite.getRotation() > -60) {
            sprite.rotate(-1);
        }
        else if(!stone3Active && sprite.getRotation() < 0 ) {
            sprite.rotate(1);
        }
    }
}
