package graphic.screen;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import graphic.main.AssetHandler;
import graphic.main.Main;

public abstract class Screen {

    protected OrthographicCamera camera;
    protected Viewport viewport;
    protected Music music;


    public abstract void create();

    public abstract void update();

    public abstract void render(SpriteBatch batch);

    public abstract void dispose();

    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    protected void setCameraAndViewport() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(Main.WIDTH, Main.HEIGHT, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    protected void setMusicVolume(boolean add) {
        if (add && music.getVolume() < 0.95)
            music.setVolume(music.getVolume() + 0.05f);
        else if ( !add && music.getVolume() > 0.05)
            music.setVolume(music.getVolume() - 0.05f);
    }

    protected  void playBackGroundMusic(String musicPath) {
        music = AssetHandler.getData().get(musicPath);
        music.setVolume(0.5f);
        music.setLooping(true);
        music.play();
    }
}
