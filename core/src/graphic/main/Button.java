package graphic.main;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Button {
    private Texture deActivePic;
    private Texture activePic;
    private Sound soundEffect;
    private boolean isActive;
    private Rectangle rectangle;

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y, float width, float height) {
        this.deActivePic = AssetHandler.getData().get(deActivePic);
        this.activePic = AssetHandler.getData().get(activePic);
        this.soundEffect = AssetHandler.getData().get(soundEffect);
        this.isActive = false;
        this.rectangle = new Rectangle(x, y, width, height);
    }

    public Button(String deActivePic, String activePic, String soundEffect, float x, float y) {
        float width = AssetHandler.getData().get(deActivePic, Texture.class).getWidth();
        float height = AssetHandler.getData().get(deActivePic, Texture.class).getHeight();
        this.deActivePic = AssetHandler.getData().get(deActivePic);
        this.activePic = AssetHandler.getData().get(activePic);
        this.soundEffect = AssetHandler.getData().get(soundEffect);
        this.isActive = false;
        this.rectangle = new Rectangle(x, y, width, height);
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
        batch.end();
    }

    public boolean contains(Vector2 vec) {
        return rectangle.contains(vec);
    }

}

