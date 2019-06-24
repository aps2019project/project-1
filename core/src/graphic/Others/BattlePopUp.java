package graphic.Others;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import control.BattleMenuHandler;
import graphic.main.Main;
import graphic.screen.BattleScreen;

public class BattlePopUp {

    private String text;
    private float time;
    private float x;
    private float y;

    public BattlePopUp(String text, float x, float y) {
        this.text = text;
        this.time = 0;
        this.x = x - 15;
        this.y = y + 100;
        BattleScreen.getPopUps().add(this);
    }

    public void draw(SpriteBatch batch) {
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/Arial 24.fnt"));
        font.setColor(0, 190/255f, 1, 1 - time / 3);
        font.draw(batch, text, x, y);
        time += Gdx.graphics.getDeltaTime();
    }

    public float getTime() {
        return time;
    }
}
