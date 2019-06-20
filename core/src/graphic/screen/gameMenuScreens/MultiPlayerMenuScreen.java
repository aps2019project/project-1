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
import graphic.screen.BattleScreen;
import graphic.screen.ScreenManager;

import java.util.ArrayList;

public class MultiPlayerMenuScreen extends graphic.screen.Screen {

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
        setCameraAndVeiwport();
        createLanternsAnimation();
        shapeRenderer = new ShapeRenderer();
        backGroundPic = AssetHandler.getData().get("backGround/background_ChooseNumberOfPlayersMenu.jpg");

        String font = "fonts/Arial 24.fnt";
        multiPlayerButton = new Button("button/big_circle.png", "button/big_circle_action.png", 500, 320, "Multi Player", font);
        storyButton =  new Button("button/big_circle.png", "button/big_circle_action.png",900, 320, "Story", font);
        customButton =  new Button("button/big_circle.png","button/big_circle_action.png", 1300, 320, "Custom", font);
        exitButton = new Button("button/exit.png", Main.WIDTH - 200, Main.HEIGHT - 200);
        mousePos = new Vector2();
        createBackGroundMusic();
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
                    ScreenManager.setScreen(new BattleScreen());
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
        showLanternAnimation(batch);
        multiPlayerButton.draw(batch);
        storyButton.draw(batch);
        customButton.draw(batch);
        exitButton.draw(batch);
    }

    private void showLanternAnimation(SpriteBatch batch) {
        for (MoveAnimation animation: lanternAnimation) {
            animation.draw(batch);
        }
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    private void createLanternsAnimation() {
        lanternAnimation = new ArrayList<MoveAnimation>();
        for (int i = 0; i < 40; ++i) {
            float xStart = 100 + (int) (900 * Math.random());
            float yStart = (750 - xStart/6f) + (int) (100 * Math.random());
            float xEnd = 900 + (float)(Math.random() * 700), yEnd = 900;
            int fireType = (int) (5 * Math.random() + 1);
            if (fireType < 3)
                fireType = 1;
            else if (fireType < 5)
                fireType = 2;
            else
                fireType = 3;
            fireType = 1;///////////////////////
            lanternAnimation.add(new MoveAnimation("simpleIcons/fire" + fireType + ".png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            lanternAnimation.get(i).setSpeed((float)( 1 + (Math.random() + 0.5f) - fireType / 2));
        }
    }

    private void createBackGroundMusic() {
        music = AssetHandler.getData().get("music/menu.mp3");
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
