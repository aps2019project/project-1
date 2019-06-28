package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.CardShowSlot;
import graphic.Others.CardTexture;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;
import model.cards.Hero;
import model.cards.Item;
import model.cards.Minion;
import model.cards.Spell;
import model.other.Account;

import java.awt.*;
import java.util.ArrayList;

public class TestScreen extends Screen {

    private Account account;
    private Texture backGroundPic;
    private Texture middleGroundPic;
    private Texture frontPic;
    private ArrayList<MoveAnimation> waterFallAnimation;
    private ArrayList<Button> allDecksButtons;
    private Button backButton;
    private Button selectAsMainDeckButton;
    private Button createDeckButton;
    private Button addToDeckButton;
    private CardShowSlot cardList;
    private Vector2 mousePos;
    private String selectedCard;


    @Override
    public void create() {
        setCameraAndViewport();
        playBackGroundMusic("music/collection.mp3");
        backGroundPic = AssetHandler.getData().get("backGround/collection1.png");
        middleGroundPic = AssetHandler.getData().get("backGround/collection2.png");
        frontPic = AssetHandler.getData().get("backGround/collection3.png");

        account = Account.getCurrentAccount();

        float buttonHeight = AssetHandler.getData().get("button/deckSlot.png", Texture.class).getHeight();
        allDecksButtons = new ArrayList<Button>();
        for (int i = 0; i < account.getAllDecks().size(); ++i)
            allDecksButtons.add(new Button("button/deckSlot.png", "button/deckSlotGlow.png", 0, Main.HEIGHT - 100 - (i + 1) * buttonHeight));
        mousePos = new Vector2();
        createWaterFallAnimation();

        cardList = new CardShowSlot(Account.getCurrentAccount().getCollection(), 670, 140, 3, 2);
        selectedCard = "";
    }

    @Override
    public void update() {
        camera.update();
        mousePos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.PAGE_DOWN)
                    setMusicVolume(false);
                if (keycode == Input.Keys.PAGE_UP)
                    setMusicVolume(true);
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
                for (Button deckButton: allDecksButtons)
                    deckButton.setActive(deckButton.contains(mousePos));
                cardList.update(mousePos);
                selectedCard = cardList.getSelectedCard(mousePos);
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
        for (Button deckButton: allDecksButtons)
            deckButton.draw(batch);
        cardList.draw(batch);
    }

    @Override
    public void dispose() {
        music.dispose();
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGroundPic, 0 ,0);
        batch.draw(middleGroundPic, 0, 0);
        batch.end();
        for (MoveAnimation animation: waterFallAnimation)
            animation.draw(batch);
        batch.begin();
        batch.draw(frontPic, Main.WIDTH - frontPic.getWidth(), 0);
        batch.end();
    }

    private void createWaterFallAnimation() {
        waterFallAnimation = new ArrayList<MoveAnimation>();
        createWaterFallType6();
        createWaterFallType5();
        createWaterFallType1(10, 390, 360, 475, 35, 2.5, 5, Math.random(), 200);
        createWaterFallType3(20, 345, 345, 475 + Math.random() * 35, 780, 210);
        createWaterFallType1(20, 390, 360, 580, 50, 5, 10, Math.random(), 230);
        createWaterFallType4(10, 670, 10, 1, 250);
        createWaterFallType4(10, 700, 10, 1, 260);
        createWaterFallType4(20, 760, 40, 1, 270);
        createWaterFallType4(5, 860, 10, 5, 290);
        createWaterFallType4(5, 890, 10, 3, 295);
        createWaterFallType3(10, 355, 350, 915 - Math.random() * 40, 810, 300);
        createWaterFallType2();
        createWaterFallType1(10, 475, 455, 610, 25, 2.5, Math.random(), 5, 320);
    }

    private void createWaterFallType6() {
        for (int i = 0; i < 100; ++i) {
            float yStart = 350;
            float yEnd = 0;
            float xStart = (float) (568 + Math.random() * 50);
            float xEnd = (float) (xStart - 10 + 20 * Math.random());
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType5() {
        for (int i = 0; i < 100; ++i) {
            float yStart = 350;
            float yEnd = 100;
            float xStart = (float) (780 + Math.random() * 40);
            float xEnd = (float) (xStart - 5 + 10 * Math.random()) + 10;
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + 100).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType4(int i2, int i3, int i4, int i5, int i6) {
        for (int i = 0; i < i2; ++i) {
            float yStart = 390;
            float yEnd = 360;
            float xStart = (float) (i3 + Math.random() * i4);
            float xEnd = xStart + i5;
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + i6).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType3(int i2, int i3, int i4, double v, int i5, int i6) {
        for (int i = 0; i < i2; ++i) {
            float xStart = (float) (v);
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, (float) i3, (float) i5, (float) i4, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + i6).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType2() {
        for (int i = 0; i < 10; ++i) {
            float yStart = 480;
            float yEnd = 440;
            float xStart = (float) (570 + Math.random() * 25);
            float xEnd = (float) (575 + Math.random() * 10);
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + 310).setSpeed((float) (0.4 + Math.random()));
        }
    }

    private void createWaterFallType1(int i2, int i3, int i4, int i5, int i6, double v, double random, double i7, int i8) {
        for (int i = 0; i < i2; ++i) {
            float xStart = (float) (i5 + Math.random() * i6);
            float xEnd = (float) (xStart - v + random * i7);
            waterFallAnimation.add(new MoveAnimation("animation/waterFall.png", xStart, (float) i3, xEnd, (float) i4, MoveType.SIMPLE, true));
            waterFallAnimation.get(i + i8).setSpeed((float) (0.4 + Math.random()));
        }
    }
}
