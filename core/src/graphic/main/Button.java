package graphic.main;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {

    private Sound soundEffect;
    private String text;
    private BitmapFont font;
    private boolean isActive;
    private Rectangle deActiveRectangle;
    private Rectangle activeRectangle;
    private Sprite activePic;
    private Sprite deActivePic;


    public Button(String picPath, float x, float y) {
        Sprite sprite = new Sprite(AssetHandler.getData().get(picPath, Texture.class));
        this.deActivePic = sprite;
        this.activePic = sprite;
        this.soundEffect = AssetHandler.getData().get("sfx/click.mp3");
        this.text = "";
        this.font = new BitmapFont();
        this.isActive = false;
        this.deActiveRectangle = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        this.activeRectangle = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    public Button(String picPath, float x, float y, float width, float height) {
        this(picPath, x, y);
        this.deActiveRectangle = new Rectangle(x, y, width, height);
        this.activeRectangle = new Rectangle(x, y, width, height);
    }

    public Button(String deActivePic, String activePic, float x, float y) {
        this(deActivePic, x, y);
        this.activePic = new Sprite(AssetHandler.getData().get(activePic, Texture.class));
    }

    public Button(String deActivePic, String activePic, float x, float y, float width, float height) {
        this(deActivePic, x, y, width, height);
        this.activePic = new Sprite(AssetHandler.getData().get(activePic, Texture.class));
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y) {
        this(deActivePic, activePic, x, y);
        this.soundEffect = AssetHandler.getData().get(soundEffect);
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, float width, float height) {
        this(deActivePic, activePic, x, y, width, height);
        this.soundEffect = AssetHandler.getData().get(soundEffect);
    }

    public Button(String pic, float x, float y, String text) {
        this(pic, x, y);
        this.text = text;
    }

    public Button(String pic, float x, float y, float width, float height, String text) {
        this(pic, x, y, width, height);
        this.text = text;
    }

    public Button(String deActivePic, String activePic, float x, float y, String text) {
        this(deActivePic, activePic, x, y);
        this.text = text;
    }

    public Button(String deActivePic, String activePic, float x, float y, float width, float height, String text) {
        this(deActivePic, activePic, x, y, width, height);
        this.text = text;
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, String text) {
        this(deActivePic, activePic, soundEffect, x, y);
        this.text = text;
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, float width, float height, String text) {
        this(deActivePic, activePic, soundEffect, x, y, width, height);
        this.text = text;
    }

    public Button(String pic, float x, float y, String text, String fontPath) {
        this(pic, x, y, text);
        this.font = AssetHandler.getData().get(fontPath);
    }

    public Button(String pic, float x, float y, float width, float height, String text, String fontPath) {
        this(pic, x, y, width, height, text);
        this.font = AssetHandler.getData().get(fontPath);
    }

    public Button(String deActivePic, String activePic, float x, float y, String text, String fontPath) {
        this(deActivePic, activePic, x, y, text);
        this.font = AssetHandler.getData().get(fontPath);
    }

    public Button(String deActivePic, String activePic, float x, float y, float width, float height, String text, String fontPath) {
        this(deActivePic, activePic, x, y, width, height, text);
        this.font = AssetHandler.getData().get(fontPath);
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, String text, String fontPath) {
        this(deActivePic, activePic, soundEffect, x, y, text);
        this.font = AssetHandler.getData().get(fontPath);
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, float width, float height, String text, String fontPath) {
        this(deActivePic, activePic, soundEffect, x, y, width, height, text);
        this.font = AssetHandler.getData().get(fontPath);
    }

    public Button(String pic, float x, float y, String text, BitmapFont font) {
        this(pic, x, y, text);
        this.font = font;
    }

    public Button(String pic, float x, float y, float width, float height, String text, BitmapFont font) {
        this(pic, x, y, width, height, text);
        this.font = font;
    }

    public Button(String deActivePic, String activePic, float x, float y, String text, BitmapFont font) {
        this(deActivePic, activePic, x, y, text);
        this.font = font;
    }

    public Button(String deActivePic, String activePic, float x, float y, float width, float height, String text, BitmapFont font) {
        this(deActivePic, activePic, x, y, width, height, text);
        this.font = font;
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, String text, BitmapFont font) {
        this(deActivePic, activePic, soundEffect, x, y, text);
        this.font = font;
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, float width, float height, String text, BitmapFont font) {
        this(deActivePic, activePic, soundEffect, x, y, width, height, text);
        this.font = font;
    }

    public Button(Sprite deActivePic, Sprite activePic, String soundEffect, float x, float y){
        this.activePic = activePic;
        this.deActivePic = deActivePic;
        this.text = "";
        this.font = new BitmapFont();
        this.isActive = false;
        this.soundEffect = AssetHandler.getData().get(soundEffect);
        this.deActiveRectangle = new Rectangle(x, y, activePic.getWidth(), activePic.getHeight());
        this.activeRectangle = this.deActiveRectangle;
    }

    public Button(String deActivePic, String activePic, String soundEffect, float deActiveX, float deActiveY, float deActiveWidth, float deActiveHeight, float activeX, float activeY, float activeScale) {

        this.activePic = new Sprite(AssetHandler.getData().get(activePic, Texture.class));
        this.deActivePic = new Sprite(AssetHandler.getData().get(deActivePic, Texture.class));
        this.text = "";
        this.font = new BitmapFont();
        this.isActive = false;
        this.soundEffect = AssetHandler.getData().get(soundEffect);
        this.activeRectangle = new Rectangle(activeX, activeY, this.activePic.getWidth()*activeScale, this.activePic.getHeight()*activeScale);
        this.deActiveRectangle = new Rectangle(deActiveX, deActiveY, deActiveWidth, deActiveHeight);
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean isActive) {
        if (isActive && !this.isActive)
            soundEffect.play();
        this.isActive = isActive;
    }

    public void draw(SpriteBatch batch) {
        batch.begin();

        if (isActive)
            batch.draw(activePic, activeRectangle.x, activeRectangle.y, activeRectangle.width, activeRectangle.height);
        else
            batch.draw(deActivePic, deActiveRectangle.x, deActiveRectangle.y, deActiveRectangle.width, deActiveRectangle.height);

        if (!text.equals("")) {
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, text);
            font.draw(batch, text, deActiveRectangle.x + (deActiveRectangle.width - glyphLayout.width) / 2,
                    deActiveRectangle.y + deActiveRectangle.height - (deActiveRectangle.height - glyphLayout.height) / 2);
        }
        batch.end();

    }

    public boolean contains(Vector2 vec) {
        return deActiveRectangle.contains(vec);
    }

    public void setText(String text) {
        this.text = text;
    }
}

