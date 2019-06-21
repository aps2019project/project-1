package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;

import java.awt.*;
import java.util.ArrayList;

public class MenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Texture backGroundPic1;
    private Texture backGroundPic2;
    private Texture brand;
    private Button gameMakerButton;
    private Button shopButton;
    private Button collectionButton;
    private Button customCardButton;
    private Button profileButton;
    private Button exitButton;
    private Vector2 mousePos;
    private ArrayList<MoveAnimation> lanternAnimation;

    @Override
    public void create() {
        setCameraAndViewport();
        createLanternsAnimation();
        shapeRenderer = new ShapeRenderer();
        backGroundPic1 = AssetHandler.getData().get("backGround/menu1.png");
        backGroundPic2 = AssetHandler.getData().get("backGround/menu2.png");

        BitmapFont font = AssetHandler.getData().get("fonts/Arial 24.fnt");
        font.setColor(Main.toColor(new Color(0xFFF6FE)));
        customCardButton =  new Button("button/menuButton.png","button/menuButtonActive.png", 250, 100, 300, 50, "Create Card", font);
        gameMakerButton = new Button("button/menuButton.png", "button/menuButtonActive.png", 200, 200, 300, 50, "Play Game", font);
        shopButton =  new Button("button/menuButton.png", "button/menuButtonActive.png",150, 300, 300, 50, "Enter Shop", font);
        collectionButton =  new Button("button/menuButton.png","button/menuButtonActive.png", 100, 400, 300, 50, "Enter Collection", font);
        exitButton = new Button("button/exit.png", Main.WIDTH - 200, Main.HEIGHT - 200);
        profileButton = new Button("button/profile.png", Main.WIDTH - 200, Main.HEIGHT - 400);
        mousePos = new Vector2();
        playBackGroundMusic("music/menu.mp3");
        brand = AssetHandler.getData().get("backGround/brand.png");
    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        collectionButton.setActive(collectionButton.contains(mousePos));
        shopButton.setActive(shopButton.contains(mousePos));
        gameMakerButton.setActive(gameMakerButton.contains(mousePos));
        customCardButton.setActive(customCardButton.contains(mousePos));
        profileButton.setActive(profileButton.contains(mousePos));
        exitButton.setActive(exitButton.contains(mousePos));


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
                if (button != Input.Buttons.LEFT)
                    return false;
                if (gameMakerButton.isActive())
                    ScreenManager.setScreen(new BattleScreen());
                if (shopButton.isActive())
                    ScreenManager.setScreen(new ShopScreen());
                if (collectionButton.isActive())
                    ScreenManager.setScreen(new TestScreen());

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
        gameMakerButton.draw(batch);
        shopButton.draw(batch);
        collectionButton.draw(batch);
        customCardButton.draw(batch);
        profileButton.draw(batch);
        exitButton.draw(batch);
    }

    private void showLanternAnimation(SpriteBatch batch) {
        for (MoveAnimation animation: lanternAnimation) {
            animation.draw(batch);
        }
    }

    @Override
    public void dispose() {
        music.stop();
        music.dispose();
    }

    private void createLanternsAnimation() {
        lanternAnimation = new ArrayList<MoveAnimation>();
        for (int i = 0; i < 40; ++i) {
            float xStart = 100 + (int) (900 * Math.random());
            float yStart = (750 - xStart/6f) + (int) (100 * Math.random());
            float xEnd = 900 + (float)(Math.random() * 700), yEnd = 900;
            int lanternType = (int) (5 * Math.random() + 1);
            if (lanternType < 3)
                lanternType = 1;
            else if (lanternType < 5)
                lanternType = 2;
            else
                lanternType = 3;
            lanternAnimation.add(new MoveAnimation("lantern_large_" + lanternType + ".png", xStart, yStart, xEnd, yEnd, MoveType.SIMPLE, true));
            lanternAnimation.get(i).setSpeed((float)( 1 + (Math.random() + 0.5f) - lanternType / 2));
        }
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGroundPic1, 0, 0);
        batch.draw(backGroundPic2, Main.WIDTH - backGroundPic2.getWidth(), 0);
        batch.end();
        showLanternAnimation(batch);
        batch.begin();
        batch.draw(brand,(Main.WIDTH - brand.getWidth()) / 2, (Main.HEIGHT - brand.getHeight()) / 2);
        batch.end();
    }
}
