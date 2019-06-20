package graphic.main;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
    private Texture deActivePic;
    private Texture activePic;
    private Sound soundEffect;
    private String text;
    private BitmapFont font;
    private boolean isActive;
    private Rectangle rectangle;


    public Button(String picPath, float x, float y) {
        Texture texture = AssetHandler.getData().get(picPath);
        this.deActivePic = texture;
        this.activePic = texture;
        this.soundEffect = AssetHandler.getData().get("sfx/click.mp3");
        this.text = "";
        this.font = new BitmapFont();
        this.isActive = false;
        this.rectangle = new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }

    public Button(String picPath, float x, float y, float width, float height) {
        this(picPath, x, y);
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public Button(String deActivePic, String activePic, float x, float y) {
        this(deActivePic, x, y);
        this.activePic = AssetHandler.getData().get(activePic);
    }

    public Button(String deActivePic, String activePic, float x, float y, float width, float height) {
        this(deActivePic, x, y, width, height);
        this.activePic = AssetHandler.getData().get(activePic);
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
            batch.draw(activePic, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        else
            batch.draw(deActivePic, rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        if (!text.equals("")) {
            GlyphLayout glyphLayout = new GlyphLayout();
            glyphLayout.setText(font, text);
            font.draw(batch, text, rectangle.x + (rectangle.width - glyphLayout.width) / 2,
                    rectangle.y + rectangle.height - (rectangle.height - glyphLayout.height) / 2);
        }
        batch.end();

    }

    public boolean contains(Vector2 vec) {
        return rectangle.contains(vec);
    }

}

