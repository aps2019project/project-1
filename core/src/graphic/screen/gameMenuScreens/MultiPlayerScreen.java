
package graphic.screen.gameMenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import connection.Client;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import graphic.screen.BattleScreen;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;
import model.game.GameType;

import java.util.ArrayList;

public class MultiPlayerScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic;
    private Button exitButton;
    private Vector2 mousePos;
    private Button killHeroButton;
    private Button captureTheFlagButton;
    private Button rollUpFlagsButton;
    private Button increaseNumberOfFlagsButton;
    private Button decreaseNumberOfFlagsButton;
    private Button startMultiPlayerGameButton;
    private BitmapFont font;
    private GameType gameType = GameType.KILL_HERO;
    private int numberOfFlags = 1;
    @Override
    public void create() {
        String font = "fonts/Arial 36.fnt";
        this.font = AssetHandler.getData().get(font);
        setCameraAndViewport();
        shapeRenderer = new ShapeRenderer();
        backGroundPic = AssetHandler.getData().get("backGround/secondCustomMenu.jpg");
        mousePos = new Vector2();
        killHeroButton = new Button("button/secondCustom1.png", "button/secondCustom1-1.png", "sfx/click.mp3", 100, 700, "kill hero", font);
        captureTheFlagButton = new Button("button/secondCustom1.png", "button/secondCustom1-1.png", "sfx/click.mp3", 500, 700, "capture the flag", font);
        rollUpFlagsButton = new Button("button/secondCustom1.png", "button/secondCustom1-1.png", "sfx/click.mp3", 900, 700, "rollup flags", font);
        increaseNumberOfFlagsButton = new Button("button/increaseButton.png", "button/increaseButton.png", "sfx/click.mp3", 800, 450);
        decreaseNumberOfFlagsButton = new Button("button/decreaseButton.png", "button/decreaseButton.png", "sfx/click.mp3", 400, 450);
        startMultiPlayerGameButton = new Button("button/red.png", "button/red glow.png", "sfx/click.mp3", 800, 200, "start", font);
        killHeroButton.setActive(true);
        captureTheFlagButton.setActive(false);
        rollUpFlagsButton.setActive(false);
        exitButton = new Button("button/exit.png", Main.WIDTH - 200, Main.HEIGHT - 200);
        createBackGroundMusic();        mousePos = new Vector2();

    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);
        increaseNumberOfFlagsButton.setActive(increaseNumberOfFlagsButton.contains(mousePos));
        decreaseNumberOfFlagsButton.setActive(decreaseNumberOfFlagsButton.contains(mousePos));
        startMultiPlayerGameButton.setActive(startMultiPlayerGameButton.contains(mousePos));

        exitButton.setActive(exitButton.contains(mousePos));
        //updateAccounts();
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
                if(startMultiPlayerGameButton.isActive()) {
                    String type = "";
                    if(gameType == GameType.KILL_HERO) type = "kill hero";
                    else if(gameType == GameType.CAPTURE_THE_FLAG) type = "capture the flag";
                    else if(gameType == GameType.ROLLUP_FLAGS)      type = "rollup flags";
                    Client.applyPlayMultiPlayerGame(type, numberOfFlags);
                    ScreenManager.setScreen(new WaitingScreen(gameType, numberOfFlags));
                } else if(killHeroButton.contains(mousePos)) {
                    killHeroButton.setActive(true);
                    captureTheFlagButton.setActive(false);
                    rollUpFlagsButton.setActive(false);
                    gameType = GameType.KILL_HERO;
                } else if(captureTheFlagButton.contains(mousePos)) {
                    killHeroButton.setActive(false);
                    captureTheFlagButton.setActive(true);
                    rollUpFlagsButton.setActive(false);
                    gameType = GameType.CAPTURE_THE_FLAG;
                } else if(rollUpFlagsButton.contains(mousePos)) {
                    killHeroButton.setActive(false);
                    captureTheFlagButton.setActive(false);
                    rollUpFlagsButton.setActive(true);
                    gameType = GameType.ROLLUP_FLAGS;
                } else if(gameType == GameType.ROLLUP_FLAGS && increaseNumberOfFlagsButton.isActive()) {
                    if(numberOfFlags< 7) numberOfFlags++;
                } else if(gameType == GameType.ROLLUP_FLAGS && decreaseNumberOfFlagsButton.isActive()) {
                    if(numberOfFlags > 1) numberOfFlags--;
                } else if(exitButton.isActive()) {
                    ScreenManager.setScreen(new ChooseNumberOfPlayersMenuScreen());

                }
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
        exitButton.draw(batch);
        killHeroButton.draw(batch);
        captureTheFlagButton.draw(batch);
        rollUpFlagsButton.draw(batch);
        startMultiPlayerGameButton.draw(batch);
        if(gameType == GameType.ROLLUP_FLAGS){
            increaseNumberOfFlagsButton.draw(batch);
            decreaseNumberOfFlagsButton.draw(batch);
            batch.begin();
            font.draw(batch, Integer.toString(numberOfFlags), 630, 500);
            batch.end();
        }
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
}
