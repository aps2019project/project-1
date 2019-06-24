package graphic.Others;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import control.BattleMenuHandler;
import graphic.main.Main;
import graphic.screen.BattleScreen;

public class BattlePopUp {

    private GlyphLayout glyphLayout = new GlyphLayout();
    private String text;
    private BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/Arial 24.fnt"));
    private float time;
    private float x;
    private float y;

    public BattlePopUp(String text, float x, float y) {
        this.text = text;
        this.time = 0;
        this.x = x - 30;
        this.y = y + 100;
        glyphLayout.setText(font, text);
        BattleScreen.getPopUps().add(this);
    }

    public void draw(SpriteBatch batch) {
        font.setColor(0, 190/255f, 1, 1 - time / 3);
        font.draw(batch, text, x, y);
        time += Gdx.graphics.getDeltaTime();
    }

    public float getTime() {
        return time;
    }
}
