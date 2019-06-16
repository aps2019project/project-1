package graphic.Others;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import graphic.main.Gif;

public class CardPicture {

    enum Type {SMALL, BIG}

    private Gif picture;
    private Texture backGroud;
    private String info;
    private String name;
    private int healthPoint;
    private int attackPoint;
    private Type type;
    private BitmapFont font;
    private Rectangle fram;

    public void draw(SpriteBatch batch, float x, float y) {
        batch.begin();
        batch.draw(backGroud, x, y);
        batch.end();
    }

}

