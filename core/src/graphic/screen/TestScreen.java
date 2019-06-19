package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.CardTexture;
import graphic.main.AssetHandler;
import model.cards.Army;
import model.cards.Card;
import model.cards.Hero;
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


    @Override
    public void create() {
        setCameraAndViewport();
        backGround = AssetHandler.getData().get("backGround/shop1.png");
        middleGround = AssetHandler.getData().get("backGround/shop2.png");
        forGround = AssetHandler.getData().get("backGround/shop3.png");
        mousePos = new Vector2();
        allCards = Card.getCards().getAllMinions();
        playBackGroundMusic("music/shop.mp3");

        cardTextures = new ArrayList<CardTexture>();


    }

    @Override
    public void update() {

        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        cardTextures.clear();
        for (int i = 0; i < 8; ++i) {
            Army army = allCards.get(i + startListFrom);
            CardTexture texture;
            texture = new CardTexture(army.getName(), army.getDescription(), army.getAp(), army.getHp(), "Card/Hero/1.atlas");
            cardTextures.add(texture);
        }



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

//        for (int i = 0; i < 4; ++i) {
//            cardTextures.get(i).draw(batch, 10 + 300 * i, 500);
//        }
//        for (int i = 4; i < 8; ++i) {
//            cardTextures.get(i).draw(batch, 10 + 300 * (i - startListFrom), 200);
//
//        }


    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGround, 0, 0);
        batch.draw(middleGround, 0, 0);
        batch.draw(forGround, 0, 0);
        batch.end();
    }

    @Override
    public void dispose() {
        music.stop();
        music.dispose();

    }
}
