package graphic.Others;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import graphic.main.Main;

public class PopUp {
    private static PopUp instance = new PopUp();

    private PopUp() {
    }

    /**
     * used to get the only instance of PopUp object
     * @return instance
     */
    public static PopUp getInstance() {
        return instance;
    }

    private GlyphLayout glyphLayout = new GlyphLayout();
    private String text = "";
    private BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/Arial 36.fnt"));
    private float time = 6;

    /**
     * used to  set Pop up message and makes renderer to draw it
     * @param text popUp message
     */
    public void setText(String text) {
        this.time = 0;
        this.text = text;
        glyphLayout.setText(font, text);
    }

    /**
     * render the text if exists
     * @param batch games spriteBatch
     */
    public void draw(SpriteBatch batch) {
        if (time > 5 || text.equals(""))
            return;
        batch.begin();
        font.setColor(1, 0.604f, 0, 1 - time / 5);
        font.draw(batch, text, (Main.WIDTH - glyphLayout.width) / 2, 850);
        batch.end();
        time += Gdx.graphics.getDeltaTime();
        if (time > 5) text = "";
    }

}
