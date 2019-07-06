
package graphic.screen.gameMenuScreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import graphic.screen.BattleScreen;
import graphic.screen.Screen;
import graphic.screen.ScreenManager;
import model.game.Deck;

import java.util.ArrayList;

public class SecondCustomMenuScreen2 extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic;
    private Button exitButton;
    private Vector2 mousePos;
    private Button[] decksButtons;
    private ArrayList<Deck> decks = new ArrayList<Deck>();
    private int numberOFDecks = 3;
    @Override
    public void create() {
        addDecks();
        setCameraAndViewport();
        shapeRenderer = new ShapeRenderer();
        decksButtons = new Button[numberOFDecks + 1];
        backGroundPic = AssetHandler.getData().get("backGround/secondCustomMenu.jpg");
        mousePos = new Vector2();


        String font = "fonts/Arial 36.fnt";
        createDecks();
        exitButton = new Button("button/exit.png", Main.WIDTH - 200, Main.HEIGHT - 200);
        createBackGroundMusic();        mousePos = new Vector2();

    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);



        exitButton.setActive(exitButton.contains(mousePos));
        updateDecks();
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
                for(int i = 0; i < numberOFDecks; i++) {
                    if (decksButtons[i].isActive()) {
                        Datas.getDatas().makeKillHeroCustom(i);
                        ScreenManager.setScreen(new BattleScreen());

                    }
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
        drawDecks(batch);
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
    private void createDecks() {
        for(int i = 0; i < numberOFDecks; i++) {
            float x = i*400;
            float y = 250;
            decksButtons[i] = new Button("button/decks/deActiveDeck.png","button/decks/activeDeck.png" , x, y, decks.get(i).getName());
            decksButtons[i].setActive(decksButtons[i].contains(mousePos));

        }
    }
    private void drawDecks(SpriteBatch batch) {
        for(int i = 0; i < numberOFDecks; i++)
            decksButtons[i].draw(batch);
    }
    private void updateDecks() {
        for(int i = 0; i < numberOFDecks; i++) {
            decksButtons[i].setActive(decksButtons[i].contains(mousePos));
        }
    }
    private void addDecks() {
        Datas.getDatas().setCustomDecks();
        decks.addAll(Datas.getDatas().getCustomDecks());
    }
}

