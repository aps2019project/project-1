package graphic.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import graphic.main.AssetHandler;
import graphic.main.Main;

import java.awt.*;

public class MenuScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGroundPic1;
    private Texture backGroundPic2;


    @Override
    public void create() {
        setCameraAndVeiwport();
        shapeRenderer = new ShapeRenderer();
        backGroundPic1 = AssetHandler.getData().get("backGround/menu1.png");
        backGroundPic2 = AssetHandler.getData().get("backGround/menu2.png");
        music = AssetHandler.getData().get("music/menu.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);
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




    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
