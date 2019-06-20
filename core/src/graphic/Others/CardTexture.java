package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import graphic.main.AssetHandler;
import graphic.main.Gif;
import graphic.main.Main;
import model.cards.CardType;

import java.awt.*;

import static model.cards.CardType.*;

public class CardTexture {
    private static BitmapFont arial16;
    private static BitmapFont arial24;
    private static BitmapFont priceFont;
    private static GlyphLayout glyphLayout;

    static {
        arial16 = new BitmapFont(AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getData() ,AssetHandler.getData().get("fonts/Arial 16.fnt", BitmapFont.class).getRegions(), true);
        arial24 = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData() ,AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);
        priceFont = new BitmapFont(AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getData() ,AssetHandler.getData().get("fonts/Arial 24.fnt", BitmapFont.class).getRegions(), true);
        arial24.setColor(Main.toColor(new Color(764765576)));
        arial16.setColor(Main.toColor(new Color(0x949592)));
        glyphLayout = new GlyphLayout();
    }

    private Texture activePic;
    private Texture deActivePic;
    private Texture pricePic;
    private String name;
    private String info;
    private int attackPoint;
    private int healthPoint;
    private Gif gif;
    private Gif activeGif;
    private boolean isActive = false;
    private CardType type;
    private int price;

    public CardTexture(String name, String info, int price, int attackPoint, int healthPoint, String gifPath) {
        this.name = name;
        this.info = info;
        this.price = price;
        this.type = MINION;
        this.pricePic = AssetHandler.getData().get("Card/backGround/price tag.png");
        createHeroAndMinionCard(attackPoint, healthPoint, gifPath);
    }

    public CardTexture(String name, String info, int price, String gifPath) {
        this.name = name;
        this.info = info;
        this.price = price;
        this.type = SPELL;
        this.pricePic = AssetHandler.getData().get("Card/backGround/price tag.png");
        createSpellAndItemCard(gifPath);
    }

    private void createSpellAndItemCard(String gifPath) {
        Animation animation = new Animation<TextureRegion>(1 / 20f, AssetHandler.getData().get(gifPath, TextureAtlas.class).findRegions("gif"), Animation.PlayMode.LOOP);
        gif = new Gif(animation);
        animation = new Animation<TextureRegion>(1 / 20f,  AssetHandler.getData().get(gifPath, TextureAtlas.class).findRegions("gif"), Animation.PlayMode.LOOP);
        activeGif = new Gif(animation);
        activePic = AssetHandler.getData().get("Card/backGround/spell active.png");
        deActivePic = AssetHandler.getData().get("Card/backGround/spell deActive.png");
    }

    private void createHeroAndMinionCard(int attackPoint, int healthPoint, String gifPath) {
        Animation animation = new Animation<TextureRegion>(1 / 20f,AssetHandler.getData().get(gifPath, TextureAtlas.class).findRegions("breathing"), Animation.PlayMode.LOOP);
        gif = new Gif(animation);
        animation = new Animation<TextureRegion>(1 / 20f, AssetHandler.getData().get(gifPath, TextureAtlas.class).findRegions("attack"), Animation.PlayMode.LOOP);
        activeGif = new Gif(animation);
        activePic = AssetHandler.getData().get("Card/backGround/hero active.png");
        deActivePic = AssetHandler.getData().get("Card/backGround/hero deActive.png");
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
        if (type == HERO || type == MINION)
            drawHpAndAp(batch, x, y);
        batch.end();
        if (type == SPELL || type == ITEM)
            gif.setTime();
        if (isActive && !activeGif.isFinished())
            activeGif.draw(batch, x + 50, y + 150, 150, 150);
        else
            gif.draw(batch, x + 50, y + 150, 150, 150);
        glyphLayout.setText(priceFont, String.valueOf(price));
        batch.begin();
        batch.draw(pricePic, x, y + (328 - pricePic.getHeight()));
        priceFont.draw(batch, String.valueOf(price), x + (250 - glyphLayout.width) / 2, y + 328 - (pricePic.getHeight() - glyphLayout.height) / 2);
        batch.end();

    }

    private void drawHpAndAp(SpriteBatch batch, float x, float y) {
        glyphLayout.setText(arial24, String.valueOf(attackPoint));
        arial24.setColor(Main.toColor(new Color(0xFFFC00)));
        arial24.draw(batch, String.valueOf(attackPoint), 37 + x + (40 - glyphLayout.width) / 2, y + 150 - (40 - glyphLayout.height) / 2);
        glyphLayout.setText(arial24, String.valueOf(healthPoint));
        arial24.setColor(Main.toColor(new Color(0xFF0001)));
        arial24.draw(batch, String.valueOf(healthPoint), 170 + x + (40 - glyphLayout.width) / 2, y + 150 - (40 - glyphLayout.height) / 2);
        arial24.setColor(Main.toColor(new Color(764765576)));
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
