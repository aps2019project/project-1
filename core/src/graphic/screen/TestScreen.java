package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.CardTexture;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import model.cards.Card;
import model.cards.Minion;

import java.util.ArrayList;

public class TestScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Vector2 mousePos;
    private Texture backGround;
    private Texture middleGround;
    private Texture forGround;
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

    @Override
    public void dispose() {
        music.stop();
        music.dispose();

    }
}
