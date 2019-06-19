package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import graphic.main.AssetHandler;
import graphic.main.Gif;
import graphic.main.Main;

import java.awt.*;

public class CardTexture {
    private static BitmapFont arial16;
    private static BitmapFont arial24;
    private static GlyphLayout glyphLayout;

    static {
        arial16 = new BitmapFont(AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getData() ,AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getRegions(), true);
        arial24 = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData() ,AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);
        arial24.setColor(Main.toColor(new Color(764765576)));
        arial16.setColor(Main.toColor(new Color(0x949592)));
        glyphLayout = new GlyphLayout();
    }

    private Texture activePic;
    private Texture deActivePic;
    private String name;
    private String info;
    private int attackPoint;
    private int healthPoint;
    private Gif gif;
    private Gif activeGif;
    private boolean isActive = false;

    public CardTexture(String name, String info, int attackPoint, int healthPoint, String gifPath) {
        Animation animation = new Animation<TextureRegion>(1 / 20f, new TextureAtlas(gifPath).findRegions("breathing"), Animation.PlayMode.LOOP);
        gif = new Gif(animation);
        animation = new Animation<TextureRegion>(1 / 20f, new TextureAtlas(gifPath).findRegions("attack"), Animation.PlayMode.LOOP);
        activeGif = new Gif(animation);
        activePic = AssetHandler.getData().get("Card/backGround/active.png");
        deActivePic = AssetHandler.getData().get("Card/backGround/deActive.png");
        this.name = name;
        this.info = info;
        this.attackPoint = attackPoint;
        this.healthPoint = healthPoint;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.begin();
        if (isActive)
            batch.draw(activePic, x, y);
        else
            batch.draw(deActivePic, x, y);
        glyphLayout.setText(arial24, name);
        arial24.draw(batch, name, x + (250 - glyphLayout.width) / 2, y + 100 - (25 - glyphLayout.height) / 2);
        arial16.draw(batch, info, x + 25, y + 70, 200, 40, true);
        glyphLayout.setText(arial24, String.valueOf(attackPoint));
        arial24.setColor(Main.toColor(new Color(0xFFFC00)));
        arial24.draw(batch, String.valueOf(attackPoint), 37 + x + (40 - glyphLayout.width) / 2, y + 150 - (40 - glyphLayout.height) / 2);
        glyphLayout.setText(arial24, String.valueOf(healthPoint));
        arial24.setColor(Main.toColor(new Color(0xFF0001)));
        arial24.draw(batch, String.valueOf(healthPoint), 170 + x + (40 - glyphLayout.width) / 2, y + 150 - (40 - glyphLayout.height) / 2);
        arial24.setColor(Main.toColor(new Color(764765576)));
        batch.end();
        if (isActive && !activeGif.isFinished())
            activeGif.draw(batch, x + 50, y + 150, 150, 150);
        else
            gif.draw(batch, x + 50, y + 150, 150, 150);
    }

    public String getName() {
        return name;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
        if (!isActive)
            activeGif.setTime();
    }
}
