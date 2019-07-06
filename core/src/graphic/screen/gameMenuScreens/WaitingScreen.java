package graphic.screen.gameMenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.main.Button;
import graphic.main.Gif;
import graphic.main.Main;
import graphic.screen.BattleScreen;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;
import model.game.GameType;
import network.Client;


public class WaitingScreen extends Screen {

    private Gif loadingGif;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String username = "";
    private String condition = "wait for another";
    private Button cancelButton;
    private Vector2 mousePos;
    private GameType gameType = GameType.KILL_HERO;
    private int numberOfFlags = 0;
    public WaitingScreen(String username) {
        this.username = username;
    }

    @Override
    public void create() {
        setCameraAndViewport();
        loadingGif = new Gif("loading.gif");
        font = new BitmapFont(Gdx.files.internal("fonts/Arial 48B.fnt"));
        glyphLayout = new GlyphLayout();
        cancelButton = new Button("button/red.png", "button/red glow.png", "sfx/click.mp3", 700, 100, "cancel", font);
        mousePos = new Vector2();

    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        String condition = Client.getApplyCondition(this.username);
        this.condition = condition;
        if(this.condition.equals("accepted")) {
            if(gameType == GameType.KILL_HERO)
                Datas.getDatas().makeKillHeroCustom(Client.getAccount(username));
            else if(gameType == GameType.CAPTURE_THE_FLAG)
                Datas.getDatas().makeCaptureTheFlagCustom(Client.getAccount(username));
            else if(gameType == GameType.ROLLUP_FLAGS)
                Datas.getDatas().makeRollUpFlagCustom(Client.getAccount(username), numberOfFlags);
            ScreenManager.setScreen(new BattleScreen());

        }
        cancelButton.setActive(cancelButton.contains(mousePos));
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
                if(cancelButton.isActive()) {
                    Client.cancelApplying(username);
                    ScreenManager.setScreen(new MultiPlayerScreen());
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
        batch.begin();
        glyphLayout.setText(font, condition);
        font.draw(batch, condition, (Main.WIDTH - glyphLayout.width) / 2 - 100, Main.HEIGHT - (Main.HEIGHT - glyphLayout.height) / 2 + 150);
        batch.end();
        loadingGif.draw(batch, Main.WIDTH/2, Main.HEIGHT/2 - 20 + 50);
        cancelButton.draw(batch);
    }

    @Override
    public void dispose() {

    }
}
