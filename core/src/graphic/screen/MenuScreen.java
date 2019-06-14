package graphic.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import graphic.Others.MoveAnimation;
import graphic.Others.MoveType;
import graphic.main.AssetHandler;
import graphic.main.Main;

import java.util.ArrayList;

public class MenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic1;
    private Texture backGroundPic2;
    private ArrayList<MoveAnimation> lanternAnimation;


    @Override
    public void create() {
        setCameraAndVeiwport();
        lanternAnimation = new ArrayList<MoveAnimation>();
        for (int i = 0; i < 10; ++i) {
            int xStart = 700 + (int) (200 * Math.random());
            int yStart = 400 + (int) (100 * Math.random());
            int xEnd = 1700, yEnd = 1000;
            int lanternType = (int) (3 * Math.random() + 1);
            lanternAnimation.add(new MoveAnimation("lantern_large_" + lanternType + ".png", xStart, yStart, xEnd, yEnd, MoveType.RANDOM, false));
        }
        shapeRenderer = new ShapeRenderer();
        backGroundPic1 = AssetHandler.getData().get("backGround/menu1.png");
        backGroundPic2 = AssetHandler.getData().get("backGround/menu2.png");
        createBackGroundMusic();
    }

    private void createBackGroundMusic() {
        music = AssetHandler.getData().get("music/menu.mp3");
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
    }

    @Override
    public void update() {
        camera.update();

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setProjectionMatrix(camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(backGroundPic1, 0, 0);
        batch.draw(backGroundPic2, Main.WIDTH - backGroundPic2.getWidth(), 0);
        batch.end();

        for (MoveAnimation animation: lanternAnimation) {
            animation.draw(batch, 20);
        }




    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
