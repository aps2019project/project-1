package graphic.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import graphic.Others.GifDecoder;

import javax.xml.soap.Text;

public class Gif {

    private Animation animation;
    private float time = 0;
    private boolean flipped = false;

    public Gif(Animation animation) {
        this.animation = animation;
    }

    public Gif(String gifAddress) {
        animation = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal(gifAddress).read());
    }

    public void draw(SpriteBatch batch, float x, float y) {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion current = (TextureRegion) animation.getKeyFrame(time);
        if( flipped && !current.isFlipX())
            current.flip(true, false);
        batch.begin();
        batch.draw(current, x, y);
        batch.end();
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion current = (TextureRegion) animation.getKeyFrame(time);
        if( flipped && !current.isFlipX())
            current.flip(true, false);
        batch.begin();
        batch.draw(current, x, y, width, height);
        batch.end();
    }

    public boolean isFinished() {
        return animation.isAnimationFinished(time);
    }

    public float getWidth() {
        return ((TextureRegion)animation.getKeyFrame(time)).getRegionWidth();
    }

    public float getHeight() {
        return ((TextureRegion)animation.getKeyFrame(time)).getRegionHeight();
    }

    public void setTime() {
        time = 0;
    }

    public void flip() {
        flipped = true;
    }

    public void setSpeed(float speed){
        this.animation.setFrameDuration(speed);
    }

}
