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
        if (text.equals("Disarm"))
            font.setColor(24 / 255f, 34 / 255f, 139 / 255f, 1 - time / 3);
        else if (text.equals("Stun"))
            font.setColor(112 / 255f, 112 / 255f, 102 / 255f, 1 - time / 3);
        else if (text.equals("Power"))
            font.setColor(193 / 255f, 179 / 255f, 21 / 255f, 1 - time / 3);
        else if (text.equals("Weakness"))
            font.setColor(124 / 255f, 65 / 255f, 14 / 255f, 1 - time / 3);
        else if (text.equals("Poison"))
            font.setColor(48 / 255f, 111 / 255f, 32 / 255f, 1 - time / 3);
        else if (text.equals("Bleeding"))
            font.setColor(124 / 255f, 14 / 255f, 14 / 255f, 1 - time / 3);
        else
            font.setColor(0, 190 / 255f, 1, 1 - time / 3);
        font.draw(batch, text, x, y);
        time += Gdx.graphics.getDeltaTime();
    }

    public float getTime() {
        return time;
    }
}
