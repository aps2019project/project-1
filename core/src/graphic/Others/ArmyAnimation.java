package graphic.Others;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import graphic.main.Gif;

import javax.xml.soap.Text;

public class ArmyAnimation {

    private final static float SPEED = 1 / 20f;

    private float x, y;
    private Gif currentGif;
    private Gif normalGif;
    private Gif attackGif;
    private Gif deathGif;
    private Gif runGif;
    private Sound attackSound;
    private Sound deathSound;
    private Sound runSound;
    private MoveAnimation runAnimation;

    public ArmyAnimation(String dataPath) {
        Animation animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("attack"), Animation.PlayMode.LOOP);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        attackGif = new Gif(animation);
        animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("breathing"));
        normalGif = new Gif(animation);
        animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("death"));
        deathGif = new Gif(animation);
        animation = new Animation<TextureRegion>(SPEED, new TextureAtlas(dataPath).findRegions("run"), Animation.PlayMode.LOOP);
        runGif = new Gif(animation);
        currentGif = attackGif;
        runAnimation = null;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        if (runAnimation != null && !runAnimation.isFinished()) {
            runAnimation.draw(batch, 5, width, height);
            return;
        }
        else if (runAnimation != null)
            runAnimation = null;

        if (currentGif != normalGif && currentGif.isFinished()) {
            currentGif = normalGif;
        }
        if (currentGif.isFinished())
            normalGif.setTime();
        currentGif.draw(batch, x, y, width, height);
    }



    public void draw(SpriteBatch batch, float x, float y) {
        draw(batch, x, y, 150, 150);
    }

    public void run( float xEnd, float yEnd) {
        runAnimation = new MoveAnimation(runGif, x, y, xEnd, yEnd, MoveType.SIMPLE, false);
        this.x = xEnd;
        this.y = yEnd;
    }

    public void death() {
        currentGif = deathGif;
        deathGif.setTime();
    }

    public void attack() {
        currentGif = attackGif;
        attackGif.setTime();
    }

}
