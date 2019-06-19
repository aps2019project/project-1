package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.CardTexture;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.cards.Card;
import model.cards.Minion;

import java.awt.*;
import java.util.ArrayList;

public class TestScreen extends Screen {

    private Vector2 mousePos;
    private Texture backGround;
    private Texture middleGround;
    private Texture forGround;
    private Button heroButton;
    private Button minionButton;
    private Button spellButton;
    private Button itemButton;
    private Button sellButton;
    private Button buyButton;
    private ArrayList<Minion> allCards;
    private ArrayList<CardTexture> cardTextures;
    private int startListFrom = 0;
    private ArrayList<MoveAnimation> snowAnimation;


    @Override
    public void create() {
        setCameraAndViewport();
        backGround = AssetHandler.getData().get("backGround/shop1.png");
        middleGround = AssetHandler.getData().get("backGround/shop2.png");
        forGround = AssetHandler.getData().get("backGround/shop3.png");
        mousePos = new Vector2();
        allCards = Card.getCards().getAllMinions();
        playBackGroundMusic("music/shop.mp3");

        createSnowAnimation();
        createButtons();

        cardTextures = new ArrayList<CardTexture>();



    }

    @Override
    public void update() {

        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);


        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.RIGHT) {
                    if (startListFrom + 8 < allCards.size())
                        startListFrom += 8;
                }
                else if (keycode == Input.Keys.LEFT) {
                    if (startListFrom - 8 >= 0)
                        startListFrom -= 8;

                } else if (keycode == Input.Keys.PAGE_DOWN) {
                    setMusicVolume(false);
                } else if (keycode == Input.Keys.PAGE_UP) {
                    setMusicVolume(true);
                }
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
                if (heroButton.contains(mousePos) || minionButton.contains(mousePos) || spellButton.contains(mousePos) || itemButton.contains(mousePos)) {
                    heroButton.setActive(heroButton.contains(mousePos));
                    minionButton.setActive(minionButton.contains(mousePos));
                    spellButton.setActive(spellButton.contains(mousePos));
                    itemButton.setActive(itemButton.contains(mousePos));
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

        drawBackGround(batch);

        heroButton.draw(batch);
        minionButton.draw(batch);
        spellButton.draw(batch);
        itemButton.draw(batch);


    }

    @Override
    public void dispose() {
        music.stop();
        music.dispose();

    }

    private void createSnowAnimation() {
        snowAnimation = new ArrayList<MoveAnimation>();
        for (int i = 0; i < 100; ++i) {
            int xStart = (int) (400 + 1200 * Math.random());
            int xEnd = (int) (xStart - 150 + 300 * Math.random());
            int random = (int) (Math.random() * 5);
            if (random > 3)
                snowAnimation.add(new MoveAnimation("animation/snow2.png", xStart, 900, xEnd, 0, MoveType.SIMPLE, true));
            else
                snowAnimation.add(new MoveAnimation("animation/snow.png", xStart, 900, xEnd, 0, MoveType.SIMPLE, true));
            snowAnimation.get(i).setSpeed((float) (0.2f + Math.random()));
        }
    }

    private void createButtons() {
        BitmapFont font = AssetHandler.getData().get("fonts/Arial 24.fnt");
        font.setColor(Main.toColor(new Color(0xFF7E05)));
        heroButton = new Button("button/shop left.png", "button/shop left active.png", 300, 700, "Hero", font);
        minionButton = new Button("button/shop middle.png", "button/shop middle active.png", 500, 700, "Minion", font);
        spellButton = new Button("button/shop middle.png", "button/shop middle active.png", 700, 700, "Spell", font);
        itemButton = new Button("button/shop right.png", "button/shop right active.png", 900, 700, "Item", font);
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(middleGround, 0, 0);
        batch.end();
        for (MoveAnimation animation: snowAnimation)
            animation.draw(batch);
        batch.begin();
        batch.draw(forGround, 0, 0);
        batch.end();
    }
}
