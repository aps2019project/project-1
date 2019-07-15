package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import graphic.main.AssetHandler;
import graphic.main.Gif;


public class SpellAnimation extends CardAnimation{

    private Gif gif;
    private Texture texture;
    private boolean isGif = false;

    public SpellAnimation(String gifPath) {
        TextureAtlas textureAtlas = AssetHandler.getData().get(gifPath, TextureAtlas.class);
        Animation animation = new Animation<TextureRegion>(SPEED, textureAtlas.findRegions("gif"));
        gif = new Gif(animation);
        texture = textureAtlas.getTextures().first();
    }

    public SpellAnimation(String gifPath, String picPath) {
        this(gifPath);
        this.texture = AssetHandler.getData().get(picPath, Texture.class);
    }

    public SpellAnimation(String gifPath, float defaultX, float defaultY) {
        this(gifPath);
        this.x = defaultX;
        this.y = defaultY;
    }

    public SpellAnimation(String gifPath, String picPath, float defaultX, float defaultY) {
        this(gifPath, picPath);
        this.x = defaultX;
        this.y = defaultY;
    }

    public void updateLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void updateLocation(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
    }

    public void draw(SpriteBatch batch, float x, float y, float width, float height) {
        if (isGif) {
            gif.draw(batch, x, y, width, height);
            isGif = !gif.isFinished();
        }
        else {
            batch.begin();
            batch.draw(texture, x, y, width, height);
            batch.end();
        }
    }

    public void draw(SpriteBatch batch, float x, float y) {
        if (isGif)
            this.draw(batch, x, y, gif.getWidth(), gif.getHeight());
        else
            this.draw(batch, x, y, texture.getWidth(), texture.getHeight());    }

    public void draw(SpriteBatch batch) {
        this.draw(batch, x, y);
    }

    public void setAction() {
        this.isGif = true;
        this.gif.setTime();
    }

    public Gif getGif() {
        return gif;
    }
}
