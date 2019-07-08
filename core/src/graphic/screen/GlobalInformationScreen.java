package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.google.gson.reflect.TypeToken;
import connection.Client;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
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
    private Texture offlineSlot;
    private Texture onlineSlot;
    private BitmapFont accountFont;
    private GlyphLayout glyphLayout;




    @Override
    public void create() {
        setCameraAndViewport();
        mousePos = new Vector2();

        backGround = AssetHandler.getData().get("backGround/global background.png");
        forGround = AssetHandler.getData().get("backGround/global forground.png");
        onlineSlot = AssetHandler.getData().get("slots/online account.png");
        offlineSlot = AssetHandler.getData().get("slots/offline account.png");

        accountFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData(), AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);

        backButton = new Button("button/back.png", 0, Main.HEIGHT - 50, 50, 50);
        chatButton = new Button("button/yellow.png", "button/green.png", 10, 450, 200, 70,"Chat", "fonts/Arial 24.fnt");
        scoreBoardButton = new Button("button/yellow.png", "button/green.png", 10, 400, 200, 70, "Score Board", "fonts/Arial 24.fnt");
        scoreBoardButton.setActive(true);

        glyphLayout = new GlyphLayout();
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
        drawBackGround(batch);
        drawButtons(batch);
        if (scoreBoardButton.isActive())
            drawAccounts(batch);
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    private void drawButtons(SpriteBatch batch) {
        backButton.draw(batch);
        chatButton.draw(batch);
        scoreBoardButton.draw(batch);
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(forGround, Main.WIDTH - forGround.getWidth(), 0);
        batch.end();
    }

    private void drawAccounts(SpriteBatch batch) {
        batch.begin();
        float yStart = Main.HEIGHT - (Main.HEIGHT - allAccounts.size() * 80) / 2;
        for (SavingObject account: allAccounts) {
            if (onlineAccounts.contains(account.getUsername()))
                batch.draw(onlineSlot, (Main.WIDTH - onlineSlot.getWidth()) / 2, yStart - 70);
            else
                batch.draw(offlineSlot, (Main.WIDTH - onlineSlot.getWidth()) / 2, yStart - 70);
            glyphLayout.setText(accountFont, account.getUsername());
            accountFont.draw(batch, account.getUsername(), (Main.WIDTH - glyphLayout.width) / 2 + 15, yStart - (71 - glyphLayout.height) / 2);
            yStart -= 80;
        }
        batch.end();
    }
}
