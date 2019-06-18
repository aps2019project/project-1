package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import graphic.main.AssetHandler;
import graphic.main.Gif;
import graphic.main.Main;

import javax.xml.soap.Text;
import java.awt.*;

public class CardTexture {

    private Texture activePic;
    private Texture deActivePic;
    private BitmapFont arial16;
    private BitmapFont arial24;
    private String name;
    private String info;
    private int attackPoint;
    private int healthPoint;
    private GlyphLayout glyphLayout;
    private Gif gif;

    public CardTexture(String name, String info, int attackPoint, int healthPoint, String gifPath) {
        Animation animation = new Animation<TextureRegion>(1 / 20f, new TextureAtlas(gifPath).findRegions("breathing"), Animation.PlayMode.LOOP);
        gif = new Gif(animation);
        glyphLayout = new GlyphLayout();
        activePic = AssetHandler.getData().get("Card/backGround/active.png");
        deActivePic = AssetHandler.getData().get("Card/backGround/deActive.png");
        arial16 = AssetHandler.getData().get("fonts/Arial 16.fnt");
        arial24 = AssetHandler.getData().get("fonts/Arial 24.fnt");
        arial24.setColor(Main.toColor(new Color(764765576)));
        arial16.setColor(Main.toColor(new Color(0x949592)));
        this.name = name;
        this.info = info;
        this.attackPoint = attackPoint;
        this.healthPoint = healthPoint;
    }

    public void draw(SpriteBatch batch, float x, float y) {
        batch.begin();
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
        gif.draw(batch, x + 50, y + 150, 150, 150);
    }

}
