package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.other.Account;
import model.other.SavingObject;

import java.util.ArrayList;
import java.util.HashSet;

public class GlobalInformationScreen extends Screen {

    private Texture backGround;
    private Texture forGround;
    private Button backButton;
    private Button chatButton;
    private Button scoreBaordButton;
    private ArrayList<Account> allAccounts;
    private HashSet<Account> onlineAccounts;
    private Vector2 mousePos;



    @Override
    public void create() {
        setCameraAndViewport();
        mousePos = new Vector2();

        backGround = AssetHandler.getData().get("backGround/global background.png");
        forGround = AssetHandler.getData().get("backGround/global forground.png");

        backButton = new Button("button/back.png", 0, Main.HEIGHT -50, 50, 50);

        playBackGroundMusic("music/login.mp3");
    }

    @Override
    public void update() {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        backButton.setActive(backButton.contains(mousePos));

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.PAGE_UP)
                    setMusicVolume(true);
                if (keycode == Input.Keys.PAGE_DOWN)
                    setMusicVolume(false);
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
                if (backButton.isActive()) {
                    ScreenManager.setScreen(new MenuScreen());
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

        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(forGround, Main.WIDTH - forGround.getWidth(), 0);
        batch.end();

        backButton.draw(batch);


    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
