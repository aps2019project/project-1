package graphic.screen.gameMenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;

import java.util.ArrayList;

public class ChooseNumberOfPlayersMenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic;
    private Button multiPlayerButton;
    private Button storyButton;
    private Button customButton;
    private Button exitButton;
    private Vector2 mousePos;
    private ArrayList<MoveAnimation> lanternAnimation;

    @Override
    public void create() {
        setCameraAndViewport();
        createFireAnimation();
        shapeRenderer = new ShapeRenderer();
        backGroundPic = AssetHandler.getData().get("backGround/background_ChooseNumberOfPlayersMenu.psd");

        String font = "fonts/Arial 36.fnt";
        multiPlayerButton = new Button("button/choosePlayerButton1.psd", "button/choosePlayerButton1-1.psd","sfx/playerChangeButton1.mp3", 303, 278, "Multi Player", font);
        storyButton =  new Button("button/choosePlayerButton2.psd", "button/choosePlayerButton2-1.psd","sfx/playerChangeButton2.mp3",755, 135, "Story", font);
        customButton =  new Button("button/choosePlayerButton3.psd","button/choosePlayerButton3-1.psd","sfx/playerChangeButton3.mp3", 1150, 351, "Custom", font);
        exitButton = new Button("button/exit.png", Main.WIDTH - 200, Main.HEIGHT - 200);
        createBackGroundMusic();        mousePos = new Vector2();

    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        customButton.setActive(customButton.contains(mousePos));
        storyButton.setActive(storyButton.contains(mousePos));
        multiPlayerButton.setActive(multiPlayerButton.contains(mousePos));
        exitButton.setActive(exitButton.contains(mousePos));


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
                    ScreenManager.setScreen(new StoryMenuScreen());
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
        shapeRenderer.setProjectionMatrix(camera.combined);


        drawBackGround(batch);
        multiPlayerButton.draw(batch);
        storyButton.draw(batch);
        customButton.draw(batch);
        exitButton.draw(batch);
        showFireAnimation(batch);
    }

    private void showFireAnimation(SpriteBatch batch) {
        for (MoveAnimation animation: lanternAnimation) {
            animation.draw(batch);
        }
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    private void createFireAnimation() {
        lanternAnimation = new ArrayList<MoveAnimation>();
        for (int i = 0; i < 120; ++i) {
            int fireType = (int) (7 * Math.random() + 1);
            float xStart;
            float yStart;
            float xEnd;
            float yEnd;
            if (fireType > 6) {
                fireType = 1;
                xStart = (int) (700 + (Math.random() * 200));
                yStart = (int) (30 + 70 * Math.random());
                xEnd = (int) (650 + 300 * Math.random());;
                yEnd = (int) (100 + Math.random()*200);

            } else if (fireType > 3) {
                fireType = 2;
                xStart = (int) (650 + (Math.random() * 300));
                yStart = (int) (100 * Math.random());
                xEnd = (int) (770 + 60 * Math.random());
                yEnd = 300;

            } else {
                fireType = 3;
                xStart = (int) (700 + (Math.random() * 200));
                yStart = (int) (100 * Math.random());
                xEnd = (int) (730 + 140 * Math.random());
                yEnd = 150;
            }
            lanternAnimation.add(new MoveAnimation("simpleIcons/fire" + fireType + ".png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            lanternAnimation.get(i).setSpeed((float) (1 + (Math.random() + 0.5f) - fireType / 2));
        }
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
}
