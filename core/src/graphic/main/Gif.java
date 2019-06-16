package graphic.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import graphic.Others.GifDecoder;

public class Gif {

    private Animation<TextureRegion> animation;
    private float time = 0;

    public Gif(Animation<TextureRegion> animation) {
        this.animation = animation;
    }

    public Gif(String gifAddress) {
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(gifAddress).read());
    }

    public void draw(SpriteBatch batch, float x, float y) {
        time += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(animation.getKeyFrame(time), x, y);
        batch.end();
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        time += Gdx.graphics.getDeltaTime();
        batch.begin();
        batch.draw(animation.getKeyFrame(time), x, y, width, height);
        batch.end();
    }

    public float getWidth() {
        return animation.getKeyFrame(time).getRegionWidth();
    }

    public float getHeight() {
        return animation.getKeyFrame(time).getRegionHeight();
    }

}
