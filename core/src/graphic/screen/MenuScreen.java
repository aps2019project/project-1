package graphic.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import graphic.main.Button;
import graphic.main.Main;

import java.util.ArrayList;

public class MenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic1;
    private Texture backGroundPic2;
    private Button gameMakerButton;
    private Button shopButton;
    private Button collectionButton;
    private Vector2 mousePos;
    private ArrayList<MoveAnimation> lanternAnimation;

    @Override
    public void create() {
        setCameraAndVeiwport();
        createLanternsAnimation();
        shapeRenderer = new ShapeRenderer();
        backGroundPic1 = AssetHandler.getData().get("backGround/menu1.png");
        backGroundPic2 = AssetHandler.getData().get("backGround/menu2.png");

        String font = "fonts/Arial 24.fnt";
        gameMakerButton = new Button("button/menuButton.png", 200, 100, 300, 50, "Play Game", font);
        shopButton =  new Button("button/menuButton.png", 150, 200, 300, 50, "Enter Shop", font);
        collectionButton =  new Button("button/menuButton.png", 100, 300, 300, 50, "Enter Collection", font);
        mousePos = new Vector2();
        createBackGroundMusic();
    }

    @Override
    public void update() {
        camera.update();
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        mousePos = viewport.unproject(mousePos);

        collectionButton.setActive(collectionButton.contains(mousePos));
        shopButton.setActive(shopButton.contains(mousePos));
        gameMakerButton.setActive(gameMakerButton.contains(mousePos));

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
                if (gameMakerButton.isActive())
                    ScreenManager.setScreen(new BattleScreen());
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
    }

    @Override
    public void dispose() {
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

    private void createBackGroundMusic() {
        music = AssetHandler.getData().get("music/menu.mp3");
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
    }

    private void drawBackGround(SpriteBatch batch) {
        batch.begin();
        batch.draw(backGroundPic1, 0, 0);
        batch.draw(backGroundPic2, Main.WIDTH - backGroundPic2.getWidth(), 0);
        batch.end();
    }
}
