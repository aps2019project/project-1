package graphic.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import graphic.main.AssetHandler;
import graphic.main.Main;

import java.awt.*;

public class BattleScreen extends Screen {

    private ShapeRenderer shapeRenderer;
    private Music music;
    private Texture backGround;


    @Override
    public void create() {
        setCameraAndVeiwport();
        shapeRenderer = new ShapeRenderer();
        backGround = AssetHandler.getData().get("backGround/battle background.png");
        music = AssetHandler.getData().get("music/battle.mp3");
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
        batch.draw(backGround, 0, 0);
        batch.end();




    }

    @Override
    public void dispose() {
        music.dispose();
    }
}
