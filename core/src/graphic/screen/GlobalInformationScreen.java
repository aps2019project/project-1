package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.google.gson.reflect.TypeToken;
import connection.Client;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.other.Account;
import model.other.SavingObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;

public class GlobalInformationScreen extends Screen {

    private Texture backGround;
    private Texture forGround;
    private Button backButton;
    private Button chatButton;
    private Button scoreBoardButton;
    private ArrayList<SavingObject> allAccounts;
    private HashSet<String> onlineAccounts;
    private Vector2 mousePos;





    @Override
    public void create() {
        setCameraAndViewport();
        mousePos = new Vector2();

        backGround = AssetHandler.getData().get("backGround/global background.png");
        forGround = AssetHandler.getData().get("backGround/global forground.png");

        backButton = new Button("button/back.png", 0, Main.HEIGHT -50, 50, 50);
        chatButton = new Button("button/yellow.png", "button/green.png", 10, 450, "Chat", "fonts/Arial 24.fnt");
        scoreBoardButton = new Button("button/yellow.png", "button/green.png", 10, 400, "Score Board", "fonts/Arial 24.fnt");
        chatButton.setActive(true);

        allAccounts = new ArrayList<SavingObject>();
        onlineAccounts = new HashSet<String>();

        playBackGroundMusic("music/login.mp3");
    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        allAccounts.clear();
        Client.sendCommand("get accounts");
        Type type = new TypeToken<ArrayList<SavingObject>>() {}.getType();
        allAccounts.addAll(Client.getData(type, ArrayList.class));

        onlineAccounts.clear();
        Client.sendCommand("get online users");
        type = new TypeToken<HashSet<String>>() {} .getType();
        onlineAccounts.addAll(Client.getData(type, HashSet.class));

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
                else if (scoreBoardButton.contains(mousePos) || chatButton.contains(mousePos)) {
                    scoreBoardButton.setActive(scoreBoardButton.contains(mousePos));
                    chatButton.setActive(chatButton.contains(mousePos));
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
        chatButton.draw(batch);
        scoreBoardButton.draw(batch);

        batch.begin();
        int y = 800;
        BitmapFont font = new BitmapFont();
        for (SavingObject account : allAccounts) {
            if (account.getUsername().equals(Account.getCurrentAccount().getUsername())) continue;
            y -= 100;
            if (onlineAccounts.contains(account.getUsername()))
                font.draw(batch, account.getUsername() + ".:online:.", 300, y);
            else
                font.draw(batch, account.getUsername() + ".:offline:.", 300, y);
        }
        batch.end();


    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
