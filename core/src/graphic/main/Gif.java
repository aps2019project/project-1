package graphic.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import graphic.Others.GifDecoder;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.xml.soap.Text;

public class Gif {

    private Animation animation;
    private float time = 0;
    private boolean flipped = false;
    private float x, y;
    private GifType type;


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

    public void draw(SpriteBatch batch){
        this.draw(batch, x, y, 150, 150);
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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getTime() {
        return time;
    }

    public Animation getAnimation() {
        return animation;
    }

    public GifType getType() {
        return type;
    }

    public void setType(GifType type) {
        this.type = type;
    }

    public void setTime(float time){
        this.time = time;
    }
}
